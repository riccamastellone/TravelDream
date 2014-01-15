package model;

import java.io.Serializable;

import javax.persistence.*;

import traveldream.dtos.AttivitaSecondariaDTO;

import java.util.List;


/**
 * The persistent class for the AttivitaSecondaria database table.
 * 
 */
@Entity
@Table(name= "AttivitaSecondaria")
@NamedQueries({ 

	@NamedQuery(name="AttivitaSecondaria.findAll", query="SELECT a FROM AttivitaSecondaria a WHERE a.eliminato != 1"),
	
	@NamedQuery(name="AttivitaSecondaria.selectMax", query="SELECT a FROM AttivitaSecondaria a Where a.id = (SELECT max(a.id) FROM AttivitaSecondaria a)"),
	
	@NamedQuery(name="AttivitaSecondaria.getVoloById", query="SELECT a FROM AttivitaSecondaria a Where a.id = :id")

})
public class AttivitaSecondaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private float costo;

	private String descrizione;

	private int disponibilita;

	private String localita;

	private String nome;
	
	private int eliminato;

	//bi-directional many-to-one association to AttivitaSecondariaPacchetto
	@OneToMany(mappedBy="attivitaSecondariaBean")
	private List<AttivitaSecondariaPacchetto> attivitaSecondariePacchetto;

	//bi-directional many-to-one association to AttivitaSecondariaPrenotazione
	@OneToMany(mappedBy="attivitaSecondariaBean")
	private List<AttivitaSecondariaPrenotazione> attivitaSecondariePrenotazione;

	public AttivitaSecondaria() {
	}
	
	
	public AttivitaSecondaria(AttivitaSecondariaDTO a) {
		this.costo = a.getCosto();
		this.descrizione = a.getDescrizione();
		this.disponibilita = a.getDisponibilita();
		this.localita = a.getLocalita();
		this.nome = a.getNome();
		this.eliminato = 0; // non ha senso creare un elemento gia eliminato!
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCosto() {
		return this.costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getDisponibilita() {
		return this.disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public String getLocalita() {
		return this.localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<AttivitaSecondariaPacchetto> getAttivitaSecondariePacchetto() {
		return this.attivitaSecondariePacchetto;
	}

	public void setAttivitaSecondariePacchetto(List<AttivitaSecondariaPacchetto> attivitaSecondariePacchetto) {
		this.attivitaSecondariePacchetto = attivitaSecondariePacchetto;
	}

	public AttivitaSecondariaPacchetto addAttivitaSecondariePacchetto(AttivitaSecondariaPacchetto attivitaSecondariePacchetto) {
		getAttivitaSecondariePacchetto().add(attivitaSecondariePacchetto);
		attivitaSecondariePacchetto.setAttivitaSecondariaBean(this);

		return attivitaSecondariePacchetto;
	}

	public AttivitaSecondariaPacchetto removeAttivitaSecondariePacchetto(AttivitaSecondariaPacchetto attivitaSecondariePacchetto) {
		getAttivitaSecondariePacchetto().remove(attivitaSecondariePacchetto);
		attivitaSecondariePacchetto.setAttivitaSecondariaBean(null);

		return attivitaSecondariePacchetto;
	}

	public List<AttivitaSecondariaPrenotazione> getAttivitaSecondariePrenotazione() {
		return this.attivitaSecondariePrenotazione;
	}

	public void setAttivitaSecondariePrenotazione(List<AttivitaSecondariaPrenotazione> attivitaSecondariePrenotazione) {
		this.attivitaSecondariePrenotazione = attivitaSecondariePrenotazione;
	}

	public AttivitaSecondariaPrenotazione addAttivitaSecondariePrenotazione(AttivitaSecondariaPrenotazione attivitaSecondariePrenotazione) {
		getAttivitaSecondariePrenotazione().add(attivitaSecondariePrenotazione);
		attivitaSecondariePrenotazione.setAttivitaSecondariaBean(this);

		return attivitaSecondariePrenotazione;
	}

	public AttivitaSecondariaPrenotazione removeAttivitaSecondariePrenotazione(AttivitaSecondariaPrenotazione attivitaSecondariePrenotazione) {
		getAttivitaSecondariePrenotazione().remove(attivitaSecondariePrenotazione);
		attivitaSecondariePrenotazione.setAttivitaSecondariaBean(null);

		return attivitaSecondariePrenotazione;
	}

	public int getEliminato() {
		return eliminato;
	}

	public void setEliminato(int eliminato) {
		this.eliminato = eliminato;
	}

}