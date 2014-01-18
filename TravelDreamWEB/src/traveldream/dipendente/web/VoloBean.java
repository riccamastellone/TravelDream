package traveldream.dipendente.web;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.VoloMng;

@ManagedBean(name = "voloBean")
@SessionScoped
public class VoloBean {

	@EJB
	private VoloMng voloMng;

	private VoloDTO volo;

	private List<VoloDTO> voli;

	public VoloBean() {
		this.volo = new VoloDTO();
		this.setVoli(new ArrayList<VoloDTO>());
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
		this.voli = new ArrayList<VoloDTO>();
		this.volo = new VoloDTO();
		return "catalogo?faces-redirect=true";

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
	

	public List<VoloDTO> getVoli() {
		if (this.voli.isEmpty()){
		   this.voli = voloMng.getVoli();
		}
		
		return this.voli;
	}

	public void setVoli(List<VoloDTO> voli) {
		this.voli = voli;
	} 
	
	public void deleteVolo(VoloDTO volo) { 
		voloMng.deleteVolo(volo);
		this.voli.remove(volo);	

	}

}
