package admin.web;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import traveldream.dtos.UtenteDTO;
import traveldream.manager.UtenteMrg;


@ManagedBean(name="listaDipendenti")
public class ListaDipendenti {
	
	private ArrayList<UtenteDTO> dipendenti;
	
	
	@EJB
	private UtenteMrg userMgr;
	
	public ListaDipendenti(){
		this.dipendenti = new ArrayList<UtenteDTO>();
	}
	
	//prova render lista dipendenti (ho messo CLIENTE per provare)
	public ArrayList<UtenteDTO> getUtenti(){
		return userMgr.getUtentiByGruppi("DIPENDENTE");
	}
	
	public void edit(){
		//this.user = dipendente;
		System.out.println("tasto premuto");
		//return "admin/edita?faces-redirect=true";
	}

}
