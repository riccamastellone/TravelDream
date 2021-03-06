package traveldream.dipendente.web;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;
import java.text.ParseException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.VoloMng;

@ManagedBean(name = "voloBean")
@SessionScoped
public class VoloBean implements Serializable {

	private static final long serialVersionUID = -8696840750297110996L;

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


	public String aggiungiVolo() throws ParseException, InterruptedException {
		
		System.out.println(this.volo);
		if (volo.getPartenza().after(volo.getArrivo()) || volo.getPartenza().equals(volo.getArrivo())){
			RequestContext.getCurrentInstance().execute("erroreDate.show()");
			return null;
		}
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
		 
		 VoloDTO voloDaAggiornare = (VoloDTO) event.getObject();
		 if (voloDaAggiornare.getPartenza().after(voloDaAggiornare.getArrivo()) || voloDaAggiornare.getPartenza().equals(voloDaAggiornare.getArrivo())){
			this.voli = this.voloMng.getVoli();
			 RequestContext.getCurrentInstance().execute("erroreDate.show()");
			 return;
		 }
	       FacesMessage msg = new FacesMessage("Volo Aggiornato");  
	       voloMng.aggiornaVolo(voloDaAggiornare);
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
	
	public String goToGestioneVoli(){
		this.voli = voloMng.getVoli();
		return "/dipendente/gestioneVolo/catalogo.xhtml?faces-redirect=true";
	}

}
