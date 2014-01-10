package traveldream.manager.ejb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Hotel;
import model.Volo;
import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.HotelMng;

/**
 * Session Bean implementation class HotelMngBean
 */
@Stateless
public class HotelMngBean implements HotelMng{

    /**
     * Default constructor. 
     */
    public HotelMngBean() {
        // TODO Auto-generated constructor stub
    }
    
    @PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;

	public void saveHotel(HotelDTO hoteldto) {

		Hotel hotel = new Hotel(hoteldto);
		em.persist(hotel);	
	}

	
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
		hdto.setId(h.getId());
		hdto.setCostoGiornaliero(h.getCostoGiornaliero());
		hdto.setLuogo(h.getLuogo());
		hdto.setNome(h.getNome());
		hdto.setStelle(h.getStelle());
		hdto.setPathtoImage(h.getImmagine());
		hdto.setDisponibilita(h.getDisponibilita());
		hdto.setDescrizione(h.getDescrizione());
		hdto.setId(h.getId());
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
		
		if(hotel.getPathtoImage() != null) {
			hotelNuovo.setImmagine(hotel.getPathtoImage());
		} 
		em.merge(hotelNuovo);
	}
	
	private Hotel findHotel(int id) {
		return em.find(Hotel.class, id);
	}
	
	public void deleteHotel(HotelDTO hotel) {
		Hotel hotelDaCancellare = this.findHotel(hotel.getId());
		hotelDaCancellare.setEliminato(1);
		em.merge(hotelDaCancellare);		
	}



	@Override
	public HotelDTO aggiungiHotelAPacchetto(HotelDTO hotel) throws ParseException {
		// TODO Auto-generated method stub
		salvaHotel(hotel);
		Hotel hotelDaRitornare = this.getLastHotel(); 
		hotel.setId(hotelDaRitornare.getId());
		return hotel;
	}
	
	private Hotel getLastHotel() {

		List<Hotel> hotel = em.createNamedQuery("Hotel.selectMax", Hotel.class).getResultList();
		return hotel.get(0);
	}

}
