package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ListaDesisderi database table.
 * 
 */
@Entity
@Table(name = "ListaDesideri")
@NamedQueries({ 
	
	@NamedQuery(name="ListaDesideri.findAll", query="SELECT l FROM ListaDesideri l"),
	
	@NamedQuery(name="ListaDesideri.getListaDesideriByPacchettoUtente", query="SELECT l FROM ListaDesideri l WHERE l.pacchetto = :pacchetto AND l.utente = :utente"),
})
public class ListaDesideri implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@ManyToOne
	@JoinColumn(name="pacchetto")
	private Pacchetto pacchetto;

	@Column(name="pagato_da")
	private String pagatoDa;

	@ManyToOne
	@JoinColumn(name="utente")
	private Utente utente;

	public ListaDesideri() {
	}
	
	public ListaDesideri(Pacchetto pacchetto, Utente utente ) {
		
		this.utente = utente;
		this.pacchetto = pacchetto;
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pacchetto getPacchetto() {
		return this.pacchetto;
	}

	public void setPacchetto(Pacchetto pacchetto) {
		this.pacchetto = pacchetto;
	}

	public String getPagatoDa() {
		return this.pagatoDa;
	}

	public void setPagatoDa(String pagatoDa) {
		this.pagatoDa = pagatoDa;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}