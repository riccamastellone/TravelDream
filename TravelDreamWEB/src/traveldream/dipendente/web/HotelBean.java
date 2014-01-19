
package traveldream.dipendente.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import com.sun.el.parser.ParseException;

import traveldream.dtos.HotelDTO;
import traveldream.manager.HotelMng;

@ManagedBean(name = "hotelBean")
@SessionScoped
public class HotelBean implements Serializable{

	private static final long serialVersionUID = -3072123322577207286L;

	@EJB
	private HotelMng hotelMng;

	private HotelDTO hotel;

	private ArrayList<HotelDTO> allHotel;

	private UploadedFile file;

	public HotelBean() {
		this.setHotel(new HotelDTO());
		this.allHotel = new ArrayList<HotelDTO>();
	}


	private void refreshHotels() {
		this.allHotel = hotelMng.getAllHotel();
	}

	public ArrayList<HotelDTO> getAllHotel() {
		if (this.allHotel.isEmpty()) {
			refreshHotels();
		}
		return this.allHotel;
	}

	public String goToEdit(HotelDTO hotel) {
		this.hotel = hotel;
		return "edita?faces-redirect=true";

	}

	public String aggiornaHotel() throws ParseException {
		
		HotelDTO hotelDTO = this.hotel;
		this.hotel = new HotelDTO();
		
		
		try {
			// Glassfish deve avere i permessi!!
			File path = new File("/var/uploads/up");
			String filename = FilenameUtils.getName(file.getFileName());
			
			String basename = FilenameUtils.getBaseName(filename) + "_";
			String extension = "." + FilenameUtils.getExtension(filename);

			// tentiamo di creare le cartelle
			System.out.println(path.mkdirs());
			
			

			InputStream input;
			try {
				File newFile = File.createTempFile(basename, extension, path);

				input = file.getInputstream();
				OutputStream output = new FileOutputStream(newFile);
				try {
					IOUtils.copy(input, output);
					hotelDTO.setPathtoImage(FilenameUtils.getName(newFile.toString()));
				} finally {
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch(IllegalArgumentException e) {
			hotelDTO.setPathtoImage(null);
		} finally {
			
		}
		
		hotelMng.aggiornaHotel(hotelDTO);
		refreshHotels();
		return "catalogo?faces-redirect=true";
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String aggiungiHotel() {
		hotel.setPathtoImage(null);
		try {
			// Glassfish deve avere i permessi!!
			File path = new File("/var/uploads/up");
			String filename = FilenameUtils.getName(file.getFileName());
			String basename = FilenameUtils.getBaseName(filename) + "_";
			String extension = "." + FilenameUtils.getExtension(filename);

			// tentiamo di creare le cartelle
			System.out.println(path.mkdirs());

			InputStream input;
			try {
				File newFile = File.createTempFile(basename, extension, path);

				System.out.println(newFile);

				input = file.getInputstream();
				OutputStream output = new FileOutputStream(newFile);
				try {
					IOUtils.copy(input, output);
					hotel.setPathtoImage(FilenameUtils.getName(newFile.toString()));
				} finally {
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {

		}

		hotelMng.salvaHotel(hotel);
		this.hotel = new HotelDTO();
		refreshHotels();
		return "catalogo?faces-redirect=true";

	}

	public String indietro() {
		this.hotel = new HotelDTO();
		return "catalogo?faces-redirect=true";
	}

	public void deleteHotel(HotelDTO hotel) {
		hotelMng.deleteHotel(hotel);
		this.allHotel.remove(hotel);

	}
	
	 public void onEdit(RowEditEvent event) throws ParseException { 
	       FacesMessage msg = new FacesMessage("Hotel Aggiornato");  
	       hotelMng.aggiornaHotel((HotelDTO) event.getObject());
	       FacesContext.getCurrentInstance().addMessage(null, msg);  
	    } 
	


}
