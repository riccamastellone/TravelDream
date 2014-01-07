package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the VoloPacchetto database table.
 * 
 */
@Entity
@Table(name= "VoloPacchetto")
@NamedQuery(name="VoloPacchetto.findAll", query="SELECT v FROM VoloPacchetto v")
public class VoloPacchetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Pacchetto
	@ManyToOne
	@JoinColumn(name="pacchetto")
	private Pacchetto pacchetto;

	//bi-directional many-to-one association to Volo
	@ManyToOne
	@JoinColumn(name="volo")
	private Volo volo;

	public VoloPacchetto() {
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

	public Volo getVolo() {
		return this.volo;
	}

	public void setVolo(Volo volo) {
		this.volo = volo;
	}

}