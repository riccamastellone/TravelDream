package traveldream.manager.ejb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.AttivitaSecondaria;
import model.AttivitaSecondariaPrenotazione;
import model.Hotel;
import model.Pacchetto;
import model.Prenotazione;
import model.Utente;
import model.Volo;
import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.PrenotazioneDTO;
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
		prenotazioneDaSalvare.setHotel(em.find(Hotel.class, prenotazione.getHotel().getId()));
		prenotazioneDaSalvare.setVoloAndata(em.find(Volo.class, prenotazione.getVoloAndata().getId()));
		prenotazioneDaSalvare.setVoloRitorno(em.find(Volo.class, prenotazione.getVoloRitorno().getId()));
		prenotazioneDaSalvare.setUtente(em.find(Utente.class, prenotazione.getUtente().getEmail()));
		
		em.persist(prenotazioneDaSalvare);
		
		for (AttivitaSecondariaDTO attivita : prenotazione.getListAttivitaSecondarie()) {
			
			AttivitaSecondaria attivitaDaAssociare = em.find(AttivitaSecondaria.class, attivita.getId());
			AttivitaSecondariaPrenotazione attivitaPrenotazione = new AttivitaSecondariaPrenotazione(attivitaDaAssociare, this.getLastPrenotazione());
			em.persist(attivitaPrenotazione);
		}
		
	}
	
	private Prenotazione getLastPrenotazione() {
		
		List<Prenotazione> prenotazioni = em.createNamedQuery("Prenotazione.selectMax", Prenotazione.class).getResultList();
		return prenotazioni.get(0);
		
	}

}
