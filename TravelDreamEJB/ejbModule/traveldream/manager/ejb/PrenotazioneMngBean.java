package traveldream.manager.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.AttivitaSecondaria;
import model.AttivitaSecondariaPrenotazione;
import model.Hotel;
import model.Prenotazione;
import model.Utente;
import model.Volo;
import registrazione.ejb.UtenteMgrBean;
import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.PrenotazioneDTO;
import traveldream.dtos.UtenteDTO;
import traveldream.manager.PrenotazioneMng;

/**
 * Session Bean implementation class PacchettoMngBean
 */
@Stateless
public class PrenotazioneMngBean implements PrenotazioneMng {

	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;
	
	
	@Override
	public void Prenota(PrenotazioneDTO prenotazione) {
		// TODO Auto-generated method stub
		
		Prenotazione prenotazioneDaSalvare = new Prenotazione(prenotazione);
		Hotel hotel = em.find(Hotel.class, prenotazione.getHotel().getId());
		Volo voloAndata = em.find(Volo.class, prenotazione.getVoloAndata().getId());
		Volo voloRitorno = em.find(Volo.class, prenotazione.getVoloRitorno().getId());
		
		prenotazioneDaSalvare.setHotel(hotel);
		prenotazioneDaSalvare.setVoloAndata(voloAndata);
		prenotazioneDaSalvare.setVoloRitorno(voloRitorno);
		prenotazioneDaSalvare.setUtente(em.find(Utente.class, prenotazione.getUtente().getEmail()));
		
		em.persist(prenotazioneDaSalvare);
		
		hotel.setDisponibilita(hotel.getDisponibilita() - prenotazione.getPersone());
		voloAndata.setDisponibilita(voloAndata.getDisponibilita() - prenotazione.getPersone());
		voloRitorno.setDisponibilita(voloRitorno.getDisponibilita() - prenotazione.getPersone());
		
		em.merge(voloAndata);
		em.merge(voloRitorno);
		em.merge(hotel);
		
		for (AttivitaSecondariaDTO attivita : prenotazione.getListAttivitaSecondarie()) {
			
			AttivitaSecondaria attivitaDaAssociare = em.find(AttivitaSecondaria.class, attivita.getId());
			AttivitaSecondariaPrenotazione attivitaPrenotazione = new AttivitaSecondariaPrenotazione(attivitaDaAssociare, this.getLastPrenotazione());
			em.persist(attivitaPrenotazione);
			attivitaDaAssociare.setDisponibilita(attivitaDaAssociare.getDisponibilita() - prenotazione.getPersone());
			em.merge(attivitaDaAssociare);
		}
		
	}
	
	private Prenotazione getLastPrenotazione() {
		
		List<Prenotazione> prenotazioni = em.createNamedQuery("Prenotazione.selectMax", Prenotazione.class).getResultList();
		return prenotazioni.get(0);
		
	}

	public List<PrenotazioneDTO> getPrenotazioniUtente(UtenteDTO userDTO) {
		Utente utente = em.find(Utente.class, userDTO.getEmail());
		List<Prenotazione> lista = em.createNamedQuery("Prenotazione.getByUtente", Prenotazione.class).setParameter("utente", utente).getResultList();
		ArrayList <PrenotazioneDTO> myDTOlist = new ArrayList <PrenotazioneDTO> ();
		for(Prenotazione p : lista) {
			myDTOlist.add(convertToDto(p));
		}
		return myDTOlist;
	}
	
	protected static PrenotazioneDTO convertToDto(Prenotazione p) {
		PrenotazioneDTO pdto = new PrenotazioneDTO();
		pdto.setCostoPersona(p.getCostoPersona());
		pdto.setDataCreazione(p.getDataCreazione());
		pdto.setHotel(HotelMngBean.HotelToDTO(p.getHotel()));
		pdto.setId(p.getId());
		pdto.setPersone(p.getPersone());
		pdto.setVoloRitorno(VoloMngBean.convertVoloToDTO(p.getVoloRitorno()));
		pdto.setVoloAndata(VoloMngBean.convertVoloToDTO(p.getVoloAndata()));
		
		for (AttivitaSecondariaPrenotazione a : p.getAttivitaSecondariePrenotazione()) {
			pdto.getListAttivitaSecondarie().add(AttivitaMngBean.AttivitaToDTO(a.getAttivitaSecondariaBean()));
		}
		
		pdto.setUtente(UtenteMgrBean.convertToDTO(p.getUtente()));
		return pdto;
		
	}
	
	public List<PrenotazioneDTO> getPrenotazioni() {
		
		List<Prenotazione> lista = em.createNamedQuery("Prenotazione.findAll", Prenotazione.class).getResultList();
		ArrayList <PrenotazioneDTO> myDTOlist = new ArrayList <PrenotazioneDTO> ();
		for(Prenotazione p : lista) {
			myDTOlist.add(convertToDto(p));
		}
		return myDTOlist;
	}

}
