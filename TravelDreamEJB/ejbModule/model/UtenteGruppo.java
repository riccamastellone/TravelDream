package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the utenteGruppo database table.
 * 
 */
@Entity
@Table(name="UtenteGruppo")
@NamedQueries({
	@NamedQuery(name="UtenteGruppo.findAll", 
				query="SELECT u FROM UtenteGruppo u"),
				
	@NamedQuery(name="UtenteGruppo.findAllByGroup",
				query="SELECT u FROM UtenteGruppo u WHERE u.gruppo = :gruppo"),
				
	@NamedQuery(name="UtenteGruppo.findByIdUtente",
				query="SELECT u FROM UtenteGruppo u WHERE u.utente = :utente"),
	})
public class UtenteGruppo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String gruppo;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="utente",referencedColumnName = "email")
	private Utente utente;

	public UtenteGruppo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGruppo() {
		return this.gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}