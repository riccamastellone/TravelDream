package traveldream.dipendente.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;
import org.primefaces.expression.impl.ThisExpressionResolver;

import com.sun.accessibility.internal.resources.accessibility;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.VoloMng;

@ManagedBean(name = "pacchettoBean")
@SessionScoped
public class PacchettoBean {
	
	@EJB
	private PacchettoMng pkgMng;
	
	@EJB
	private VoloMng voloMng;
	
	@EJB
	private HotelMng hotelMng;
	
	
	private PacchettoDTO pacchetto;
	
	//serve per visualizzare i voli esistenti
	private List<VoloDTO> voli;
	
	//serve per l inserimento a db (creare un nuovo volo nella tabella voli)
	private List<VoloDTO> voliNuoviAndata;
	
	//serve per l inserimento a db (creare un nuovo volo nella tabella voli)
	private List<VoloDTO> voliNuoviRitorno;
	
	//serve per l inserimento a db (NON creare un nuovo volo nella tabella voli ma associare e basta)
	private List<VoloDTO> voliEsistentiAndata;
	
	//serve per l inserimento a db (NON creare un nuovo volo nella tabella voli ma associare e basta)
	private List<VoloDTO> voliEsistentiRitorno;
	
	private VoloDTO volo;	
	
	private String tipoVolo;
	
	private HotelDTO hotelDTO;
	
	private List<HotelDTO> hotelSalvato;
	
	private List<HotelDTO> listaHotelesistenti;
	
	private List<PacchettoDTO> pacchetti;
	
