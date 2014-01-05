package traveldream;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Hotel;
import dtos.HotelDTO;


@Stateless
public class ComponentManagerBean  {

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
		hdto.setPathtoImage("");
		return hdto;
 
	}
	




	
}
