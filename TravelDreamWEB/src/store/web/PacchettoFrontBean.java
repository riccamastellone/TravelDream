package store.web;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.primefaces.expression.impl.ThisExpressionResolver;

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
	
	
	
	public PacchettoFrontBean(){
		
		this.pacchetti = new ArrayList<PacchettoDTO>();
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
	
	public void refresh() {
		
		if (destinazione.equals("")){
			this.destinazione="%";
		}
		
		this.pacchetti = this.pkgMng.ricercaPacchetto(this.destinazione, this.data1, this.data2, this.persone);
		System.out.println("DDD" + destinazione);
		System.out.println(data1.equals(data2));
		//for (PacchettoDTO pacchettoDTO : this.pacchetti) {
		//	System.out.println(pacchettoDTO.getNome());
		//}
	
		
		
	}


	public String goToListPacchetti(){
		
		this.pacchetti = pkgMng.getAllPacchetti();
		this.destinazione = "";
		return "list?faces-redirect=true";
		
	}

	

}
