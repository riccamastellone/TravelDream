package traveldream.manager.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Hotel;
import traveldream.dtos.HotelDTO;
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

		Hotel hotel = new Hotel(hoteldto);	//aggiungo alla tabella Utente una tupla utilizzanto il DTO
		em.persist(hotel);	
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

}
