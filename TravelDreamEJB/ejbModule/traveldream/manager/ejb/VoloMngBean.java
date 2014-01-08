package traveldream.manager.ejb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Volo;
import traveldream.dtos.VoloDTO;
import traveldream.manager.VoloMng;


@Stateless
public class VoloMngBean implements VoloMng  {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;

	
	/* VOLI */
	
	private VoloDTO convertVoloToDTO(Volo h) {
		VoloDTO vl = new VoloDTO();
		vl.setId(h.getId());
		vl.setArrivo(h.getArrivo());
		vl.setCittaPartenza(h.getCittaPartenza());
		vl.setCittaArrivo(h.getCittaArrivo());
		vl.setCosto(h.getCosto());
		vl.setDisponibilita(h.getDisponibilita());
		vl.setNomeCompagnia(h.getNomeCompagnia());
		vl.setPartenza(h.getPartenza());
		vl.setEliminato(h.getEliminato());
		return vl;
	}



	@Override
	public void salvaVolo(VoloDTO volo) throws ParseException {
		System.out.println("salvo volo");
		Volo voloNuovo = new Volo(volo); 
		em.persist(voloNuovo);
	}



	@Override
	public ArrayList<VoloDTO> getVoli() {
		// TODO Auto-generated method stub
	ArrayList<VoloDTO> voliDTO = new ArrayList<VoloDTO>();
		
		//query dichiarate nell entita UtenteGruppo 		
		List<Volo> voli = em.createNamedQuery("Volo.findAll", Volo.class).getResultList();;
		
		for (Volo volo : voli) {
			
			voliDTO.add(this.convertVoloToDTO(volo));	
		}
		return voliDTO;
	}
	
	private Volo findVolo(int id) {
		return em.find(Volo.class, id);
	}



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
				
	}
	


	@Override
	public void deleteVolo(VoloDTO volo) {
		// TODO Auto-generated method stub
		Volo voloDaCancellare = this.findVolo(volo.getId());
		voloDaCancellare.setEliminato(1);
		em.merge(voloDaCancellare);		
	}

	public Date convertiInData(String data) throws ParseException {
		if (data.equals("")) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date convertedDate = dateFormat.parse(data);
		return convertedDate;
	}
	
	public String ConvertiInStringa(Date data){
		DateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
		return formato.format(data);
	}




	
}
