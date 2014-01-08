package traveldream.dipendente.web;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.RowEditEvent;

import traveldream.dtos.VoloDTO;
import traveldream.gestioneComponente.ComponenteMng;

@ManagedBean(name = "voloBean")
@SessionScoped
public class VoloBean {

	@EJB
	private ComponenteMng cmpMng;

	private VoloDTO volo;

	private ArrayList<VoloDTO> voli;

	public VoloBean() {
		this.volo = new VoloDTO();
		// this.elencoVoli = cmpMng.getVoli();

	}

	public VoloDTO getVolo() {
		return volo;
	}

	public void setVolo(VoloDTO volo) {
		this.volo = volo;
	}


	public String aggiungiVolo() throws ParseException {
		
		System.out.println(this.volo);
		
		cmpMng.salvaVolo(volo);
		this.voli = cmpMng.getVoli();
		return "catalogo?faces-redirect=true";

	}


	public ArrayList<VoloDTO> getVoli() {
		if (this.voli == null) {
			this.voli = cmpMng.getVoli();
		}
		return this.voli;
	}
	
	/*
	public String goToEdit(VoloDTO volo){

		this.volo = volo;
		DateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
		this.dataArrivo = formato.format(this.volo.getArrivo());
		this.dataPartenza = formato.format(this.volo.getPartenza());
		return "edita?faces-redirect=true";

	}
	*/

	public void editVolo() throws ParseException {
		
		System.out.println("tato premuto");
		VoloDTO voloDTO = this.volo;
		this.volo = new VoloDTO();
		cmpMng.aggiornaVolo(voloDTO);
		this.voli = cmpMng.getVoli();
		//return "catalogo?faces-redirect=true";
	}

	public void deleteVolo(VoloDTO volo) {
		System.out.println("tasto premuto");
		cmpMng.deleteVolo(volo);

	}
	
	public String indietro() {
		this.volo = new VoloDTO();
		return "catalogo?faces-redirect=true";
	}
	
	 public void onEdit(RowEditEvent event) throws ParseException { 
		 System.out.println("fsdfsdfdgsdg");
	       FacesMessage msg = new FacesMessage("Volo Aggiornato");  
	       cmpMng.aggiornaVolo((VoloDTO) event.getObject());
	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	    } 
	
	 public void onDelete(RowEditEvent event) {  
	       FacesMessage msg = new FacesMessage("Volo Cancellato");  
	       cmpMng.deleteVolo((VoloDTO) event.getObject());
	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	    } 

}
