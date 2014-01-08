package traveldream.dipendente.web;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.PacchettoDTO;
import traveldream.manager.PacchettoMng;

@ManagedBean(name = "pacchettoBean")
@SessionScoped
public class PacchettoBean {
	
	@EJB
	private PacchettoMng pkgMng;
	
	
	private PacchettoDTO pacchetto;
	
	
	public PacchettoBean() {
		this.pacchetto = new PacchettoDTO();
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}
	
	
	public String agiungiNuovoVoloAPacchetto() {
		return null;
	}
	
	public String goToAggiungiVoli(){
		pkgMng.salvaInfoGenerali(pacchetto);
		return "aggiungiVoli?faces-redirect=true";
	}

}
