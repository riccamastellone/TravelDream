package traveldream.manager.ejb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.AttivitaSecondaria;
import model.AttivitaSecondariaPacchetto;
import model.Hotel;
import model.Volo;
import model.VoloPacchetto;
import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.AttivitaMng;

@Stateless
public class AttivitaMngBean implements AttivitaMng {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;


	protected static AttivitaSecondariaDTO AttivitaToDTO(AttivitaSecondaria a) {
		AttivitaSecondariaDTO as = new AttivitaSecondariaDTO();
		as.setNome(a.getNome());
		as.setCosto(a.getCosto());
		as.setDescrizione(a.getDescrizione());
		as.setDisponibilita(a.getDisponibilita());
		as.setId(a.getId());
		as.setLocalita(a.getLocalita());
		return as;
	}

	public void salvaAttivita(AttivitaSecondariaDTO attivita) {
		AttivitaSecondaria attivitaNew = new AttivitaSecondaria(attivita);
		em.persist(attivitaNew);
	}

	public ArrayList<AttivitaSecondariaDTO> getAttivita() {
		List<AttivitaSecondaria> myList;
		ArrayList<AttivitaSecondariaDTO> attivitaDTO = new ArrayList<AttivitaSecondariaDTO>();
		myList = em.createNamedQuery("AttivitaSecondaria.findAll", AttivitaSecondaria.class)
				.getResultList();
		for (AttivitaSecondaria h : myList) {
			attivitaDTO.add(this.AttivitaToDTO(h));
		}
		return attivitaDTO;
	}

	private AttivitaSecondaria findAttivita(int id) {
		return em.find(AttivitaSecondaria.class, id);
	}

	public void aggiornaAttivita(AttivitaSecondariaDTO attivita)  {
		AttivitaSecondaria as = this.findAttivita(attivita.getId());
		as.setNome(attivita.getNome());
		as.setLocalita(attivita.getLocalita());
		as.setDisponibilita(attivita.getDisponibilita());
		as.setCosto(attivita.getCosto());
		as.setDescrizione(attivita.getDescrizione());

		em.merge(as);

	}

	public void deleteAttivita(AttivitaSecondariaDTO attivita) {
		AttivitaSecondaria attivitaDC = this.findAttivita(attivita.getId());
		attivitaDC.setEliminato(1);
		em.merge(attivitaDC);
		List<AttivitaSecondariaPacchetto> attivitaPacchetto = em.createNamedQuery("AttivitaSecondariaPacchetto.getPacchettiByAttivita", AttivitaSecondariaPacchetto.class).setParameter("attivita", attivitaDC).getResultList();
		for (AttivitaSecondariaPacchetto attivitaSecondariaPacchetto : attivitaPacchetto) {
				em.remove(attivitaSecondariaPacchetto);
			
		}
	}
	
	private AttivitaSecondaria getLastAttivita() {

		List<AttivitaSecondaria> attivita = em.createNamedQuery("AttivitaSecondaria.selectMax", AttivitaSecondaria.class).getResultList();
		return attivita.get(0);
	}

	@Override
	public AttivitaSecondariaDTO aggiungiAttivitaAPacchetto(AttivitaSecondariaDTO attivita) throws ParseException {
		// TODO Auto-generated method stub
		salvaAttivita(attivita);
		AttivitaSecondaria attivitaDaRitornare = this.getLastAttivita();
		attivita.setId(attivitaDaRitornare.getId());
		return attivita;
	}

	@Override
	public List<AttivitaSecondariaDTO> getAttivitaCompatibiliPacchetto(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		List<AttivitaSecondariaDTO> attivitaDTO = new ArrayList<AttivitaSecondariaDTO>();
		
		List<AttivitaSecondaria> listaAttivita = em.createNamedQuery("AttivitaSecondaria.getAttivitaCompatibiliPacchetto", AttivitaSecondaria.class).setParameter("localita", "%"+pacchetto.getLocalita()+"%").getResultList();
	
		for (AttivitaSecondaria attivita : listaAttivita) {

			attivitaDTO.add(this.AttivitaToDTO(attivita));
		}
		return attivitaDTO;
	
	}

}
