package traveldream.dipendente.web;

import java.util.ArrayList;
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
public class VoloBean {

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
		if (this.voli == null) {
			this.voli = voloMng.getVoli();
		}
		return this.voli;
	}


	public void editVolo() throws ParseException {
		
		System.out.println("tato premuto");
		VoloDTO voloDTO = this.volo;
		this.volo = new VoloDTO();
		voloMng.aggiornaVolo(voloDTO);
		this.voli = voloMng.getVoli();
		//return "catalogo?faces-redirect=true";
	}

	public void deleteVolo(VoloDTO volo) {
		System.out.println("tasto premuto");
		voloMng.deleteVolo(volo);

	}
	
	public String indietro() {
		this.volo = new VoloDTO();
		return "catalogo?faces-redirect=true";
	}
	
	 public void onEdit(RowEditEvent event) throws ParseException { 
		 System.out.println("fsdfsdfdgsdg");
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