	private PacchettoDTO pacchettoDaVisualizzareDto;
	
	
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
		if (this.pacchetti.isEmpty()) {
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


	

	/*PARTE GESTIONE VOLO IN PACCHETTO*/
	
	public String goToAggiungiVoli(){
		//mi serve l id aggiornato per scrivere nella tabella VoloPacchetto senza causare errori
		//this.pacchetto = pkgMng.salvaInfoGenerali(pacchetto);
		
		//serve per precaricare la tabella di AggiungiVoloEsistente
		this.voli = voloMng.getVoli();
		
		return "aggiungiVoli?faces-redirect=true";
	}
	
	/**
	 * aggiunge volo nuovo a lista voliNuovi distinguendo in andata e ritorno
	 * @return
	 * 
	 */
	public String aggiungiVoloNuovoAPacchetto() {
		//mi serve l id aggiornato per scrivere nella tabella VoloPacchetto senza causare errori
		//this.volo = this.voloMng.aggiungiVoloAPacchetto(volo);
		System.out.println(pacchetto.getNome());
		System.out.println(pacchetto.getId());
		System.out.println(volo.getId());
		//controllo il tipo e scelgo di aggiungere il volo all opportuna arraylist
		
		if (this.tipoVolo.equals("Andata")){
			
			
			//serve solamante per mostrare a schermo
			this.pacchetto.getVoliAndata().add((VoloDTO) this.volo.clone());
			//utile per il eliminaVoloAndata
			this.voliNuoviAndata.add(this.pacchetto.getVoliAndata().get(this.pacchetto.getVoliAndata().size() - 1));
			
		}
		else {
			
			//serve solamante per mostrare a schermo
			this.pacchetto.getVoliRitorno().add((VoloDTO) this.volo.clone());
			//utile per il eliminaVoloRitorno
			this.voliNuoviRitorno.add(this.pacchetto.getVoliRitorno().get(this.pacchetto.getVoliRitorno().size() - 1));
		}
		this.volo = new VoloDTO();
		//this.pkgMng.aggiungiVoloAPacchetto(pacchetto, volo, tipoVolo);
		
		
		
		return "aggiungiVoli?faces-redirect=true";
	}
	
	/**
	 * aggiunge volo esistente a lista voliEsistenti distinguendo in andata e ritorno
	 * @param volo
	 */
	public void aggiungiVoloEsistenteAPacchetto(VoloDTO volo, int tipo) {
		this.voli.remove(volo);

		if (tipo == 1) {

			
			//serve solamante per mostrare a schermo
			this.pacchetto.getVoliAndata().add((VoloDTO) volo.clone());
			//utile per il eliminaVoloAndata
			this.voliEsistentiAndata.add(this.pacchetto.getVoliAndata().get(this.pacchetto.getVoliAndata().size() - 1));

		} else {
			//serve solamante per mostrare a schermo
			this.pacchetto.getVoliRitorno().add((VoloDTO) volo.clone());
			//utile per il eliminaVoloRitorno
			this.voliEsistentiRitorno.add(this.pacchetto.getVoliRitorno().get(this.pacchetto.getVoliRitorno().size() - 1));
		}
		
		
	}
	
	/**
	 * funzione chiamate da aggiungiVoli per cancellare i voli di andata immessi nel pacchetto ma non ancora a db
	 * @param volo
	 */
	public void eliminaVoloAndata(VoloDTO volo){
		if (this.voliEsistentiAndata.contains(volo)){
			this.voli.add(volo);
			this.voliEsistentiAndata.remove(volo);
		}
		//this.voliEsistentiAndata.remove(volo);
		this.voliNuoviAndata.remove(volo);
		this.pacchetto.getVoliAndata().remove(volo);		
		
	}
	
	
	/**
	 * funzione chiamate da aggiungiVoli per cancellare i voli di andata immessi nel pacchetto ma non ancora a db
	 * @param volo
	 */
	public void eliminaVoloRitorno(VoloDTO volo){
		if (this.voliEsistentiRitorno.contains(volo)){
			this.voli.add(volo);
			this.voliEsistentiRitorno.remove(volo);
		}
		//this.voliEsistentiRitorno.remove(volo);
		this.voliNuoviRitorno.remove(volo);
		this.pacchetto.getVoliRitorno().remove(volo);
			
	}
	
	
	
	/*PARTE GESTIONE HOTEL IN PACCHETTO*/
	
	/**
	 * 
	 * visualizza lista hotel esistenti giusta
	 * @return
	 */
	public String goToAggiungiHotelEsistente(){
				
		//serve per precaricare la tabella di AggiungiHotelEsistente
		this.listaHotelesistenti = this.hotelMng.getAllHotel();
		this.listaHotelesistenti.remove(this.hotelSalvato);
		return "aggiungiHotelEsistente?faces-redirect=true";
	}
	
	/**
	 * ripristina i campi da inserire
	 * @return
	 */
	public String goToAggiungiNuovoHotel(){
		this.hotelDTO = new HotelDTO();
		return "aggiungiNuovoHotel?faces-redirect=true";
	}
	
	/**
	 * crea un nuovo oggetto hoteldto e lo assegna come hotel da salvarevisualizza l hotel selezionato
	 * @return
	 */
	public String aggiungiNuovoHotelAPacchetto(){
		//ricordarsi di aggiungere elimnato!!!!
		this.hotelSalvato.add((HotelDTO) this.hotelDTO.clone());
		return "aggiungiHotel?faces-redirect=true";
	}
	
	/**
	 * assegna l hotel esistente scelto come quello da associare al pacchetto
	 * @param hotel
	 * @return
	 */
	public String aggiungiHotelesistenteAPacchetto(HotelDTO hotel){
		
	
		this.hotelSalvato.clear();
		this.hotelSalvato.add((HotelDTO) hotel.clone());
		
		return "aggiungiHotel?faces-redirect=true";
	}
	
	/**
	 * toglie l hotel da salvare
	 * @param hotel
	 */
	public void eliminaHotel(HotelDTO hotel) {
		
		this.hotelSalvato.clear();
	}
	
	/**
	 * step finale in cui salva le info a db
	 * @return
	 * @throws ParseException
	 */
	public String aggiungiPacchetto() throws ParseException{
		
		//PRIMO STEP: aggiungo le info generali del pacchetto a db e ricavo l id giudto del pacchetto
		this.pacchetto = pkgMng.salvaInfoGenerali(pacchetto);
		
		//SECONDO STEP: aggiungo i voli di andata nuovi a db e prendo 
		//              l id del volo giusto e lo associo a pacchetto
		for (VoloDTO voloNuovoAndata : this.voliNuoviAndata) {
			voloNuovoAndata = this.voloMng.aggiungiVoloAPacchetto(voloNuovoAndata);
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloNuovoAndata, "Andata");
		}
		
		//TERZO STEP: aggiungo i voli di ritorno nuovi a db e prendo 
		//            l id del volo giusto e lo associo a pacchetto
		for (VoloDTO voloNuovoRitorno : this.voliNuoviRitorno) {
			voloNuovoRitorno = this.voloMng.aggiungiVoloAPacchetto(voloNuovoRitorno);
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloNuovoRitorno, "Ritorno");
		}
		
