package store.web;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.primefaces.context.RequestContext;
import org.primefaces.expression.impl.ThisExpressionResolver;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.PrenotazioneDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.AttivitaMng;
import traveldream.manager.HotelMng;
import traveldream.manager.PrenotazioneMng;
import traveldream.manager.UtenteMrg;


@ManagedBean(name = "bookBean")
@SessionScoped
public class BookBean implements Serializable {

	private static final long serialVersionUID = 2502104564094834379L;

	@EJB
	private PrenotazioneMng prenotazioneMng;
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private HotelMng hotelMng;
	
	@EJB
	private AttivitaMng attivitaMng;
	
	private int persone;
	
	private Date date1;
	
	private Date date2;
	
	private List<VoloDTO> listaVoliAndata;
	
	private List<VoloDTO> listaVoliRitorno;
	
	private VoloDTO voloAndata;
	
	private VoloDTO voloRitorno;
	
	private PrenotazioneDTO prenotazione;
	
	private List<HotelDTO> hotelDisponibili;
	
	private List<AttivitaSecondariaDTO> listaAttivitaSecondarie;
	

	public BookBean(){
		this.voloAndata = new VoloDTO();
		this.voloRitorno = new VoloDTO();
		this.listaVoliAndata = new ArrayList<VoloDTO>();
		this.listaVoliRitorno = new ArrayList<VoloDTO>();
		this.hotelDisponibili = new ArrayList<HotelDTO>();
		this.listaAttivitaSecondarie = new ArrayList<AttivitaSecondariaDTO>();
		
	}
	
	public int getPersone() {
		return persone;
	}

	public void setPersone(int persone) {
		this.persone = persone;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}
	
	public VoloDTO getVoloAndata() {
		return voloAndata;
	}

	public void setVoloAndata(VoloDTO voloAndata) {
		this.voloAndata = voloAndata;
	}

	public VoloDTO getVoloRitorno() {
		return voloRitorno;
	}

	public void setVoloRitorno(VoloDTO voloRitorno) {
		this.voloRitorno = voloRitorno;
	}
	
	public List<VoloDTO> getListaVoliAndata() {
		return listaVoliAndata;
	}

	public void setListaVoliAndata(List<VoloDTO> listaVoliAndata) {
		this.listaVoliAndata = listaVoliAndata;
	}

	public List<VoloDTO> getListaVoliRitorno() {
		return listaVoliRitorno;
	}

	public void setListaVoliRitorno(List<VoloDTO> listaVoliRitorno) {
		this.listaVoliRitorno = listaVoliRitorno;
	}
	
	public PrenotazioneDTO getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(PrenotazioneDTO prenotazione) {
		this.prenotazione = prenotazione;
	}
	
	public List<HotelDTO> getHotelDisponibili() {
		return hotelDisponibili;
	}

	public void setHotelDisponibili(List<HotelDTO> hotelDisponibili) {
		this.hotelDisponibili = hotelDisponibili;
	}
	
	public List<AttivitaSecondariaDTO> getListaAttivitaSecondarie() {
		return listaAttivitaSecondarie;
	}

	public void setListaAttivitaSecondarie(List<AttivitaSecondariaDTO> listaAttivitaSecondarie) {
		this.listaAttivitaSecondarie = listaAttivitaSecondarie;
	}
	
	
	
