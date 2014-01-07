package model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Prenotazione database table.
 * 
 */
@Entity
@Table(name= "Prenotazione")
@NamedQuery(name="Prenotazione.findAll", query="SELECT p FROM Prenotazione p")
public class Prenotazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="costo_persona")
	private float costoPersona;

	@Column(name="data_creazione")
	private Timestamp dataCreazione;

	private int hotel;

	private int persone;

	private String utente;

	@Column(name="volo_andata")
	private int voloAndata;

	@Column(name="volo_ritorno")
	private int voloRitorno;

	//bi-directional many-to-one association to AttivitaSecondariaPrenotazione
	@OneToMany(mappedBy="prenotazioneBean")
	private List<AttivitaSecondariaPrenotazione> attivitaSecondariePrenotazione;

	public Prenotazione() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCostoPersona() {
		return this.costoPersona;
	}

	public void setCostoPersona(float costoPersona) {
		this.costoPersona = costoPersona;
	}

	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public int getHotel() {
		return this.hotel;
	}

	public void setHotel(int hotel) {
		this.hotel = hotel;
	}

	public int getPersone() {
		return this.persone;
	}

	public void setPersone(int persone) {
		this.persone = persone;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public int getVoloAndata() {
		return this.voloAndata;
	}

	public void setVoloAndata(int voloAndata) {
		this.voloAndata = voloAndata;
	}

	public int getVoloRitorno() {
		return this.voloRitorno;
	}

	public void setVoloRitorno(int voloRitorno) {
		this.voloRitorno = voloRitorno;
	}

	public List<AttivitaSecondariaPrenotazione> getAttivitaSecondariePrenotazione() {
		return this.attivitaSecondariePrenotazione;
	}

	public void setAttivitaSecondariePrenotazione(List<AttivitaSecondariaPrenotazione> attivitaSecondariePrenotazione) {
		this.attivitaSecondariePrenotazione = attivitaSecondariePrenotazione;
	}

	public AttivitaSecondariaPrenotazione addAttivitaSecondariePrenotazione(AttivitaSecondariaPrenotazione attivitaSecondariePrenotazione) {
		getAttivitaSecondariePrenotazione().add(attivitaSecondariePrenotazione);
		attivitaSecondariePrenotazione.setPrenotazioneBean(this);

		return attivitaSecondariePrenotazione;
	}

	public AttivitaSecondariaPrenotazione removeAttivitaSecondariePrenotazione(AttivitaSecondariaPrenotazione attivitaSecondariePrenotazione) {
		getAttivitaSecondariePrenotazione().remove(attivitaSecondariePrenotazione);
		attivitaSecondariePrenotazione.setPrenotazioneBean(null);

		return attivitaSecondariePrenotazione;
	}

}