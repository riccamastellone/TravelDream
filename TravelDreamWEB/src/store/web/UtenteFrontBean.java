package store.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.ListaDesideriMng;
import traveldream.manager.ListaDesideriDTO;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "utenteFrontBean")
@SessionScoped
public class UtenteFrontBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 781977263206659707L;
	
	private List<ListaDesideriDTO> listaDesideri;
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	public UtenteFrontBean(){
		this.listaDesideri = new ArrayList<ListaDesideriDTO>();
		
	}

	public List<ListaDesideriDTO> getListaDesideri() {
		System.out.println("ricevo");
		return this.listaDesideri = listaDesideriMng.getListaDesisderiUtente(this.userMgr.getUserDTO());
	}

	public void setListaDesideri(List<ListaDesideriDTO> listaDesideri) {
		this.listaDesideri = listaDesideri;
	}
	
	

}
