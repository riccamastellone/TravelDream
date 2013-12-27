package admin.web;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import registrazione.client.UtenteDTO;
import registrazione.client.UtenteMrg;

@ManagedBean(name="listaDipendenti")
public class ListaDipendenti {
	
	@EJB
	private UtenteMrg userMgr;
	
	public ListaDipendenti(){
	}
	
	//prova render lista dipendenti (ho messo amministratore per provare)
	public List<UtenteDTO> getUtenti(){
		return userMgr.getUtentiByGruppi("AMMINISTRATORE");
	}

}
