package traveldream.dipendente.web;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sun.el.parser.ParseException;

import traveldream.dtos.HotelDTO;
import traveldream.gestioneComponente.ComponenteMng;

@ManagedBean(name = "hotelBean")
@SessionScoped
public class HotelBean {

	@EJB
	private ComponenteMng cmpMng;

	private HotelDTO hotel;

	private ArrayList<HotelDTO> allHotel;

	public HotelBean() {
		this.setHotel(new HotelDTO());
	}


	public String aggiungiHotel() throws ParseException {
		cmpMng.salvaHotel(hotel);
		refreshHotels();
		return "catalogo?faces-redirect=true";

	} 
	
	private void refreshHotels() {
		this.allHotel = cmpMng.getAllHotel();
	}


	public ArrayList<HotelDTO> getAllHotel() {
		if (this.allHotel == null) {
			refreshHotels();
		}
		return this.allHotel;
	}
	/*
	
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
	}*/

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	

}