		//QUARTO STEP: associo i voli di andata esistenti al pacchetto 
		for (VoloDTO voloEsistenteAndata : this.voliEsistentiAndata) {
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloEsistenteAndata, "Andata");
		}
		
		//QUINTO STEP: associo i voli di ritorno esistenti al pacchetto
		for (VoloDTO voloEsistenteRitorno : this.voliEsistentiRitorno) {
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloEsistenteRitorno, "Ritorno");
		}
		
		//SESTO STEP: verifico se un hotel e nuovo oppure esistente(id !=0), se nuovo lo inserisco a db
		//e poi lo associo al pacchetto, se esistente mi limito ad associarlo
		//verificare se in un db vuoto l id parte da 0
		if ( this.hotelSalvato.get(0).getId() == 0){
			HotelDTO hotelDaSalvare = this.hotelMng.aggiungiHotelAPacchetto(this.hotelSalvato.get(0));
			this.pkgMng.aggiungiHotelAPacchetto(pacchetto, hotelDaSalvare);
		}
		else {
			this.pkgMng.aggiungiHotelAPacchetto(pacchetto, this.hotelSalvato.get(0));
		}
		
		this.reInitProcesso();
		
		return "catalogo?faces-redirect=true";
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
	}
	
	public void mostraInfo(AjaxBehaviorEvent actionEvent, PacchettoDTO pacchetto) {
		System.out.println("tasto premuto");
		//perche non va??
		this.pacchettoDaVisualizzareDto = this.pkgMng.getPacchettoAggiornato(pacchetto);
		System.out.println("ANDATA");
		System.out.println(this.pacchettoDaVisualizzareDto.getHotel().getDescrizione());
		/*
		for (VoloDTO volo : this.pacchettoDaVisualizzareDto.getVoliAndata()) {
			System.out.println(volo.getNomeCompagnia());
		}
		System.out.println("RITORNO");
		for (VoloDTO volo : this.pacchettoDaVisualizzareDto.getVoliRitorno()) {
			System.out.println(volo.getNomeCompagnia());
		}
		*/
	}
	
	public List<PacchettoDTO> getAllPacchetti(){
		//return new ArrayList<PacchettoDTO>();
		System.out.println(pkgMng.getAllPacchetti().toString());
		return pkgMng.getAllPacchetti();
	}
	
	 public void onEdit(RowEditEvent event) throws ParseException { 
	       FacesMessage msg = new FacesMessage("Pacchetto Aggiornato");  
	       pkgMng.editInfoGenerali((PacchettoDTO) event.getObject());
	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	    } 
	
	 public void onDelete(RowEditEvent event) {  
		   FacesMessage msg = new FacesMessage("Pacchetto Aggiornato");  
	       pkgMng.editInfoGenerali((PacchettoDTO) event.getObject());
	       FacesContext.getCurrentInstance().addMessage(null, msg); 
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
	 
	 public void eliminaVoloRitornoDaPacchetto(AjaxBehaviorEvent action, VoloDTO volo){
		 if (this.pacchettoDaVisualizzareDto.getVoliRitorno().size() == 1){
			 FacesMessage msg = new FacesMessage("Il pacchetto deve avere almeno un volo di ritorno");
			 FacesContext.getCurrentInstance().addMessage(null, msg);
		 }
		 else {
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
		 this.pacchettoDaVisualizzareDto = pacchetto;
		 this.voli = voloMng.getVoli();
	 }
	 
	
	 public void addVoloNuovo() throws ParseException{
		 System.out.println(this.volo.getNomeCompagnia());		
		 this.volo = this.voloMng.aggiungiVoloAPacchetto(this.volo);	
		 pkgMng.aggiungiVoloAPacchetto(this.pacchettoDaVisualizzareDto, this.volo, this.tipoVolo);
		
		 this.volo = new VoloDTO();
		 this.tipoVolo = "Andata";
		 
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
		
	 }
	 
	 public void aggiungiNuovoHotelAPacchettoEsistente() throws ParseException{
		 //ricordarsi di aggiungere elimnato!!!!
		 System.out.println(" aggiungiNuovoHotelAPacchettoEsistente");
		 HotelDTO hotelDaSalvare = this.hotelMng.aggiungiHotelAPacchetto(this.hotelDTO);
		 this.pkgMng.aggiungiHotelAPacchetto(pacchetto, hotelDaSalvare);
		 this.hotelDTO = new HotelDTO();
		 
		}
		

}
