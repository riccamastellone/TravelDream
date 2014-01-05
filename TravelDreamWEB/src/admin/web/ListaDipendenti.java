package admin.web;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import registrazione.client.UtenteDTO;
import registrazione.client.UtenteMrg;

@ManagedBean(name="listaDipendenti")
public class ListaDipendenti {
	
	private ArrayList<UtenteDTO> dipendenti;
	
	@EJB
	private UtenteMrg userMgr;
	
	public ListaDipendenti(){
		this.dipendenti = new ArrayList<UtenteDTO>();
		UtenteDTO pippoDto = new UtenteDTO();
		pippoDto.setCognome("strati");
		pippoDto.setNome("bruno");
		this.dipendenti.add(pippoDto);
	}
	
	//prova render lista dipendenti (ho messo CLIENTE per provare)
	public ArrayList<UtenteDTO> getUtenti(){
		return userMgr.getUtentiByGruppi("CLIENTE");
	}

}
