package store.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.PacchettoDTO;

@ManagedBean(name = "pacchettoBeanFrontEnd")
@SessionScoped
public class PacchettoBeanFrontEnd {
	
	private PacchettoDTO pacchettoDaVisualizzare;
	
	public PacchettoBeanFrontEnd(){
		this.pacchettoDaVisualizzare = new PacchettoDTO();
	}

	public PacchettoDTO getPacchettoDaVisualizzare() {
		return pacchettoDaVisualizzare;
	}

	public void setPacchettoDaVisualizzare(PacchettoDTO pacchettoDaVisualizzare) {
		this.pacchettoDaVisualizzare = pacchettoDaVisualizzare;
	}
	
	

}
