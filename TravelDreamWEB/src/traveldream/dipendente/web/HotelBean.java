package traveldream.dipendente.web;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.sun.el.parser.ParseException;

import traveldream.dtos.HotelDTO;
import traveldream.manager.HotelMng;

@ManagedBean(name = "hotelBean")
@SessionScoped
public class HotelBean {

	@EJB
	private HotelMng hotelMng;

	private HotelDTO hotel;

	private ArrayList<HotelDTO> allHotel;

	public HotelBean() {
		this.setHotel(new HotelDTO());
	}


	public String aggiungiHotel() throws ParseException {
		hotelMng.salvaHotel(hotel);
		refreshHotels();
		return "catalogo?faces-redirect=true";

	} 
	
	private void refreshHotels() {
		this.allHotel = hotelMng.getAllHotel();
	}


	public ArrayList<HotelDTO> getAllHotel() {
		if (this.allHotel == null) {
			refreshHotels();
		}
		return this.allHotel;
	}
	
	
	public String goToEdit(HotelDTO hotel){
		this.hotel = hotel;
		return "edita?faces-redirect=true";

	}

	public String aggiornaHotel() throws ParseException {
		HotelDTO hotelDTO = this.hotel;
		this.hotel = new HotelDTO();
		hotelMng.aggiornaHotel(hotelDTO);
		refreshHotels();
		return "catalogo?faces-redirect=true";
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}


	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	

}
