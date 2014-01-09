package traveldream.manager.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.AttivitaSecondaria;
import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.manager.AttivitaMng;

@Stateless
public class AttivitaMngBean implements AttivitaMng {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;


	private AttivitaSecondariaDTO AttivitaToDTO(AttivitaSecondaria a) {
		AttivitaSecondariaDTO as = new AttivitaSecondariaDTO();
		as.setNome(a.getNome());
		as.setCosto(a.getCosto());
		as.setDescrizione(a.getDescrizione());
		as.setDisponibilita(a.getDisponibilita());
		as.setId(a.getId());
		as.setLocalita(a.getLocalita());
		return as;
	}
/*
	public void salvaVolo(VoloDTO volo) throws ParseException {
		System.out.println("salvo volo");
		Volo voloNuovo = new Volo(volo);
		em.persist(voloNuovo);
	}
*/
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
/*
	@Override
	public void aggiornaVolo(VoloDTO volo) throws ParseException {
		Volo voloDaModificare = this.findVolo(volo.getId());
		voloDaModificare.setArrivo(volo.getArrivo());
		voloDaModificare.setCittaArrivo(volo.getCittaArrivo());
		voloDaModificare.setCittaPartenza(volo.getCittaPartenza());
		voloDaModificare.setCosto(volo.getCosto());
		voloDaModificare.setDisponibilita(volo.getDisponibilita());
		voloDaModificare.setNomeCompagnia(volo.getNomeCompagnia());
		voloDaModificare.setPartenza(volo.getPartenza());

		em.merge(voloDaModificare);

	}*/

	public void deleteAttivita(AttivitaSecondariaDTO attivita) {
		AttivitaSecondaria attivitaDC = this.findAttivita(attivita.getId());
		attivitaDC.setEliminato(1);
		em.merge(attivitaDC);
	}

}
