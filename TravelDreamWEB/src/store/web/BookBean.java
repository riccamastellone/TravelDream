package store.web;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.expression.impl.ThisExpressionResolver;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.VoloMng;

@ManagedBean(name = "bookBean")
@SessionScoped
public class BookBean implements Serializable {

	private static final long serialVersionUID = 2502104564094834379L;

	@EJB
	private PacchettoMng pkgMng;

	@EJB
	private HotelMng hotelMng;

	@EJB
	private VoloMng voloMng;
	
	private int persone;
	
	private Date date1;
	
	private Date date2;
	
	private List<VoloDTO> listaVoliAndata;
	
	private List<VoloDTO> listaVoliRitorno;
	
	private VoloDTO voloAndata;
	
	private VoloDTO voloRitorno;
	

	public BookBean(){
		this.voloAndata = new VoloDTO();
		this.voloRitorno = new VoloDTO();
		this.listaVoliAndata = new ArrayList<VoloDTO>();
		this.listaVoliRitorno = new ArrayList<VoloDTO>();
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
			return "prenotazioneOk?faces-redirect=true";
		}
		
		
	}



}