	public void checkDisponibilitaPacchetto(ActionEvent event, PacchettoDTO pacchetto){
		
		//evito che si ricarichi la lista con i risultati vecchi
		this.listaVoliAndata.clear();
		this.listaVoliRitorno.clear();
		//controllo che ci sia l hotel disponibile
		if ( (pacchetto.getHotel().getDisponibilita() >= this.persone) ){
			
			//controllo che yutte le attivita scelte siano disponibili
			for (AttivitaSecondariaDTO attivita : pacchetto.getAttivitaSecondarie()) {
				if (attivita.getDisponibilita() < this.persone ){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO:", "We are sorry but there is not availability for the combination of people/dates"));
					return;
				}
			}
			
			//controllo che i voli di andata siano disponibili per le date scelte
			for (VoloDTO voloAndata : pacchetto.getVoliAndata()) {
				//controllo che il volo sia disponibile e che sia nel range di date scelte
				if ( (voloAndata.getDisponibilita() >= this.persone) && (voloAndata.getPartenza().after(this.date1)) && (voloAndata.getArrivo().before(this.date2))){
					this.listaVoliAndata.add(voloAndata);
				}
			}
			
			//se non e presente nemmeno un volo disponibile esco
			if (this.listaVoliAndata.isEmpty()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO:", "We are sorry but there is not availability for the combination of people/dates"));
				return;
			}
			
			//controllo che i voli di ritorno siano disponibili per le date scelte
			for (VoloDTO voloRitorno : pacchetto.getVoliRitorno()) {
				//controllo che il volo sia disponibile e che sia nel range di date scelte
				if ( (voloRitorno.getDisponibilita() >= this.persone) && (voloRitorno.getPartenza().after(this.date1)) && (voloRitorno.getArrivo().before(this.date2))){
					this.listaVoliRitorno.add(voloRitorno);
				}
			}
			
			//se non e presente nemmeno un volo disponibile esco
			if (this.listaVoliRitorno.isEmpty()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO:", "We are sorry but there is not availability for the combination of people/dates"));
				return;
			}
			
			
			
			RequestContext.getCurrentInstance().execute("voliDialog.show()");
			return;
		}
		
		else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"INFO:", "We are sorry but there is not availability for the combination of people/dates"));
			return;
		}
		
		
	}
	
	public String prenota(PacchettoDTO pacchetto){
		//System.out.println("tastopremuto");
		//System.out.println(this.voloAndata.getCittaArrivo());
		if (this.voloAndata == null || this.voloRitorno == null){
			RequestContext.getCurrentInstance().execute("errorDialog.show()");
			return null;
		}
		else {
			
			this.prenotazione = new PrenotazioneDTO();
			this.prenotazione.setHotel(pacchetto.getHotel());
			this.prenotazione.setPersone(this.persone);
			this.prenotazione.setUtente(this.userMgr.getUserDTO());
			this.prenotazione.setVoloAndata(this.voloAndata);
			this.prenotazione.setVoloRitorno(this.voloRitorno);
			this.prenotazione.setCostoPersona(132);
			this.prenotazione.setListAttivitaSecondarie(pacchetto.getAttivitaSecondarie());
			this.prenotazioneMng.Prenota(this.prenotazione);
			
			return "prenotazioneOk?faces-redirect=true";
		}
				
	}
	
	public void goToCambiaHotel(ActionEvent event, PacchettoDTO pacchetto) {
		this.hotelDisponibili = this.hotelMng.getAllHotelCompatibili(pacchetto.getLocalita());
		RequestContext.getCurrentInstance().execute("hotelDialog.show()");
	}
	
	public void cambiaHotel(PacchettoDTO pacchetto, HotelDTO hotel) {
		System.out.println("sccs");
		pacchetto.setHotel(hotel);
		
	}
	
	public void goToCambiaAttivita(ActionEvent event, PacchettoDTO pacchetto) {
		this.listaAttivitaSecondarie = this.attivitaMng.getAttivitaCompatibiliPacchetto(pacchetto);
		
		for (AttivitaSecondariaDTO attivitaPacchetto : pacchetto.getAttivitaSecondarie()) {
				
			for (Iterator<AttivitaSecondariaDTO> attivitaEsistente = this.listaAttivitaSecondarie.iterator(); attivitaEsistente.hasNext(); ) {
				AttivitaSecondariaDTO attivitaSecondariaDTO = attivitaEsistente.next();
				if(attivitaSecondariaDTO.getId() == attivitaPacchetto.getId()){
					attivitaEsistente.remove();
				}
				
			}
		
		}
		/*
		for (AttivitaSecondariaDTO attivitaPacchetto : pacchetto.getAttivitaSecondarie()) {
			for (AttivitaSecondariaDTO attivitaEsistente : this.listaAttivitaSecondarie) {
				if (attivitaPacchetto.getId() == attivitaEsistente.getId()){
					this.listaAttivitaSecondarie.remove(attivitaEsistente);
				}
			}
		}
		*/
		
		RequestContext.getCurrentInstance().execute("attivitaDialog2.show()");
	}
	
	public void scegliAttivita(AjaxBehaviorEvent action, AttivitaSecondariaDTO attivita, PacchettoDTO pacchetto){
		
		this.listaAttivitaSecondarie.remove(attivita);
		pacchetto.getAttivitaSecondarie().add(attivita);
		System.out.println("scegli");
		
	 }
	
	public void eliminaAttivita(AjaxBehaviorEvent action, AttivitaSecondariaDTO attivita, PacchettoDTO pacchetto){
		 
		System.out.println("elimina");

		
	 }

	

	

}
