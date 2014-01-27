package model;

import java.io.Serializable;

import javax.persistence.*;

import traveldream.dtos.PrenotazioneDTO;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Prenotazione database table.
 * 
 */
@Entity
@Table(name= "Prenotazione")

@NamedQueries({
		
	@NamedQuery(name="Prenotazione.findAll", query="SELECT p FROM Prenotazione p"),
	
	@NamedQuery(name= "Prenotazione.selectMax", query= "SELECT p FROM Prenotazione p WHERE p.id = (SELECT max(p.id) FROM Prenotazione p)"),
	@NamedQuery(name= "Prenotazione.getByUtente", query= "SELECT p FROM Prenotazione p WHERE p.utente = :utente")

})
public class Prenotazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="costo_persona")
	private float costoPersona;

	@Column(name="data_creazione")
	private Timestamp dataCreazione;

	@ManyToOne
	@JoinColumn(name = "hotel")
	private Hotel hotel;

	private int persone;

	@ManyToOne
	@JoinColumn(name="utente",referencedColumnName = "email")
	private Utente utente;

	
	@ManyToOne
	@JoinColumn(name = "volo_andata")
	private Volo voloAndata;

	
	@ManyToOne
	@JoinColumn(name = "volo_ritorno")
	private Volo voloRitorno;

	//bi-directional many-to-one association to AttivitaSecondariaPrenotazione
	@OneToMany(mappedBy="prenotazioneBean")
	private List<AttivitaSecondariaPrenotazione> attivitaSecondariePrenotazione;

	public Prenotazione(){}
	
	public Prenotazione(PrenotazioneDTO prenotazione) {
		
		this.costoPersona = prenotazione.getCostoPersona();
		this.persone = prenotazione.getPersone();
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

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public int getPersone() {
		return this.persone;
	}

	public void setPersone(int persone) {
		this.persone = persone;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Volo getVoloAndata() {
		return this.voloAndata;
	}

	public void setVoloAndata(Volo voloAndata) {
		this.voloAndata = voloAndata;
	}

	public Volo getVoloRitorno() {
		return this.voloRitorno;
	}

	public void setVoloRitorno(Volo voloRitorno) {
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