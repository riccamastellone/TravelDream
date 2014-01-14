package model;

import java.io.Serializable;

import javax.persistence.*;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Volo database table.
 * 
 */
@Entity
@Table(name="Volo")
@NamedQueries({
	@NamedQuery(name="Volo.findAll", query="SELECT v FROM Volo v WHERE v.eliminato != 1"),
	
	@NamedQuery(name="Volo.getVoloById", query="SELECT v FROM Volo v Where v.id = :id"),
	
	@NamedQuery(name="Volo.selectMax", query="SELECT v FROM Volo v Where v.id = (SELECT max(v.id) FROM Volo v)"),
	
	@NamedQuery(name="Volo.getVoliDisponibili", query="SELECT v FROM Volo v Where v.eliminato != 1 AND v.disponibilita > 0")
})

public class Volo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivo;

	@Column(name="citta_arrivo")
	private String cittaArrivo;

	@Column(name="citta_partenza")
	private String cittaPartenza;

	private float costo;

	private int disponibilita;

	@Column(name="nome_compagnia")
	private String nomeCompagnia;

	@Temporal(TemporalType.TIMESTAMP)
	private Date partenza;
	
	private int eliminato;
	

	public Volo() {
		
	}
	
	public Volo(VoloDTO volo) {
		this.arrivo = volo.getArrivo();
		this.cittaArrivo = volo.getCittaArrivo();
		this.cittaPartenza = volo.getCittaPartenza();
		this.costo = volo.getCosto();
		this.disponibilita = volo.getDisponibilita();
		this.nomeCompagnia = volo.getNomeCompagnia();
		this.partenza = volo.getPartenza();
		this.setEliminato(0); // non ha senso creare un elemento gia eliminato!
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getArrivo() {
		return this.arrivo;
	}

	public void setArrivo(Date arrivo) {
		this.arrivo = arrivo;
	}

	public String getCittaArrivo() {
		return this.cittaArrivo;
	}

	public void setCittaArrivo(String cittaArrivo) {
		this.cittaArrivo = cittaArrivo;
	}

	public String getCittaPartenza() {
		return this.cittaPartenza;
	}

	public void setCittaPartenza(String cittaPartenza) {
		this.cittaPartenza = cittaPartenza;
	}

	public float getCosto() {
		return this.costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public int getDisponibilita() {
		return this.disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public String getNomeCompagnia() {
		return this.nomeCompagnia;
	}

	public void setNomeCompagnia(String nomeCompagnia) {
		this.nomeCompagnia = nomeCompagnia;
	}

	public Date getPartenza() {
		return this.partenza;
	}

	public void setPartenza(Date partenza) {
		this.partenza = partenza;
	}

	public int getEliminato() {
		return eliminato;
	}

	public void setEliminato(int eliminato) {
		this.eliminato = eliminato;
	}

}