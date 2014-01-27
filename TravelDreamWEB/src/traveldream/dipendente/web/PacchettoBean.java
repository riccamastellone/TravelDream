package traveldream.dipendente.web;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.expression.impl.ThisExpressionResolver;
import org.primefaces.model.UploadedFile;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.AttivitaMng;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.VoloMng;

@ManagedBean(name = "pacchettoBean")
@SessionScoped
public class PacchettoBean implements Serializable {

	private static final long serialVersionUID = 1592347875820256991L;

	@EJB
	private PacchettoMng pkgMng;

	@EJB
	private VoloMng voloMng;

	@EJB
	private HotelMng hotelMng;
	
	@EJB
	private AttivitaMng attivitalMng;
	
	
	private PacchettoDTO pacchetto;

	// serve per visualizzare i voli esistenti
	private List<VoloDTO> voli;

	// serve per l inserimento a db (creare un nuovo volo nella tabella voli)
	private List<VoloDTO> voliNuoviAndata;

	// serve per l inserimento a db (creare un nuovo volo nella tabella voli)
	private List<VoloDTO> voliNuoviRitorno;

	// serve per l inserimento a db (NON creare un nuovo volo nella tabella voli
	// ma associare e basta)
	private List<VoloDTO> voliEsistentiAndata;

	// serve per l inserimento a db (NON creare un nuovo volo nella tabella voli
	// ma associare e basta)
	private List<VoloDTO> voliEsistentiRitorno;

	private VoloDTO volo;

	private String tipoVolo;

	private HotelDTO hotelDTO;

	private List<HotelDTO> hotelSalvato;

	private List<HotelDTO> listaHotelesistenti;

	private List<PacchettoDTO> pacchetti;

	private PacchettoDTO pacchettoDaVisualizzareDto;
	
	private List<AttivitaSecondariaDTO> attivitaSecondarie;
	
	private List<AttivitaSecondariaDTO> attivitaSecondarieEsistentiCompatibili;
	
	private AttivitaSecondariaDTO attivitaDaSalvare;
	
	private UploadedFile file;
	
	private UploadedFile fileHotel;
	
	private String tmpImage;
	
	private String tmpImageHotel;
	

