package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the AttivitaSecondariaPacchetto database table.
 * 
 */
@Entity
@Table(name= "AttivitaSecondariaPacchetto")
@NamedQuery(name="AttivitaSecondariaPacchetto.findAll", query="SELECT a FROM AttivitaSecondariaPacchetto a")
public class AttivitaSecondariaPacchetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to AttivitaSecondaria
	@ManyToOne
	@JoinColumn(name="attivita_secondaria")
	private AttivitaSecondaria attivitaSecondariaBean;

	//bi-directional many-to-one association to Pacchetto
	@ManyToOne
	@JoinColumn(name="pacchetto")
	private Pacchetto pacchettoBean;

	public AttivitaSecondariaPacchetto() {
	}
	
	public AttivitaSecondariaPacchetto(Pacchetto pacchetto, AttivitaSecondaria attivitaSecondaria) {
		
		this.pacchettoBean = pacchetto;		
		this.attivitaSecondariaBean = attivitaSecondaria;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AttivitaSecondaria getAttivitaSecondariaBean() {
		return this.attivitaSecondariaBean;
	}

	public void setAttivitaSecondariaBean(AttivitaSecondaria attivitaSecondariaBean) {
		this.attivitaSecondariaBean = attivitaSecondariaBean;
	}

	public Pacchetto getPacchettoBean() {
		return this.pacchettoBean;
	}

	public void setPacchettoBean(Pacchetto pacchettoBean) {
		this.pacchettoBean = pacchettoBean;
	}

}