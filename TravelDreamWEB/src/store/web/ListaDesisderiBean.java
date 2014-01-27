package store.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.ListaDesideriDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.UtenteDTO;
import traveldream.manager.ListaDesideriMng;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "listaDesideriBean")
@SessionScoped
public class ListaDesisderiBean implements Serializable{
	
	
	private static final long serialVersionUID = -4029975305697609232L;
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	private List<ListaDesideriDTO> listaDesideriUtente;
	
	private String utenteProprietario ="";
	
	public ListaDesisderiBean(){
		
		this.listaDesideriUtente = new ArrayList<ListaDesideriDTO>();
		
	}
	
	public void addAListaDesideri(PacchettoDTO pacchetto){
		
		ListaDesideriDTO lista = new ListaDesideriDTO();
		lista.setUtente(this.userMgr.getUserDTO());
		lista.setPacchetto(pacchetto);
		this.listaDesideriMng.addAListaDesideri(lista);
		System.out.println(pacchetto.getNome());
		
	}
	
	
	public void getListaDesideriUtenteProprietario(){
		this.listaDesideriUtente.clear();
		UtenteDTO utente = new UtenteDTO();
		utente.setEmail(this.utenteProprietario);
		List<ListaDesideriDTO> listaDaScremare = new ArrayList<ListaDesideriDTO>(); 
		listaDaScremare = this.listaDesideriMng.getListaDesisderiUtente(utente);
		
		for (ListaDesideriDTO listaDesideriDTO : listaDaScremare) {
			if (listaDesideriDTO.getPagatoDa() == null ){
				this.listaDesideriUtente.add(listaDesideriDTO);
			}
		}
		
		for (Iterator<ListaDesideriDTO> s = this.listaDesideriUtente.iterator(); s.hasNext(); ) {
			
			ListaDesideriDTO listaDaControllare = s.next();
			if (listaDaControllare.getPacchetto().getOk().equals("X") || listaDaControllare.getPacchetto().getEliminato() == 1) {
				s.remove();
			}
		}
		
		this.listaDesideriUtente.size();
		
	}

	

	public List<ListaDesideriDTO> getListaDesideriUtente() {
		
			this.getListaDesideriUtenteProprietario();
		
		return listaDesideriUtente;
	}

	public void setListaDesideriUtente(List<ListaDesideriDTO> listaDesideriUtente) {
		this.listaDesideriUtente = listaDesideriUtente;
	}

	public String getUtenteProprietario() {
		return utenteProprietario;
	}

	public void setUtenteProprietario(String utenteProprietario) {
		this.utenteProprietario = utenteProprietario;
	}
	
	public void pagaPacchetto(ListaDesideriDTO lista){
		System.out.println("pagato");
		this.listaDesideriMng.pagaPacchttoInListaDesideri(lista, "bruno");
	}

}
