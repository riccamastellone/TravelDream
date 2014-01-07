package traveldream.dipendente.web;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import traveldream.dtos.VoloDTO;
import traveldream.gestioneComponente.ComponenteMng;

@ManagedBean(name = "voloBean")
@SessionScoped
public class VoloBean {

	@EJB
	private ComponenteMng cmpMng;

	private VoloDTO volo;

	private String dataPartenza;

	private String dataArrivo;

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

	public String getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(String dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public String getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(String dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public String aggiungiVolo() throws ParseException {
		this.volo.setPartenza(this.converti(dataPartenza));
		this.volo.setArrivo(this.converti(dataArrivo));
		System.out.println(this.volo);
		this.volo.printaDati();
		cmpMng.salvaVolo(volo);
		this.voli = cmpMng.getVoli();
		return "catalogo?faces-redirect=true";

	}

	public Date converti(String data) throws ParseException {
		if (data.equals("")) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date convertedDate = dateFormat.parse(data);
		return convertedDate;
	}

	public ArrayList<VoloDTO> getVoli() {
		if (this.voli == null) {
			this.voli = cmpMng.getVoli();
		}
		return this.voli;
	}
	
	
	public String goToEdit(VoloDTO volo){

		this.volo = volo;
		DateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
		this.dataArrivo = formato.format(this.volo.getArrivo());
		this.dataPartenza = formato.format(this.volo.getPartenza());
		return "edita?faces-redirect=true";

	}

	public String editVolo() throws ParseException {
		System.out.println("tato premuto");
		VoloDTO voloDTO = this.volo;
		voloDTO.setArrivo(this.converti(this.dataArrivo));
		voloDTO.setPartenza(this.converti(this.dataPartenza));
		this.volo = new VoloDTO();
		this.dataArrivo = null;
		this.dataPartenza = null;
		cmpMng.aggiornaVolo(voloDTO);
		this.voli = cmpMng.getVoli();
		return "catalogo?faces-redirect=true";
	}

	public void deleteVolo(VoloDTO volo) {
		System.out.println("tasto premuto");
		cmpMng.deleteVolo(volo);

	}
	
	public String indietro() {
		this.volo = new VoloDTO();
		this.dataArrivo = null;
		this.dataPartenza = null;
		return "catalogo?faces-redirect=true";
	}
	

}
