package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the PacchettoCondiviso database table.
 * 
 */
@Entity
@NamedQuery(name="PacchettoCondiviso.findAll", query="SELECT p FROM PacchettoCondiviso p")
public class PacchettoCondiviso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String chiave;

	@Column(name="email_amico")
	private String emailAmico;
	
	@ManyToOne
	@JoinColumn(name="pacchetto",referencedColumnName = "id")
	private int pacchetto;

	
	private String stato;
	
	@JoinColumn(name="utente",referencedColumnName = "email")
	private String utente;

	public PacchettoCondiviso() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChiave() {
		return this.chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getEmailAmico() {
		return this.emailAmico;
	}

	public void setEmailAmico(String emailAmico) {
		this.emailAmico = emailAmico;
	}

	public int getPacchetto() {
		return this.pacchetto;
	}

	public void setPacchetto(int pacchetto) {
		this.pacchetto = pacchetto;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

}