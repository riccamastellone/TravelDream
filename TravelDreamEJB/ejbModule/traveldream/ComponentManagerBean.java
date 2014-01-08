package traveldream;

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
import javax.persistence.Query;

import model.Hotel;
import model.Volo;
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
    
	/* HOTEL */
	
	public ArrayList<HotelDTO> getAllHotel()
	{
		List<Hotel> myList;
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
		hdto.setCostoGiornaliero(h.getCostoGiornaliero());
		hdto.setLuogo(h.getLuogo());
		hdto.setNome(h.getNome());
		hdto.setStelle(h.getStelle());
		hdto.setPathtoImage(h.getImmagine());
		hdto.setDisponibilita(h.getDisponibilita());
		hdto.setDescrizione(h.getDescrizione());
		return hdto;
		
	}
	
	public void salvaHotel(HotelDTO hotel) {
		System.out.println("salvo nuovo hotel");
		Hotel hotelNuovo = new Hotel(hotel); 
		em.persist(hotelNuovo);
	}
	
	public void aggiornaHotel(HotelDTO hotel) {
		Hotel hotelNuovo = this.findHotel(hotel.getId());
		hotelNuovo.setCostoGiornaliero(hotel.getCostoGiornaliero());
		hotelNuovo.setDescrizione(hotel.getDescrizione());
		hotelNuovo.setDisponibilita(hotel.getDisponibilita());
		hotelNuovo.setLuogo(hotel.getLuogo());
		hotelNuovo.setNome(hotel.getNome());
		hotelNuovo.setStelle(hotel.getStelle());
		em.merge(hotelNuovo);
	}
	
	private Hotel findHotel(int id) {
		return em.find(Hotel.class, id);
	}
	
	/* END HOTEL */
	
	
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
		vl.setArrivoInStringa(this.ConvertiInStringa(h.getArrivo()));
		vl.setPartenzaInStringa(this.ConvertiInStringa(h.getPartenza()));
		return vl;
	}



	@Override
	public void salvaVolo(VoloDTO volo) throws ParseException {
		// TODO Auto-generated method stub
		volo.setArrivo(this.convertiInData(volo.getArrivoInStringa()));
		volo.setPartenza(this.convertiInData(volo.getPartenzaInStringa()));
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
		// TODO Auto-generated method stub
		volo.setArrivo(this.convertiInData(volo.getArrivoInStringa()));
		volo.setPartenza(this.convertiInData(volo.getPartenzaInStringa()));
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
