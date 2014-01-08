package model;

import java.io.Serializable;

import javax.persistence.*;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.PacchettoDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the Pacchetto database table.
 * 
 */
@Entity
@Table(name= "Pacchetto")
@NamedQuery(name = "Pacchetto.findAll", query = "SELECT p FROM Pacchetto p")
public class Pacchetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descrizione;

	@Temporal(TemporalType.DATE)
	@Column(name = "fine_validita")
	private Date fineValidita;

	@ManyToOne
	@JoinColumn(name = "hotel")
	private Hotel hotel;

	private String immagine;

	@Temporal(TemporalType.DATE)
	@Column(name = "inizio_validita")
	private Date inizioValidita;

	private String localita;

	private String nome;

	@OneToMany(mappedBy = "pacchetto")
	private List<VoloPacchetto> voliPacchetto;

	// bi-directional many-to-one association to AttivitaSecondariaPacchetto
	@OneToMany(mappedBy = "pacchettoBean")
	private List<AttivitaSecondariaPacchetto> attivitaSecondariePacchetto;

	public Pacchetto() {
	}
	
	public Pacchetto(PacchettoDTO pacchetto) {
		
		this.descrizione = pacchetto.getDescrizione();
		this.nome = pacchetto.getNome();
		this.inizioValidita = pacchetto.getInizioValidita();
		this.fineValidita = pacchetto.getFineValidita();
		this.localita = pacchetto.getLocalita();
		
	}	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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

	public List<AttivitaSecondariaPacchetto> getAttivitaSecondariePacchetto() {
		return this.attivitaSecondariePacchetto;
	}

	public void setAttivitaSecondariePacchetto(
			List<AttivitaSecondariaPacchetto> attivitaSecondariePacchetto) {
		this.attivitaSecondariePacchetto = attivitaSecondariePacchetto;
	}

	public AttivitaSecondariaPacchetto addAttivitaSecondariePacchetto(
			AttivitaSecondariaPacchetto attivitaSecondariePacchetto) {
		getAttivitaSecondariePacchetto().add(attivitaSecondariePacchetto);
		attivitaSecondariePacchetto.setPacchettoBean(this);

		return attivitaSecondariePacchetto;
	}

	public AttivitaSecondariaPacchetto removeAttivitaSecondariePacchetto(
			AttivitaSecondariaPacchetto attivitaSecondariePacchetto) {
		getAttivitaSecondariePacchetto().remove(attivitaSecondariePacchetto);
		attivitaSecondariePacchetto.setPacchettoBean(null);

		return attivitaSecondariePacchetto;
	}
	
	
	/*
	/**
	 * serve al costruttore per convertire il pacchettoDTO
	 * @param attivitaSecondarieDTO
	 * @return
	 */
	/*
	public List<AttivitaSecondaria> convertiAttivitaSecondarie(List<AttivitaSecondariaDTO> attivitaSecondarieDTO) {
		
		List<AttivitaSecondaria> attivitaSecondarie = new ArrayList<AttivitaSecondaria>();
		
		for (AttivitaSecondariaDTO attivitaDTO : attivitaSecondarieDTO) {
			AttivitaSecondaria attivita = new AttivitaSecondaria(attivitaDTO);
			attivitaSecondarie.add(attivita);
		}

		return attivitaSecondarie;
	}
	*/

}