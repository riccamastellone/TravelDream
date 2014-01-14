package traveldream.dipendente.web;

import java.util.ArrayList;
import java.io.Serializable;
import java.text.ParseException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import traveldream.dtos.VoloDTO;
import traveldream.manager.VoloMng;

@ManagedBean(name = "voloBean")
@SessionScoped
public class VoloBean implements Serializable {

	private static final long serialVersionUID = -8696840750297110996L;

	@EJB
	private VoloMng voloMng;

	private VoloDTO volo;

	private ArrayList<VoloDTO> voli;

	public VoloBean() {
		this.volo = new VoloDTO();

	}

	public VoloDTO getVolo() {
		return volo;
	}

	public void setVolo(VoloDTO volo) {
		this.volo = volo;
	}


	public String aggiungiVolo() throws ParseException {
		
		System.out.println(this.volo);
		
		voloMng.salvaVolo(volo);
		this.voli = voloMng.getVoli();
		return "catalogo?faces-redirect=true";

	}


	public ArrayList<VoloDTO> getVoli() {
		
			this.voli = voloMng.getVoli();
		
		return this.voli;
	}


	public void editVolo() throws ParseException {
		VoloDTO voloDTO = this.volo;
		this.volo = new VoloDTO();
		voloMng.aggiornaVolo(voloDTO);
		this.voli = voloMng.getVoli();
		//return "catalogo?faces-redirect=true";
	}

	public void deleteVolo(VoloDTO volo) {
		voloMng.deleteVolo(volo);
		this.voli.remove(volo);
	}
	
	public String indietro() {
		this.volo = new VoloDTO();
		return "catalogo?faces-redirect=true";
	}
	
	 public void onEdit(RowEditEvent event) throws ParseException { 
	       FacesMessage msg = new FacesMessage("Volo Aggiornato");  
	       voloMng.aggiornaVolo((VoloDTO) event.getObject());
	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	    } 
	
	 public void onDelete(RowEditEvent event) {  
	       FacesMessage msg = new FacesMessage("Volo Cancellato");  
	       voloMng.deleteVolo((VoloDTO) event.getObject());
	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	    } 

}
