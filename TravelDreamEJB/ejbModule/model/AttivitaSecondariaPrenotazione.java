package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AttivitaSecondariaPrenotazione database table.
 * 
 */
@Entity
@NamedQuery(name="AttivitaSecondariaPrenotazione.findAll", query="SELECT a FROM AttivitaSecondariaPrenotazione a")
public class AttivitaSecondariaPrenotazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Prenotazione
	@ManyToOne
	@JoinColumn(name="prenotazione")
	private Prenotazione prenotazioneBean;

	//bi-directional many-to-one association to AttivitaSecondaria
	@ManyToOne
	@JoinColumn(name="attivita_secondaria")
	private AttivitaSecondaria attivitaSecondariaBean;

	public AttivitaSecondariaPrenotazione() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Prenotazione getPrenotazioneBean() {
		return this.prenotazioneBean;
	}

	public void setPrenotazioneBean(Prenotazione prenotazioneBean) {
		this.prenotazioneBean = prenotazioneBean;
	}

	public AttivitaSecondaria getAttivitaSecondariaBean() {
		return this.attivitaSecondariaBean;
	}

	public void setAttivitaSecondariaBean(AttivitaSecondaria attivitaSecondariaBean) {
		this.attivitaSecondariaBean = attivitaSecondariaBean;
	}

}