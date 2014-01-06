package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Pacchetto database table.
 * 
 */
@Entity
@NamedQuery(name="Pacchetto.findAll", query="SELECT p FROM Pacchetto p")
public class Pacchetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private Object descrizione;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	private String immagine;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	private String localita;

	private String nome;

	//bi-directional many-to-one association to Hotel
	@ManyToOne
	@JoinColumn(name="id_hotel")
	private Hotel hotel;


	//bi-directional many-to-one association to VoloPacchetto
	@OneToMany(mappedBy="pacchetto")
	private List<VoloPacchetto> voliPacchetto;

	public Pacchetto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(Object descrizione) {
		this.descrizione = descrizione;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public String getImmagine() {
		return this.immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
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

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public List<VoloPacchetto> getVoliPacchetto() {
		return this.voliPacchetto;
	}

	public void setVoliPacchetto(List<VoloPacchetto> voliPacchetto) {
		this.voliPacchetto = voliPacchetto;
	}

	public VoloPacchetto addVoloPacchetto(VoloPacchetto voloPacchetto) {
		getVoliPacchetto().add(voloPacchetto);
		voloPacchetto.setPacchetto(this);

		return voloPacchetto;
	}

	public VoloPacchetto removeVoloPacchetto(VoloPacchetto voloPacchetto) {
		getVoliPacchetto().remove(voloPacchetto);
		voloPacchetto.setPacchetto(null);

		return voloPacchetto;
	}

}