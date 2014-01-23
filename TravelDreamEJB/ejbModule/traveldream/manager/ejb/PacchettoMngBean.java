package traveldream.manager.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.iconType;

import model.AttivitaSecondaria;
import model.AttivitaSecondariaPacchetto;
import model.Hotel;
import model.Pacchetto;
import model.Volo;
import model.VoloPacchetto;
import traveldream.dtos.AttivitaSecondariaDTO;
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

	protected static PacchettoDTO convertToDto(Pacchetto pacchetto) {
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
		em.getEntityManagerFactory().getCache().evictAll();
		return em.find(Pacchetto.class, id);
	}

	public PacchettoDTO findPacchettoDTO(int id) {
		Pacchetto pacchetto = this.findPacchetto(id);
		PacchettoDTO pacchettoDTO = this.convertToDto(pacchetto);
		for (VoloPacchetto voloPacchetto : pacchetto.getVoliPacchetto()) {

			if (voloPacchetto.getTipo().equals("Andata")) {
				pacchettoDTO.getVoliAndata().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
			} else {
				pacchettoDTO.getVoliRitorno().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
			}

			
		}
		
		//ricavo l hotel
		pacchettoDTO.setHotel(HotelMngBean.HotelToDTO(pacchetto.getHotel()));
		//ricavo la lista delle attivita secondarie asociate al paccchetto
		for (AttivitaSecondariaPacchetto attivitaSecondariaPacchetto : pacchetto.getAttivitaSecondariePacchetto()) {
			pacchettoDTO.getAttivitaSecondarie().add(AttivitaMngBean.AttivitaToDTO(attivitaSecondariaPacchetto.getAttivitaSecondariaBean()));
		}
		
		return pacchettoDTO;
		
	}

	@Override
	public PacchettoDTO salvaInfoGenerali(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		Pacchetto pacchettoDaSalvare = new Pacchetto(pacchetto);
		em.persist(pacchettoDaSalvare);

		// serve per fornire l'id el acchetto al client per poi aggiungere i
		// voli
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
	 * serve per prender il volo appena salvato con l'id aggiornato non
	 * riuscirei a passarlo al client se no
	 * 
	 * @param volo
	 * @return
	 */
	private Volo getVoloById(VoloDTO volo) {

		List<Volo> voli = em.createNamedQuery("Volo.getVoloById", Volo.class).setParameter("id", volo.getId()).getResultList();
		return voli.get(0);
	}

	/**
	 * serve per prendere l ultimo pacchetto e per passarlo al client con l id
	 * aggiornato
	 * 
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

	private Hotel getHotelById(HotelDTO hotel) {

		List<Hotel> hotels = em.createNamedQuery("Hotel.getVoloById", Hotel.class).setParameter("id", hotel.getId()).getResultList();
		return hotels.get(0);
	}


	private List<PacchettoDTO> buildPacchetti(List<Pacchetto> pacchetti) {
		List<PacchettoDTO> pacchettiDto = new ArrayList<PacchettoDTO>();
		for (Pacchetto pacchetto : pacchetti) {

			PacchettoDTO pacchettoDTO = this.convertToDto(pacchetto);
			
			//ricavo tutti i voli e li distinguo tra andata e ritorno
			for (VoloPacchetto voloPacchetto : pacchetto.getVoliPacchetto()) {

				if (voloPacchetto.getTipo().equals("Andata")) {
					pacchettoDTO.getVoliAndata().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
				} else {
					pacchettoDTO.getVoliRitorno().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
				}

				
			}
			
			//ricavo l hotel
			pacchettoDTO.setHotel(HotelMngBean.HotelToDTO(pacchetto.getHotel()));
			//ricavo la lista delle attivita secondarie asociate al paccchetto
			for (AttivitaSecondariaPacchetto attivitaSecondariaPacchetto : pacchetto.getAttivitaSecondariePacchetto()) {
				pacchettoDTO.getAttivitaSecondarie().add(AttivitaMngBean.AttivitaToDTO(attivitaSecondariaPacchetto.getAttivitaSecondariaBean()));
			}
			
			pacchettiDto.add(pacchettoDTO);
		}
		
		return pacchettiDto;	
	}
	
	public List<PacchettoDTO> getAllPacchetti() {
		
		List<Pacchetto> pacchetti = em.createNamedQuery("Pacchetto.findAll", Pacchetto.class).getResultList();
		return this.buildPacchetti(pacchetti);
	}

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

	public void eliminaVoloDaPacchetto(PacchettoDTO pacchettoDTO, VoloDTO volo) {
		// TODO Auto-generated method stub
		Volo voloDaDisassociare = this.getVoloById(volo);
		Pacchetto pacchetto = this.findPacchetto(pacchettoDTO.getId());

		for (VoloPacchetto voloPacchetto : pacchetto.getVoliPacchetto()) {
			if (voloPacchetto.getVolo().equals(voloDaDisassociare)) {
				pacchetto.getVoliPacchetto().remove(voloPacchetto);
				em.remove(voloPacchetto);
				break;
			}

		}

		em.merge(pacchetto);
		System.out.println("aggiorno pacchetto");

	}

	
	
	@Override
	public PacchettoDTO getPacchettoAggiornato(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		System.out.println("chiamato");
		Pacchetto pacchettoAggiornato = this.findPacchetto(pacchetto.getId());
		PacchettoDTO nuovoPacchetto = this.convertToDto(pacchettoAggiornato);
		for (VoloPacchetto voloPacchetto : pacchettoAggiornato.getVoliPacchetto()) {
			System.out.println(pacchettoAggiornato.getVoliPacchetto().size());
			if (voloPacchetto.getTipo().equals("Andata")) {
				nuovoPacchetto.getVoliAndata().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
			} else {
				nuovoPacchetto.getVoliRitorno().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
			}
		}
		nuovoPacchetto.setHotel(HotelMngBean.HotelToDTO(pacchettoAggiornato.getHotel()));
		
		for (AttivitaSecondariaPacchetto attivitaSecondariaPacchetto : pacchettoAggiornato.getAttivitaSecondariePacchetto()) {
			nuovoPacchetto.getAttivitaSecondarie().add(AttivitaMngBean.AttivitaToDTO(attivitaSecondariaPacchetto.getAttivitaSecondariaBean()));
		}
		return nuovoPacchetto;
	}

	@Override
	public void deletePacchetto(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		Pacchetto pacchettoDaCancellare = this.findPacchetto(pacchetto.getId());
		pacchettoDaCancellare.setEliminato(1);
		em.merge(pacchettoDaCancellare);
		
	}
	
	private AttivitaSecondaria getAttivitaById(AttivitaSecondariaDTO attivita) {
		List<AttivitaSecondaria> attivitaSecondarie = em.createNamedQuery("AttivitaSecondaria.getVoloById", AttivitaSecondaria.class).setParameter("id", attivita.getId()).getResultList();
		return attivitaSecondarie.get(0);
	}

	@Override
	public void aggiungiAttivitaAPacchetto(PacchettoDTO pacchetto, AttivitaSecondariaDTO attivita) {
		// TODO Auto-generated method stub
		AttivitaSecondaria attivitaNuova = this.getAttivitaById(attivita);
		Pacchetto pacchettoDaAggiornare = this.findPacchetto(pacchetto.getId());
		System.out.println(pacchettoDaAggiornare.getNome());
		AttivitaSecondariaPacchetto attivitaPacchetto = new AttivitaSecondariaPacchetto(pacchettoDaAggiornare, attivitaNuova);
		em.persist(attivitaPacchetto);
			
	}

	@Override
	public void eliminaAttivitaDaPacchetto(PacchettoDTO pacchettoDTO, AttivitaSecondariaDTO attivita) {
		// TODO Auto-generated method stub
		AttivitaSecondaria attivitaDaDissociare= this.getAttivitaById(attivita);
		Pacchetto pacchetto = this.findPacchetto(pacchettoDTO.getId());
		
		for (AttivitaSecondariaPacchetto attivitaPacchetto : pacchetto.getAttivitaSecondariePacchetto()) {
			if (attivitaPacchetto.getAttivitaSecondariaBean().equals(attivitaDaDissociare)){
				pacchetto.getAttivitaSecondariePacchetto().remove(attivitaPacchetto);
				em.remove(attivitaPacchetto);
				break;
			}
				
		}
		
		em.merge(pacchetto);
		System.out.println("aggiorno pacchetto");
		
	}

	@Override
	public List<PacchettoDTO> ricercaPacchetto(String destinazione, Date partenza, Date ritorno, int persone) {
		// TODO Auto-generated method stub
		System.out.println("sono qui");
		List<Pacchetto> lista = em.createNamedQuery("Pacchetto.Ricerca", Pacchetto.class).setParameter("localita", destinazione).getResultList();
		List<PacchettoDTO> pacchettiDTO = new ArrayList<PacchettoDTO>();
		
		//tolgo i paccheti non validi eliminando quelli che non hanno l hotel disponibile per il numero di persone scelte e che non sono nel range di date scelte
		for (Iterator<Pacchetto> pacchettiIterator = lista.iterator(); pacchettiIterator.hasNext();) {
			Pacchetto pacchetto = pacchettiIterator.next();
			if ((pacchetto.getInizioValidita().after(partenza) || pacchetto.getFineValidita().before(ritorno)) || (pacchetto.getHotel().getDisponibilita() < persone) ){
				System.out.println(pacchetto.getNome());
				pacchettiIterator.remove();
			}
		}
		System.out.println("------ Pacchetti rimanenti ----");
		for (Pacchetto pacchetto : lista) {
			System.out.println(pacchetto.getNome());
		}
		
		for (Pacchetto pacchetto : lista) {
			
			PacchettoDTO pacchettoDTO = this.convertToDto(pacchetto);
			
			pacchettoDTO.setHotel(HotelMngBean.HotelToDTO(pacchetto.getHotel()));
			
			//tolgo le attivita che non hanno abbastanza posti disponibili
			List<AttivitaSecondariaPacchetto> attivitaSecondariaPacchetto = pacchetto.getAttivitaSecondariePacchetto();
			for (AttivitaSecondariaPacchetto attivitaSecondariaPacchetto2 : attivitaSecondariaPacchetto) {
				if (attivitaSecondariaPacchetto2.getAttivitaSecondariaBean().getDisponibilita() >= persone){
					pacchettoDTO.getAttivitaSecondarie().add(AttivitaMngBean.AttivitaToDTO(attivitaSecondariaPacchetto2.getAttivitaSecondariaBean()));
				}
			}
		
		//se l utente ha cercato anche le date restituisco solo i voli compatibili con le date scelte	
		if (!partenza.equals(ritorno)){
			
			System.out.println("entrato");

				List<VoloPacchetto> voliPacchetto = pacchetto.getVoliPacchetto();
				
				
				for (VoloPacchetto voloPacchetto : voliPacchetto) {
					//controllo il range delle date sui voli di andata e che i posti disponibii siano sufficienti
					if (voloPacchetto.getTipo().equals("Andata") && (voloPacchetto.getVolo().getPartenza().after(partenza) || voloPacchetto.getVolo().getPartenza().equals(partenza)) && 
							(voloPacchetto.getVolo().getPartenza().before(ritorno)) && (voloPacchetto.getVolo().getDisponibilita() >= persone) ){
						pacchettoDTO.getVoliAndata().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
					}
					//controllo il range delle date sui voli di ritorno e che i posti disponibii siano sufficienti
					if (voloPacchetto.getTipo().equals("Ritorno") && (voloPacchetto.getVolo().getPartenza().before(ritorno) || voloPacchetto.getVolo().getPartenza().equals(ritorno)) && 
							(voloPacchetto.getVolo().getPartenza().after(partenza)) && (voloPacchetto.getVolo().getDisponibilita() >= persone)){
						pacchettoDTO.getVoliRitorno().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
					}
				}

				//se almeno una lista di componenti e vuota non restitutisco il pacchetto
				if (pacchettoDTO.getVoliAndata().isEmpty() || pacchettoDTO.getVoliRitorno().isEmpty() || pacchettoDTO.getAttivitaSecondarie().isEmpty() ){
					continue;
				}
				//altrimenti lo aggiungo ai risultati della ricerca
				else {
					pacchettiDTO.add(pacchettoDTO);
				}
	
			} //CHIUDO IF

			//se l utente non ha cercato per data mi limito a scremare i voli per disponibilita
			else{
				System.out.println("entro else");
				
				List<VoloPacchetto> voliPacchetto = pacchetto.getVoliPacchetto();
				for (VoloPacchetto voloPacchetto : voliPacchetto) {
					if (voloPacchetto.getTipo().equals("Andata")  && 
							(voloPacchetto.getVolo().getDisponibilita() >= persone) ){
						pacchettoDTO.getVoliAndata().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
					}
					if (voloPacchetto.getTipo().equals("Ritorno") && 
							(voloPacchetto.getVolo().getDisponibilita() >= persone)){
						pacchettoDTO.getVoliRitorno().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
					}
				}

				//se almeno una lista di componenti e vuota non restitutisco il pacchetto
				if (pacchettoDTO.getVoliAndata().isEmpty() || pacchettoDTO.getVoliRitorno().isEmpty() || pacchettoDTO.getAttivitaSecondarie().isEmpty() ){
					continue;
				}
				else {
					pacchettiDTO.add(pacchettoDTO);
				}
				
			}// CHIUDO ELSE
		
		}//CHIUDO FOR
		
		
		//DEBUG
		System.out.println(pacchettiDTO.size());
		for (PacchettoDTO pacchettoDTO : pacchettiDTO) {
			System.out.println(pacchettoDTO.getHotel());
			System.out.println("------ VOLI ANDATA ------");
			for (VoloDTO voloDTO : pacchettoDTO.getVoliAndata()) {
				System.out.println(voloDTO.getNomeCompagnia());
			}
			System.out.println("------ VOLI RITORNO ------");
			for (VoloDTO voloDTO : pacchettoDTO.getVoliRitorno()) {
				System.out.println(voloDTO.getNomeCompagnia());
			}
			System.out.println("------ ATTIVITA ------");
			for (AttivitaSecondariaDTO att : pacchettoDTO
					.getAttivitaSecondarie()) {
				System.out.println(att.getNome());
			}

		}

		return pacchettiDTO;
					
	}
	

}