	public PacchettoBean() {
		this.pacchetto = new PacchettoDTO();
		this.volo = new VoloDTO();
		this.voli = new ArrayList<VoloDTO>();
		this.setTipoVolo("Andata");
		this.voliNuoviAndata = new ArrayList<VoloDTO>();
		this.voliNuoviRitorno = new ArrayList<VoloDTO>();
		this.setVoliEsistentiAndata(new ArrayList<VoloDTO>());
		this.setVoliEsistentiRitorno(new ArrayList<VoloDTO>());
		this.pacchetto.getVoliAndata().clear();
		this.pacchetto.getVoliRitorno().clear();
		this.hotelDTO = new HotelDTO();
		this.hotelSalvato = new ArrayList<HotelDTO>();
		this.listaHotelesistenti = new ArrayList<HotelDTO>();
		this.pacchetti = new ArrayList<PacchettoDTO>();
		this.pacchettoDaVisualizzareDto = new PacchettoDTO();
		this.attivitaSecondarie = new ArrayList<AttivitaSecondariaDTO>();
		this.attivitaSecondarie = new ArrayList<AttivitaSecondariaDTO>();
		this.attivitaDaSalvare = new AttivitaSecondariaDTO();
		this.pacchetto.setInizioValidita(new Date());
		this.pacchetto.setFineValidita(new Date());

	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	public List<VoloDTO> getVoliNuoviAndata() {
		return voliNuoviAndata;
	}

	public void setVoliNuoviAndata(List<VoloDTO> voliAndata) {
		this.voliNuoviAndata = voliAndata;
	}

	public List<VoloDTO> getVoliNuoviRitorno() {
		return voliNuoviRitorno;
	}

	public void setVoliNuoviRitorno(List<VoloDTO> voliRitorno) {
		this.voliNuoviRitorno = voliRitorno;
	}

	public List<VoloDTO> getVoliEsistentiAndata() {
		return voliEsistentiAndata;
	}

	public void setVoliEsistentiAndata(List<VoloDTO> voliEsistentiAndata) {
		this.voliEsistentiAndata = voliEsistentiAndata;
	}

	public List<VoloDTO> getVoliEsistentiRitorno() {
		return voliEsistentiRitorno;
	}

	public void setVoliEsistentiRitorno(List<VoloDTO> voliEsistentiRitorno) {
		this.voliEsistentiRitorno = voliEsistentiRitorno;
	}

	public List<VoloDTO> getVoli() {
		return voli;
	}

	public void setVoli(List<VoloDTO> voli) {
		this.voli = voli;
	}

	public VoloDTO getVolo() {
		return volo;
	}

	public void setVolo(VoloDTO voloDTO) {
		this.volo = voloDTO;
	}

	public String getTipoVolo() {
		return tipoVolo;
	}

	public void setTipoVolo(String tipoVolo) {
		this.tipoVolo = tipoVolo;
	}

	public HotelDTO getHotelDTO() {
		return hotelDTO;
	}

	public void setHotelDTO(HotelDTO hotelDTO) {
		this.hotelDTO = hotelDTO;
	}

	public List<HotelDTO> getHotelSalvato() {
		return hotelSalvato;
	}

	public void setHotelSalvato(List<HotelDTO> hotelSalvato) {
		this.hotelSalvato = hotelSalvato;
	}

	public List<HotelDTO> getListaHotelesistenti() {
		return listaHotelesistenti;
	}

	public void setListaHotelesistenti(List<HotelDTO> listaHotelesistenti) {
		this.listaHotelesistenti = listaHotelesistenti;
	}

	public List<PacchettoDTO> getPacchetti() {

		if (this.pacchetti.isEmpty()){
			  this.pacchetti = pkgMng.getAllPacchetti();
			  
		}
		
		return this.pacchetti;
	}

	public void setPacchetti(List<PacchettoDTO> pacchetti) {
		this.pacchetti = pacchetti;
	}

	public PacchettoDTO getPacchettoDaVisualizzareDto() {
		return pacchettoDaVisualizzareDto;
	}

	public void setPacchettoDaVisualizzareDto(PacchettoDTO pacchettoDaVisualizzareDto) {
		this.pacchettoDaVisualizzareDto = pacchettoDaVisualizzareDto;
	}
	
	public List<AttivitaSecondariaDTO> getAttivitaSecondarie() {
		return attivitaSecondarie;
	}

	public void setAttivitaSecondarie(List<AttivitaSecondariaDTO> attivitaSecondarie) {
		this.attivitaSecondarie = attivitaSecondarie;
	}

	public AttivitaSecondariaDTO getAttivitaDaSalvare() {
		return attivitaDaSalvare;
	}

	public void setAttivitaDaSalvare(AttivitaSecondariaDTO attivitaDaSalvare) {
		this.attivitaDaSalvare = attivitaDaSalvare;
	}
	
	public List<AttivitaSecondariaDTO> getAttivitaSecondarieEsistentiCompatibili() {
		return attivitaSecondarieEsistentiCompatibili;
	}

	public void setAttivitaSecondarieEsistentiCompatibili(
			List<AttivitaSecondariaDTO> attivitaSecondarieEsistentiCompatibili) {
		this.attivitaSecondarieEsistentiCompatibili = attivitaSecondarieEsistentiCompatibili;
	}
	
	public void handleFileUploadHotel(FileUploadEvent event) {
		this.setFileHotel(event.getFile());
        
        try {
			// Glassfish deve avere i permessi!!
			File path = new File("/var/uploads/up");
			
			String filename = FilenameUtils.getName(fileHotel.getFileName());
			String basename = FilenameUtils.getBaseName(filename) + "_";
			String extension = "." + FilenameUtils.getExtension(filename);

			// tentiamo di creare le cartelle
			System.out.println(path.mkdirs());

			InputStream input;
			try {
				File newFile = File.createTempFile(basename, extension, path);


				input = fileHotel.getInputstream();
				OutputStream output = new FileOutputStream(newFile);
				try {
					IOUtils.copy(input, output);
					tmpImageHotel = ((FilenameUtils.getName(newFile.toString())));
				} finally {
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {

		}
	}
    
	
	// funzione per l'update dell'immagine
	public void handleFileUpload(FileUploadEvent event) {  
		
			 this.setFile(event.getFile());
		        
		        try {
					// Glassfish deve avere i permessi!!
					File path = new File("/var/uploads/up");
					
					String filename = FilenameUtils.getName(file.getFileName());
					String basename = FilenameUtils.getBaseName(filename) + "_";
					String extension = "." + FilenameUtils.getExtension(filename);

					// tentiamo di creare le cartelle
					System.out.println(path.mkdirs());

					InputStream input;
					try {
						File newFile = File.createTempFile(basename, extension, path);


						input = file.getInputstream();
						OutputStream output = new FileOutputStream(newFile);
						try {
							IOUtils.copy(input, output);
							tmpImage = ((FilenameUtils.getName(newFile.toString())));
						} finally {
							IOUtils.closeQuietly(input);
							IOUtils.closeQuietly(output);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} finally {

				}
	
        
    }  

	/* PARTE GESTIONE VOLO IN PACCHETTO */

	public String goToAggiungiVoli(){
		//mi serve l id aggiornato per scrivere nella tabella VoloPacchetto senza causare errori
		//this.pacchetto = pkgMng.salvaInfoGenerali(pacchetto);
		
		//serve per precaricare la tabella di AggiungiVoloEsistente
		if (pacchetto.getInizioValidita().after(pacchetto.getFineValidita()) || pacchetto.getInizioValidita().equals(pacchetto.getFineValidita())){
			
			 return null;
		}
		pacchetto.setImmagine(null);
		try {
			// Glassfish deve avere i permessi!!
			File path = new File("/var/uploads/up");
			
			String filename = FilenameUtils.getName(file.getFileName());
			String basename = FilenameUtils.getBaseName(filename) + "_";
			String extension = "." + FilenameUtils.getExtension(filename);

			// tentiamo di creare le cartelle
			System.out.println(path.mkdirs());

			InputStream input;
			try {
				File newFile = File.createTempFile(basename, extension, path);

				System.out.println(newFile);

				input = file.getInputstream();
				OutputStream output = new FileOutputStream(newFile);
				try {
					IOUtils.copy(input, output);
					pacchetto.setImmagine(FilenameUtils.getName(newFile.toString()));
				} finally {
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
				}
			} catch (IllegalArgumentException | IOException e2) {
				return null;
			}
		} finally {

		}
		
		
		this.voli = voloMng.getVoliDisponibiliECompatibili(this.pacchetto);
		
		return "aggiungiVoli?faces-redirect=true";
	}

	/**
	 * aggiunge volo nuovo a lista voliNuovi distinguendo in andata e ritorno
	 * 
	 * @return
	 * 
	 */
	public String aggiungiVoloNuovoAPacchetto() {
		// mi serve l id aggiornato per scrivere nella tabella VoloPacchetto
		// senza causare errori
		// this.volo = this.voloMng.aggiungiVoloAPacchetto(volo);
		System.out.println(pacchetto.getNome());
		System.out.println(pacchetto.getId());
		System.out.println(volo.getId());
		// controllo il tipo e scelgo di aggiungere il volo all opportuna
		// arraylist

		 if (this.volo.getPartenza().after(this.volo.getArrivo()) || this.volo.getPartenza().equals(this.volo.getArrivo())){
				
				 return null;
			 }
		if (this.tipoVolo.equals("Andata")) {

			// serve solamante per mostrare a schermo
			this.pacchetto.getVoliAndata().add((VoloDTO) this.volo.clone());
			// utile per il eliminaVoloAndata
			this.voliNuoviAndata.add(this.pacchetto.getVoliAndata().get(this.pacchetto.getVoliAndata().size() - 1));

		} else {

			// serve solamante per mostrare a schermo
			this.pacchetto.getVoliRitorno().add((VoloDTO) this.volo.clone());
			// utile per il eliminaVoloRitorno
			this.voliNuoviRitorno.add(this.pacchetto.getVoliRitorno().get(this.pacchetto.getVoliRitorno().size() - 1));
		}
		this.volo = new VoloDTO();
		// this.pkgMng.aggiungiVoloAPacchetto(pacchetto, volo, tipoVolo);

		return "aggiungiVoli?faces-redirect=true";
	}

	/**
	 * aggiunge volo esistente a lista voliEsistenti distinguendo in andata e
	 * ritorno
	 * 
	 * @param volo
	 */
	public void aggiungiVoloEsistenteAPacchetto(VoloDTO volo, int tipo) {
		this.voli.remove(volo);

		if (tipo == 1) {

			// serve solamante per mostrare a schermo
			this.pacchetto.getVoliAndata().add((VoloDTO) volo.clone());
			// utile per il eliminaVoloAndata
			this.voliEsistentiAndata.add(this.pacchetto.getVoliAndata().get(this.pacchetto.getVoliAndata().size() - 1));

		} else {
			// serve solamante per mostrare a schermo
			this.pacchetto.getVoliRitorno().add((VoloDTO) volo.clone());
			// utile per il eliminaVoloRitorno
			this.voliEsistentiRitorno.add(this.pacchetto.getVoliRitorno().get(this.pacchetto.getVoliRitorno().size() - 1));
		}

	}

	/**
	 * funzione chiamate da aggiungiVoli per cancellare i voli di andata immessi
	 * nel pacchetto ma non ancora a db
	 * 
	 * @param volo
	 */
	public void eliminaVoloAndata(VoloDTO volo) {
		if (this.voliEsistentiAndata.contains(volo)) {
			this.voli.add(volo);
			this.voliEsistentiAndata.remove(volo);
		}
		// this.voliEsistentiAndata.remove(volo);
		this.voliNuoviAndata.remove(volo);
		this.pacchetto.getVoliAndata().remove(volo);

	}

	/**
	 * funzione chiamate da aggiungiVoli per cancellare i voli di andata immessi
	 * nel pacchetto ma non ancora a db
	 * 
	 * @param volo
	 */
	public void eliminaVoloRitorno(VoloDTO volo) {
		if (this.voliEsistentiRitorno.contains(volo)) {
			this.voli.add(volo);
			this.voliEsistentiRitorno.remove(volo);
		}
		// this.voliEsistentiRitorno.remove(volo);
		this.voliNuoviRitorno.remove(volo);
		this.pacchetto.getVoliRitorno().remove(volo);

	}

	/* PARTE GESTIONE HOTEL IN PACCHETTO */

	/**
	 * 
	 * visualizza lista hotel esistenti giusta
	 * 
	 * @return
	 */

	public String goToAggiungiHotelEsistente(){
				
		//serve per precaricare la tabella di AggiungiHotelEsistente
		this.listaHotelesistenti = this.hotelMng.getAllHotelCompatibili(this.pacchetto.getLocalita());
		this.listaHotelesistenti.remove(this.hotelSalvato);
		return "aggiungiHotelEsistente?faces-redirect=true";
	}

	/**
	 * ripristina i campi da inserire
	 * 
	 * @return
	 */
	public String goToAggiungiNuovoHotel() {
		this.hotelDTO = new HotelDTO();
		this.hotelDTO.setLuogo(this.pacchetto.getLocalita());
		return "aggiungiNuovoHotel?faces-redirect=true";
	}

	/**
	 * crea un nuovo oggetto hoteldto e lo assegna come hotel da
	 * salvarevisualizza l hotel selezionato
	 * 
	 * @return
	 */
	public String aggiungiNuovoHotelAPacchetto() {
	
		hotelDTO.setPathtoImage(null);
		try {
			// Glassfish deve avere i permessi!!
			File path = new File("/var/uploads/up");
			
			String filename = FilenameUtils.getName(file.getFileName());
			String basename = FilenameUtils.getBaseName(filename) + "_";
			String extension = "." + FilenameUtils.getExtension(filename);

			// tentiamo di creare le cartelle
			System.out.println(path.mkdirs());

			InputStream input;
			try {
				File newFile = File.createTempFile(basename, extension, path);

				System.out.println(newFile);

				input = file.getInputstream();
				OutputStream output = new FileOutputStream(newFile);
				try {
					IOUtils.copy(input, output);
					hotelDTO.setPathtoImage(FilenameUtils.getName(newFile.toString()));
				} finally {
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
				}
			} catch (IllegalArgumentException | IOException e2) {
				return null;
			}
		} finally {

		}
		
		this.hotelSalvato.add((HotelDTO) this.hotelDTO.clone());
		return "aggiungiHotel?faces-redirect=true";
	}

	/**
	 * assegna l hotel esistente scelto come quello da associare al pacchetto
	 * 
	 * @param hotel
	 * @return
	 */
	public String aggiungiHotelesistenteAPacchetto(HotelDTO hotel) {

		this.hotelSalvato.clear();
		this.hotelSalvato.add((HotelDTO) hotel.clone());

		return "aggiungiHotel?faces-redirect=true";
	}

	/**
	 * toglie l hotel da salvare
	 * 
	 * @param hotel
	 */
	public void eliminaHotel(HotelDTO hotel) {

		this.hotelSalvato.clear();
	}

	
	/*PARTE GESTIONE ATTIVITA IN PACCHETTO*/
	
	
	public String goToAggiungiNuovaAttivita(){
		this.attivitaDaSalvare = new AttivitaSecondariaDTO();
		this.attivitaDaSalvare.setLocalita(this.pacchetto.getLocalita());
		return "aggiungiNuovaAttivita?faces-redirect=true";
	}
	
	/**
	 * toglie l attivita da salvare
	 * @param hotel
	 */
	public void eliminaAttivita(AttivitaSecondariaDTO attivita) {		
		this.attivitaSecondarie.remove(attivita);
		
		//se e un attivita esistente la riaggiungo ala lista delle possibili scelte
		if (attivita.getId() != 0){
			this.attivitaSecondarieEsistentiCompatibili.add(attivita);
		}
	}
	
	public String aggiungiNuovaAttivitaAPacchetto(){
		this.attivitaSecondarie.add((AttivitaSecondariaDTO) this.attivitaDaSalvare.clone());
		return "aggiungiAttivita?faces-redirect=true";
	}
	
	 
	public void aggiungiAttivitaEsistenteAPacchetto(AttivitaSecondariaDTO attivita) {
		this.attivitaSecondarieEsistentiCompatibili.remove(attivita);
		// serve solamante per mostrare a schermo
		this.attivitaSecondarie.add(attivita);
		
	}


	/**
	 * step finale in cui salva le info a db
	 * 
	 * @return
	 * @throws ParseException
	 */

	private void aggiungiPacchetto() throws ParseException{
		
		//PRIMO STEP: aggiungo le info generali del pacchetto a db e ricavo l id giudto del pacchetto
		this.pacchetto = pkgMng.salvaInfoGenerali(pacchetto);

		// SECONDO STEP: aggiungo i voli di andata nuovi a db e prendo
		// l id del volo giusto e lo associo a pacchetto
		for (VoloDTO voloNuovoAndata : this.voliNuoviAndata) {
			voloNuovoAndata = this.voloMng.aggiungiVoloAPacchetto(voloNuovoAndata);
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloNuovoAndata, "Andata");
		}

		// TERZO STEP: aggiungo i voli di ritorno nuovi a db e prendo
		// l id del volo giusto e lo associo a pacchetto
		for (VoloDTO voloNuovoRitorno : this.voliNuoviRitorno) {
			voloNuovoRitorno = this.voloMng.aggiungiVoloAPacchetto(voloNuovoRitorno);
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloNuovoRitorno, "Ritorno");
		}

		// QUARTO STEP: associo i voli di andata esistenti al pacchetto
		for (VoloDTO voloEsistenteAndata : this.voliEsistentiAndata) {
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloEsistenteAndata, "Andata");
		}

		// QUINTO STEP: associo i voli di ritorno esistenti al pacchetto
		for (VoloDTO voloEsistenteRitorno : this.voliEsistentiRitorno) {
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloEsistenteRitorno, "Ritorno");
		}
		
		//SESTO STEP: verifico se un hotel e nuovo oppure esistente(id !=0), se nuovo lo inserisco a db
		//e poi lo associo al pacchetto, se esistente mi limito ad associarlo
		if ( this.hotelSalvato.get(0).getId() == 0){

			HotelDTO hotelDaSalvare = this.hotelMng.aggiungiHotelAPacchetto(this.hotelSalvato.get(0));
			this.pkgMng.aggiungiHotelAPacchetto(pacchetto, hotelDaSalvare);
		} else {
			this.pkgMng.aggiungiHotelAPacchetto(pacchetto, this.hotelSalvato.get(0));
		}

		
		// SESTO STEP: verifico se un attivita e nuova oppure esistente(id !=0), se
		// nuova la inserisco a db
		// e poi lo associo al pacchetto, se esistente mi limito ad associarla
		for (AttivitaSecondariaDTO attivitaSecondaria : this.attivitaSecondarie) {
			if (attivitaSecondaria.getId() == 0) {
				attivitaSecondaria = this.attivitalMng.aggiungiAttivitaAPacchetto(attivitaSecondaria);
				this.pkgMng.aggiungiAttivitaAPacchetto(pacchetto, attivitaSecondaria);
			}			
			else {
				this.pkgMng.aggiungiAttivitaAPacchetto(pacchetto, attivitaSecondaria);
			}
		}

		this.reInitProcesso();
		
	}

	/**
	 * reinizializzail processo d creazione del pacchetto
	 */
	private void reInitProcesso() {
		this.pacchetto = new PacchettoDTO();
		this.volo = new VoloDTO();
		this.voli = new ArrayList<VoloDTO>();
		this.setTipoVolo("Andata");
		this.voliNuoviAndata = new ArrayList<VoloDTO>();
		this.voliNuoviRitorno = new ArrayList<VoloDTO>();
		this.setVoliEsistentiAndata(new ArrayList<VoloDTO>());
		this.setVoliEsistentiRitorno(new ArrayList<VoloDTO>());
		this.pacchetto.getVoliAndata().clear();
		this.pacchetto.getVoliRitorno().clear();
		this.hotelDTO = new HotelDTO();
		this.hotelSalvato = new ArrayList<HotelDTO>();
		this.listaHotelesistenti = new ArrayList<HotelDTO>();
		this.pacchetti = new ArrayList<PacchettoDTO>();
		this.pacchettoDaVisualizzareDto = new PacchettoDTO();
		this.attivitaSecondarie = new ArrayList<AttivitaSecondariaDTO>();
		this.attivitaSecondarie = new ArrayList<AttivitaSecondariaDTO>();
		this.attivitaDaSalvare = new AttivitaSecondariaDTO();
		this.pacchetto.setInizioValidita(new Date());
		this.pacchetto.setFineValidita(new Date());
	
	}

	public void mostraInfo(AjaxBehaviorEvent actionEvent, PacchettoDTO pacchetto) {
		
		this.pacchettoDaVisualizzareDto = this.pkgMng.getPacchettoAggiornato(pacchetto);
	
		if (this.pacchettoDaVisualizzareDto.getHotel() == null){
			this.pacchettoDaVisualizzareDto.setHotel(null);
		}
		
	}

	public List<PacchettoDTO> getAllPacchetti() {
		return pkgMng.getAllPacchetti();
	}

	public void onEdit(RowEditEvent event) throws ParseException { 
		   
		pacchetto = (PacchettoDTO) event.getObject();
		 if (pacchetto.getInizioValidita().after(pacchetto.getFineValidita()) || pacchetto.getInizioValidita().equals(pacchetto.getFineValidita())){
			this.pacchetti = this.pkgMng.getAllPacchetti();
			 RequestContext.getCurrentInstance().execute("erroreDate.show()");
			 return;
		 }
		 
	     FacesMessage msg = new FacesMessage("Pacchetto Aggiornato");  
	       
	       
	     System.out.println("IMM " + tmpImage);
	     if(tmpImage != null) {
	    	   pacchetto.setImmagine(tmpImage);
	     }	
	     
	     pkgMng.editInfoGenerali(pacchetto);
	     this.pacchetti = this.pkgMng.getAllPacchetti();
	     this.tmpImage = null;
	      FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }
	
	
	public void deletePacchetto(PacchettoDTO pacchetto){
		pkgMng.deletePacchetto(pacchetto);
		this.pacchetti.remove(pacchetto);
	}
	 
	 public void eliminaVoloAndataDaPacchetto(AjaxBehaviorEvent action, VoloDTO volo){
		 if (this.pacchettoDaVisualizzareDto.getVoliAndata().size() == 1){
			 FacesMessage msg = new FacesMessage("Il pacchetto deve avere almeno un volo di andata");
			 FacesContext.getCurrentInstance().addMessage(null, msg);
		 }
		 else {
			FacesMessage msg = new FacesMessage("Associazione Eliminata");
			pkgMng.eliminaVoloDaPacchetto(this.pacchettoDaVisualizzareDto, volo);
			this.pacchettoDaVisualizzareDto.getVoliAndata().remove(volo);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void eliminaVoloRitornoDaPacchetto(AjaxBehaviorEvent action, VoloDTO volo) {
		if (this.pacchettoDaVisualizzareDto.getVoliRitorno().size() == 1) {
			FacesMessage msg = new FacesMessage("Il pacchetto deve avere almeno un volo di ritorno");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("Associazione Eliminata");
			pkgMng.eliminaVoloDaPacchetto(this.pacchettoDaVisualizzareDto, volo);
			this.pacchettoDaVisualizzareDto.getVoliRitorno().remove(volo);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	 }
	 
	 public void goToAddVoloNuovo(AjaxBehaviorEvent event, PacchettoDTO pacchetto){
		 this.pacchettoDaVisualizzareDto = pacchetto;
		 this.volo = new VoloDTO();
		 System.out.println(this.volo.getNomeCompagnia());
		 this.tipoVolo = "Andata";
	 }
	 
	 public void goToAddVoloEsistente(AjaxBehaviorEvent event, PacchettoDTO pacchetto){
		 this.pacchettoDaVisualizzareDto = this.pkgMng.getPacchettoAggiornato(pacchetto);
		 this.voli = voloMng.getVoliDisponibiliECompatibili(this.pacchettoDaVisualizzareDto);
		 for (VoloDTO voloEsaminato : this.pacchettoDaVisualizzareDto.getVoliAndata()) {		
			 //serve per non mostrare i voli gia inseriti
			 for (Iterator<VoloDTO> voliEsistentIterator = this.voli.iterator(); voliEsistentIterator.hasNext(); ) {
					VoloDTO voloDTO = voliEsistentIterator.next();
					if(voloDTO.getId() == voloEsaminato.getId()){
						voliEsistentIterator.remove();
					}
			 }
		}
		 
		 for (VoloDTO voloEsaminato : this.pacchettoDaVisualizzareDto.getVoliRitorno()) {		
			 //serve per non mostrare i voli gia inseriti
			 for (Iterator<VoloDTO> voliEsistentIterator = this.voli.iterator(); voliEsistentIterator.hasNext(); ) {
					VoloDTO voloDTO = voliEsistentIterator.next();
					if(voloDTO.getId() == voloEsaminato.getId()){
						voliEsistentIterator.remove();
					}
			 }
		}
				
		
	 }
	 
	
	 public void addVoloNuovo() throws ParseException{
		 System.out.println(this.volo.getNomeCompagnia());
		 if (volo.getPartenza().after(volo.getArrivo()) || volo.getPartenza().equals(volo.getArrivo())){
				RequestContext.getCurrentInstance().execute("erroreDate.show()");
				return;
			}
		 this.volo = this.voloMng.aggiungiVoloAPacchetto(this.volo);	
		 pkgMng.aggiungiVoloAPacchetto(this.pacchettoDaVisualizzareDto, this.volo, this.tipoVolo);
		
		 this.volo = new VoloDTO();
		 this.tipoVolo = "Andata";
		 RequestContext.getCurrentInstance().execute("addVoloNuovo.hide()");
		 
	 }
	 
	 public void aggiungiVoloEsistenteAPacchettoEsistente(VoloDTO volo, int tipo) {
			this.voli.remove(volo);

			if (tipo == 1) {				
				//serve solamante per mostrare a schermo
				this.pacchettoDaVisualizzareDto.getVoliAndata().add((VoloDTO) volo.clone());
				pkgMng.aggiungiVoloAPacchetto(this.pacchettoDaVisualizzareDto, volo, "Andata");

			} else {
				//serve solamante per mostrare a schermo
				this.pacchettoDaVisualizzareDto.getVoliRitorno().add((VoloDTO) volo.clone());
				pkgMng.aggiungiVoloAPacchetto(this.pacchettoDaVisualizzareDto, volo, "Ritorno");
			}
			
			
		}
	 
	 public void goToAddHotelNuovo(AjaxBehaviorEvent event, PacchettoDTO pacchetto){
		 this.pacchettoDaVisualizzareDto = pacchetto;
		 this.hotelDTO = new HotelDTO();
		 this.hotelDTO.setLuogo(pacchetto.getLocalita());
		
	 }
	 
	 public void aggiungiNuovoHotelAPacchettoEsistente() throws ParseException{
		 //ricordarsi di aggiungere elimnato!!!!
		 System.out.println(" aggiungiNuovoHotelAPacchettoEsistente");
		 if(tmpImageHotel != null) {
	    	   hotelDTO.setPathtoImage(tmpImageHotel);
	     }	
		 else {
			return;
		}
		 HotelDTO hotelDaSalvare = this.hotelMng.aggiungiHotelAPacchetto(this.hotelDTO);
		 this.pkgMng.aggiungiHotelAPacchetto(this.pacchettoDaVisualizzareDto, hotelDaSalvare);
		 this.hotelDTO = new HotelDTO();
		 this.tmpImageHotel = null;
		 System.out.println(this.hotelDTO.getNome());
		 RequestContext.getCurrentInstance().execute("PF('editHotelNuovoDialog').hide();");
		 
		}
	 
	 public void goToAddHotelEsistente(AjaxBehaviorEvent event, PacchettoDTO pacchetto){
		 this.pacchettoDaVisualizzareDto = pacchetto;
		 this.listaHotelesistenti = hotelMng.getAllHotelCompatibili(this.pacchettoDaVisualizzareDto.getLocalita());
	 }
	 
	 
	 public void aggiungiHotelesistenteAPacchettoEsistente(HotelDTO hotel){
			
			this.pkgMng.aggiungiHotelAPacchetto(this.pacchettoDaVisualizzareDto, hotel);
			this.pacchettoDaVisualizzareDto.setHotel(hotel);
			System.out.println("OK");
		}
		
	 
	 /**
	  * controlla che si abbia inserito almeno un volo di andata e uno di ritorno
	  * nel paccheto
	  * @return
	  */
	 public String checkConsistenzaVoli(){
		 if ( this.pacchetto.getVoliAndata().size()<1 || this.pacchetto.getVoliRitorno().size()<1 ){
			 return "aggiungiVoli?faces-redirect=true";
		 }
		 else {
			return "aggiungiHotel?faces-redirect=true";
		}
	 }
	 
	 /**
	  * controllo che ci cia almeno un hotel
	  * @return
	  * @throws ParseException
	  */
	 public String checkConsistenzaHotel() throws ParseException{
		 if ( this.hotelSalvato.isEmpty() ){
			 return "aggiungiHotel?faces-redirect=true";
		 }
		 
		 else {
			 this.attivitaSecondarieEsistentiCompatibili = attivitalMng.getAttivitaCompatibiliPacchetto(this.pacchetto);
			 return "aggiungiAttivita?faces-redirect=true";
		}
	 }
	 
	 public String checkConsistenzaAttivita() throws ParseException{
		 if ( this.attivitaSecondarie.isEmpty() ){
			 return "aggiungiAttivita?faces-redirect=true";
		 }
		 
		 else {
			//aggiungi pacchetto andra messo dopo quando ci saranno attivita secondarie
			 aggiungiPacchetto();
			 return  "catalogo?faces-redirect=true";
		}
	 }
	 
	 public void eliminaAttivitaDaPacchetto(AjaxBehaviorEvent action, AttivitaSecondariaDTO attivita){
		 if (this.pacchettoDaVisualizzareDto.getAttivitaSecondarie().size() == 1){
			 FacesMessage msg = new FacesMessage("Il pacchetto deve avere almeno un attivita");
			 FacesContext.getCurrentInstance().addMessage(null, msg);
		 }
		 else {
			FacesMessage msg = new FacesMessage("Associazione Eliminata");
			pkgMng.eliminaAttivitaDaPacchetto(this.pacchettoDaVisualizzareDto, attivita);
			this.pacchettoDaVisualizzareDto.getAttivitaSecondarie().remove(attivita);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	 }
	 
	 public void aggiungiNuovaAttivitaAPacchettoEsistente() throws ParseException{
	
		 System.out.println(" aggiungiNuovaAttivitaAPacchettoEsistente");
		 AttivitaSecondariaDTO attivitaDaSalvare = this.attivitalMng.aggiungiAttivitaAPacchetto(this.attivitaDaSalvare);
		 this.pkgMng.aggiungiAttivitaAPacchetto(this.pacchettoDaVisualizzareDto, attivitaDaSalvare);
		 this.attivitaDaSalvare = new AttivitaSecondariaDTO();
		 RequestContext.getCurrentInstance().execute("PF('editAttivitaNuovaDialog').hide();");
		 
		 
		}
	 
	 public void goToAddAttivitaNuova(AjaxBehaviorEvent event, PacchettoDTO pacchetto){
		 this.pacchettoDaVisualizzareDto = pacchetto;
		 this.attivitaDaSalvare = new AttivitaSecondariaDTO();
		 this.attivitaDaSalvare.setLocalita(pacchetto.getLocalita());
		
	 }
	 
	 public void aggiungiAttivitaEsistenteAPacchettoEsistente(AttivitaSecondariaDTO attivita) {
			this.attivitaSecondarieEsistentiCompatibili.remove(attivita);
			this.pacchettoDaVisualizzareDto.getAttivitaSecondarie().add((AttivitaSecondariaDTO) attivita.clone());
			pkgMng.aggiungiAttivitaAPacchetto(this.pacchettoDaVisualizzareDto, attivita);			
			
		}
	 
	 public void goToAddAttivitaEsistente(AjaxBehaviorEvent event, PacchettoDTO pacchetto){
		
		 this.pacchettoDaVisualizzareDto = this.pkgMng.getPacchettoAggiornato(pacchetto);
		 this.attivitaSecondarieEsistentiCompatibili = attivitalMng.getAttivitaCompatibiliPacchetto(pacchetto);
		 
		 //non mostro le attivit gia associate
		 for (AttivitaSecondariaDTO attivitaPacchetto : this.pacchettoDaVisualizzareDto.getAttivitaSecondarie()) {
				
				for (Iterator<AttivitaSecondariaDTO> attivitaEsistente = this.attivitaSecondarieEsistentiCompatibili.iterator(); attivitaEsistente.hasNext(); ) {
					AttivitaSecondariaDTO attivitaSecondariaDTO = attivitaEsistente.next();
					if(attivitaSecondariaDTO.getId() == attivitaPacchetto.getId()){
						attivitaEsistente.remove();
					}
					
				}
			
			}
		 
	 }

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getTmpImage() {
		return tmpImage;
	}

	public void setTmpImage(String tmpImage) {
		this.tmpImage = tmpImage;
	}

	public UploadedFile getFileHotel() {
		return fileHotel;
	}

	public void setFileHotel(UploadedFile fileHotel) {
		this.fileHotel = fileHotel;
	}

	public String getTmpImageHotel() {
		return tmpImageHotel;
	}

	public void setTmpImageHotel(String tmpImageHotel) {
		this.tmpImageHotel = tmpImageHotel;
	}
	
	public String goToGestionePacchetti(){
		this.pacchetti = this.pkgMng.getAllPacchetti();
		return "/dipendente/gestionePacchetto/catalogo.xhtml?faces-redirect=true";
	}
	 

	 
	 
		

}
