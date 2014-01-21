package store.web;


import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.PacchettoDTO;
import traveldream.manager.PacchettoMng;

@ManagedBean(name = "pacchettoFrontBean")
@SessionScoped
public class PacchettoFrontBean implements Serializable {

	private static final long serialVersionUID = 250210540456834379L;

	@EJB
	private PacchettoMng pkgMng;
	
	private List<PacchettoDTO> pacchetti;
	
	private String destinazione;
	private Date data1 = new Date();
	private Date data2 = new Date();
	private int persone = 1;
	private int stelle;
	private int costo1;
	private int costo2;

	
	public PacchettoFrontBean(){
	}
	
	
	public List<PacchettoDTO> getPacchetti() {

		if (this.pacchetti.isEmpty()){
			  this.pacchetti = pkgMng.getAllPacchetti();
		}
		
		return this.pacchetti;
	}
	
	public void refresh() {
		System.out.println("DDD" + destinazione);
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


	public int getStelle() {
		return stelle;
	}


	public void setStelle(int stelle) {
		this.stelle = stelle;
	}


	public int getCosto1() {
		return costo1;
	}


	public void setCosto1(int costo1) {
		this.costo1 = costo1;
	}


	public int getCosto2() {
		return costo2;
	}


	public void setCosto2(int costo2) {
		this.costo2 = costo2;
	}
	
	

}
