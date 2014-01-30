package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import traveldream.dtos.UtenteDTO;



/**
 * The persistent class for the utente database table.
 * 
 */
@Entity
@Table(name="Utente")
@NamedQueries({
		@NamedQuery(name="Utente.findAll", 
				 	query="SELECT u FROM Utente u"),
		})


public class Utente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String cognome;

	
	@Id
	private String email;

	private String indirizzo;

	private String nome;

	private String password;

	//bi-directional many-to-one association to UtenteGruppo
	@OneToMany(mappedBy="utente")
	private List<UtenteGruppo> utenteGruppos;

	public Utente() {
	}
	
	public Utente(UtenteDTO utente) {
		
		this.nome = utente.getNome();
		this.cognome = utente.getCognome();
		this.email = utente.getEmail();
		this.indirizzo = utente.getIndirizzo();
		this.password = DigestUtils.sha256Hex(utente.getPassword());
		
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UtenteGruppo> getUtenteGruppos() {
		return this.utenteGruppos;
	}

	public void setUtenteGruppos(List<UtenteGruppo> utenteGruppos) {
		this.utenteGruppos = utenteGruppos;
	}

	public UtenteGruppo addUtenteGruppo(UtenteGruppo utenteGruppo) {
		getUtenteGruppos().add(utenteGruppo);
		utenteGruppo.setUtente(this);

		return utenteGruppo;
	}

	public UtenteGruppo removeUtenteGruppo(UtenteGruppo utenteGruppo) {
		getUtenteGruppos().remove(utenteGruppo);
		utenteGruppo.setUtente(null);

		return utenteGruppo;
	}


}