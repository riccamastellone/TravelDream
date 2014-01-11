package traveldream.manager.ejb;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import model.Hotel;
import model.Pacchetto;
import model.Volo;
import model.VoloPacchetto;
import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.PacchettoMng;

/**
 * Session Bean implementation class PacchettoMngBean
 */
@Stateless
public class PacchettoMngBean implements PacchettoMng {

	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	/**
	 * Default constructor.
	 */
	public PacchettoMngBean() {
		// TODO Auto-generated constructor stub
	}
	
	private PacchettoDTO convertToDto(Pacchetto pacchetto){
		PacchettoDTO p = new PacchettoDTO();
		p.setId(pacchetto.getId());
		p.setDescrizione(pacchetto.getDescrizione());
		p.setInizioValidita(pacchetto.getInizioValidita());
		p.setFineValidita(pacchetto.getFineValidita());
		p.setLocalita(pacchetto.getLocalita());
		p.setNome(pacchetto.getNome());
		p.setImmagine(pacchetto.getImmagine());
		return p;
	}
	
	private Pacchetto findPacchetto(int id) {
		return em.find(Pacchetto.class, id);
	}

	@Override
	public PacchettoDTO salvaInfoGenerali(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub		
		Pacchetto pacchettoDaSalvare = new Pacchetto(pacchetto);
		em.persist(pacchettoDaSalvare);
		
		//serve per fornire l'id el acchetto al client per poi aggiungere i voli
		pacchettoDaSalvare = this.getLastPacchetto();
		PacchettoDTO pacchettoNuovo = convertToDto(pacchettoDaSalvare);
		System.out.println("salvo info generali");
		return pacchettoNuovo;
	}

	@Override
	public void aggiungiVoloAPacchetto(PacchettoDTO pacchetto, VoloDTO volo, String tipo) {
		// TODO Auto-generated method stub
		Volo voloNuovo = this.getVoloById(volo); 
		
		Pacchetto pacchettoDaAggiornare = this.findPacchetto(pacchetto.getId());
		System.out.println(pacchettoDaAggiornare.getNome());
		
		VoloPacchetto voloPacchetto = new VoloPacchetto(pacchettoDaAggiornare, voloNuovo, tipo);
		
		em.persist(voloPacchetto);
	}
    
	/**
	 * serve per prender il volo appena salvato con l'id aggiornato
	 * non riuscirei a passarlo al client se no
	 * @param volo
	 * @return
	 */
	private Volo getVoloById(VoloDTO volo){
		
		List<Volo> voli = em.createNamedQuery("Volo.getVoloById", Volo.class).setParameter("id", volo.getId()).getResultList();
		return voli.get(0);
	}
	
	/**
	 * serve per prendere l ultimo pacchetto e per passarlo al client con l id aggiornato
	 * @return
	 */
	private Pacchetto getLastPacchetto() {

		List<Pacchetto> pacchetti = em.createNamedQuery("Pacchetto.selectMax", Pacchetto.class).getResultList();
		return pacchetti.get(0);
	}

	@Override
	public void aggiungiHotelAPacchetto(PacchettoDTO pacchetto, HotelDTO hotel) {
		// TODO Auto-generated method stub
		Hotel hotelNuovo = this.getHotelById(hotel);
		
		Pacchetto pacchettoDaAggiornare = this.findPacchetto(pacchetto.getId());
		pacchettoDaAggiornare.setHotel(hotelNuovo);
		
		em.merge(pacchettoDaAggiornare);
				
	}
	
	private Hotel getHotelById(HotelDTO hotel){
		
		List<Hotel> hotels = em.createNamedQuery("Hotel.getVoloById", Hotel.class).setParameter("id", hotel.getId()).getResultList();
		return hotels.get(0);
	}

	@Override
	public List<PacchettoDTO>  getAllPacchetti() {
		// TODO Auto-generated method stub
		List<PacchettoDTO> pacchettiDto = new ArrayList<PacchettoDTO>();
		List<Pacchetto> pacchetti = em.createNamedQuery("Pacchetto.findAll", Pacchetto.class).getResultList();
		System.out.println("eseguito");
		
		for (Pacchetto pacchetto : pacchetti) {
			
			PacchettoDTO pacchettoDTO = this.convertToDto(pacchetto);
			for (VoloPacchetto voloPacchetto : pacchetto.getVoliPacchetto()) {
				
				if (voloPacchetto.getTipo().equals("Andata")){
					pacchettoDTO.getVoliAndata().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
				}
				else {
					pacchettoDTO.getVoliRitorno().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
				}
				
				//pacchettoDTO.getVoliAndata().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
				//pacchettiDTO.get
			}
			
			
			pacchettiDto.add(pacchettoDTO);
		}
		
		
		
		return pacchettiDto;
		
	}

	@Override
	public void editInfoGenerali(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		Pacchetto pacchettoDaModificare = this.findPacchetto(pacchetto.getId());
		pacchettoDaModificare.setDescrizione(pacchetto.getDescrizione());
		pacchettoDaModificare.setFineValidita(pacchetto.getFineValidita());
		pacchettoDaModificare.setInizioValidita(pacchetto.getInizioValidita());
		pacchettoDaModificare.setLocalita(pacchetto.getLocalita());
		pacchettoDaModificare.setNome(pacchetto.getNome());
		pacchettoDaModificare.setImmagine(pacchetto.getImmagine());
		em.merge(pacchettoDaModificare);
		System.out.println("aggiorno pacchetto");
						
	}
	
 

}
