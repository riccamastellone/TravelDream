package store.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;







import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import traveldream.dtos.ListaDesideriMng;
import traveldream.dtos.PacchettoDTO;
import traveldream.manager.ListaDesideriDTO;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "listaDesideriBean")
@SessionScoped
public class ListaDesisderiBean implements Serializable{
	
	
	private static final long serialVersionUID = -4029975305697609232L;
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	public ListaDesisderiBean(){
		
	}
	
	public void addAListaDesideri(PacchettoDTO pacchetto){
		
		ListaDesideriDTO lista = new ListaDesideriDTO();
		lista.setUtente(this.userMgr.getUserDTO());
		lista.setPacchetto(pacchetto);
		this.listaDesideriMng.addAListaDesideri(lista);
		System.out.println(pacchetto.getNome());
		
	}

}
