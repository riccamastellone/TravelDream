package traveldream;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Hotel;
import model.Utente;
import model.UtenteGruppo;
import model.Volo;
import registrazione.client.UtenteDTO;
import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;
import traveldream.gestioneComponente.ComponenteMng;


@Stateless
public class ComponentManagerBean implements ComponenteMng  {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;

	public void saveHotel(HotelDTO hoteldto) {

		Hotel hotel = new Hotel(hoteldto);	//aggiungo alla tabella Utente una tupla utilizzanto il DTO
		em.persist(hotel);	
	}
	

	
	public void update() {
	}

	public void remove() {
	}
    
	//sfrutto la named query per ritornare tutti gli hotel dal DB
	//convertendoli in DTO per il managed bean
	public ArrayList<HotelDTO> getAllHotel()
	{
		List <Hotel> myList;
		ArrayList <HotelDTO> myDTOlist = new ArrayList <HotelDTO> ();
		myList = em.createNamedQuery(Hotel.FIND_ALL, Hotel.class).getResultList();
		for (Hotel h : myList)
		    {
			 myDTOlist.add(this.HotelToDTO(h));
		    }
		return myDTOlist;
	}

	private HotelDTO HotelToDTO(Hotel h) {
		HotelDTO hdto = new HotelDTO();
		hdto.setCosto_giornaliero(h.getCosto_giornaliero());
		hdto.setLuogo(h.getLuogo());
		hdto.setNome(h.getNome());
		hdto.setStelle(h.getStelle());
		hdto.setPathtoImage(h.getImmagine());
		hdto.setDisponibilita(h.getDisponibilita());
		hdto.setDescrizione(h.getDescrizione());
		return hdto;
		
	}
	
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
	public void salvaVolo(VoloDTO volo) {
		// TODO Auto-generated method stub
		System.out.println("salvo volo");
		Volo voloNuovo = new Volo(volo); 
		em.persist(voloNuovo);
	}



	@Override
	public ArrayList<VoloDTO> getVoli() {
		// TODO Auto-generated method stub
	ArrayList<VoloDTO> voliDTO = new ArrayList<VoloDTO>();
		
		//query dichiarate nell entita UtenteGruppo 
		Query queryGetAllVoli = em.createNamedQuery("Volo.findAll");
		
		List<Volo> voli = queryGetAllVoli.getResultList();
		
		for (Volo volo : voli) {
			
			voliDTO.add(this.convertVoloToDTO(volo));	
		}
		return voliDTO;
	}
	
	private Volo findVolo(int id) {
		return em.find(Volo.class, id);
	}




	@Override
	public void aggiornaVolo(VoloDTO volo) {
		// TODO Auto-generated method stub
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
		em.remove(voloDaCancellare);		
	}
	




	
}
