package model;

import java.io.Serializable;

import javax.persistence.*;

import traveldream.dtos.HotelDTO;

@Entity
@Table(name = "Hotel")

@NamedQueries({ 
	
	@NamedQuery(name = Hotel.FIND_ALL, query = "SELECT h FROM Hotel h WHERE h.eliminato != 1"),
	
	@NamedQuery(name="Hotel.selectMax", query="SELECT h FROM Hotel h Where h.id = (SELECT max(h.id) FROM Hotel h)"),
	
	@NamedQuery(name="Hotel.getVoloById", query="SELECT h FROM Hotel h Where h.id = :id"),
	
	@NamedQuery(name="Hotel.getHotelCompatibiliPacchetto", query="SELECT h FROM Hotel h Where h.luogo LIKE :luogo AND h.eliminato != 1"),
	
	@NamedQuery(name="Hotel.Ricerca", query="SELECT h FROM Hotel h Where h.eliminato !=1 AND (h.luogo Like :luogo OR h.nome Like :luogo) AND h.disponibilita >= :persone"),
	
})

public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FIND_ALL = "Hotel.findAll";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name = "costo_giornaliero")
	private float costoGiornaliero;

	private int disponibilita;

	private String immagine;

	private String luogo;

	private String nome;
	
	private String descrizione;

	private int stelle;
	
	private int eliminato;

	public Hotel() {
		super();
	}

	public Hotel(HotelDTO hoteldto) {
		this.nome = hoteldto.getNome();
		this.luogo = hoteldto.getLuogo();
		this.costoGiornaliero = hoteldto.getCostoGiornaliero();
		this.immagine = hoteldto.getPathtoImage();
		this.stelle = hoteldto.getStelle();
		this.disponibilita = hoteldto.getDisponibilita();
		this.setDescrizione(hoteldto.getDescrizione());
		this.setEliminato(0); // non ha senso creare un elemento gia eliminato!
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCostoGiornaliero() {
		return this.costoGiornaliero;
	}

	public void setCostoGiornaliero(float costoGiornaliero) {
		this.costoGiornaliero = costoGiornaliero;
	}

	public String getImmagine() {
		return this.immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public String getLuogo() {
		return this.luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getStelle() {
		return this.stelle;
	}

	public void setStelle(int stelle) {
		this.stelle = stelle;
	}

	
	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getEliminato() {
		return eliminato;
	}

	public void setEliminato(int eliminato) {
		this.eliminato = eliminato;
	}

}