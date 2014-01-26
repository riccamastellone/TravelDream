package store.web;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.primefaces.expression.impl.ThisExpressionResolver;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;

@ManagedBean(name = "pacchettoFrontBean")
@SessionScoped
public class PacchettoFrontBean implements Serializable {

	private static final long serialVersionUID = 250210540456834379L;

	@EJB
	private PacchettoMng pkgMng;
	
	@EJB
	private HotelMng hotelMng;
	
	private List<PacchettoDTO> pacchetti;
	
	private String destinazione;
	private Date data1 = new Date();
	private Date data2 = new Date();
	private int persone = 1;
	
	private List<HotelDTO> listaHotel;
	
	
	
	public PacchettoFrontBean(){
		
		this.pacchetti = new ArrayList<PacchettoDTO>();
		this.listaHotel = new ArrayList<HotelDTO>();
	}
	
	public String getDestinazione() {
		return destinazione;
	}


	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}


	public Date getData1() {
		return data1;
	}


	public void setData1(Date data1) {
		this.data1 = data1;
	}


	public Date getData2() {
		return data2;
	}


	public void setData2(Date data2) {
		this.data2 = data2;
	}


	public int getPersone() {
		return persone;
	}


	public void setPersone(int persone) {
		this.persone = persone;
	}
	
	
	public List<PacchettoDTO> getPacchetti() {
		
		return this.pacchetti;
	}
	
	public List<HotelDTO> getListaHotel() {
		return listaHotel;
	}

	public void setListaHotel(List<HotelDTO> listaHotel) {
		this.listaHotel = listaHotel;
	}
	
	public void refresh() {
	
		this.pacchetti = this.pkgMng.ricercaPacchetto(this.destinazione, this.data1, this.data2, this.persone);
		System.out.println("DDD" + destinazione);
		System.out.println(data1.equals(data2));
		
		System.out.println("TROVARI");
		for (PacchettoDTO pacchettoDTO : this.pacchetti) {
			System.out.println(pacchettoDTO.getNome());
		}
	
		
		
	}


	public String goToListPacchetti(){
		
		this.pacchetti = pkgMng.getAllPacchetti();
		this.destinazione = "";
		 for (Iterator<PacchettoDTO> pacchettidaFiltrare = this.pacchetti.iterator(); pacchettidaFiltrare.hasNext();) {
				PacchettoDTO pacchettoDaControllare = pacchettidaFiltrare.next();
				if (pacchettoDaControllare.getOk().equals("X")) {
					pacchettidaFiltrare.remove();
				}

			}
		return "list?faces-redirect=true";
		
	}
	
	public String goToListHotel(){
		
		this.listaHotel = hotelMng.getAllHotel();
		this.destinazione = "";
		return "listHotel?faces-redirect=true";
		
	}
	
	public String goToRicerca(){
		this.refresh();
		return "list?faces-redirect=true";
	}
	
	public void ricercaHotel() {
		this.listaHotel = this.hotelMng.ricercaHotel(this.destinazione, this.persone);
		System.out.println("DDD" + destinazione);
		System.out.println(data1.equals(data2));
		// for (PacchettoDTO pacchettoDTO : this.pacchetti) {
		// System.out.println(pacchettoDTO.getNome());
		// }

	}
	
	public String goToRicercaHotel(){
		this.ricercaHotel();
		return "listHotel?faces-redirect=true";
	}

	

	

}
