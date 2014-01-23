package traveldream.manager;


import javax.persistence.Id;

import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.UtenteDTO;



public class ListaDesideriDTO {
	
	@Id
	private int id;

	
	private PacchettoDTO pacchetto;

	
	private String pagatoDa;

	
	private UtenteDTO utente;

	public ListaDesideriDTO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PacchettoDTO getPacchetto() {
		return this.pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	public String getPagatoDa() {
		return this.pagatoDa;
	}

	public void setPagatoDa(String pagatoDa) {
		this.pagatoDa = pagatoDa;
	}

	public UtenteDTO getUtente() {
		return this.utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

}
