package traveldream.dtos;


import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class HotelDTO {
	
	private int id;

	@NotEmpty(message= "Non deve essere vuoto") 
	private String nome;

	@NotEmpty(message= "Non deve essere vuoto")
	private String luogo;
	
	@Min(value = 0, message = "Non deve essere negativo")
	@Digits(integer=6, fraction=2)
    private float costoGiornaliero;
	 
	
	private String pathtoImage; 
	
	@NotNull
	private Integer stelle;
	
	@Min(value = 0, message = "Non deve essere negativo")
	@NotNull(message= "Non deve essere vuoto")
	private Integer disponibilita;
	
	@NotEmpty(message= "Non deve essere vuoto")
	private String descrizione;
	
	private int eliminato;

	public Integer getStelle() {
		return this.stelle;
	}

	public void setStelle(Integer stelle) {
		this.stelle = stelle;
	}

	public String getPathtoImage() {
		return pathtoImage;
	}

	public void setPathtoImage(String pathtoImage) {
		this.pathtoImage = pathtoImage;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public float getCostoGiornaliero() {
		return costoGiornaliero;
	}

	public void setCostoGiornaliero(float f) {
		this.costoGiornaliero = f;
	}

	public Integer getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(Integer disponibilita) {
		this.disponibilita = disponibilita;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	 public Object clone() {

		    HotelDTO HotelDTO = new HotelDTO();
		    HotelDTO.id = this.id;
		    HotelDTO.nome = this.nome;
		    HotelDTO.luogo = this.luogo;
		    HotelDTO.costoGiornaliero = this.costoGiornaliero;
		    HotelDTO.pathtoImage = this.pathtoImage;
		    HotelDTO.stelle = this.stelle;
		    HotelDTO.disponibilita = this.disponibilita;
		    HotelDTO.descrizione = this.descrizione;
		    HotelDTO.eliminato = this.eliminato;
		    return HotelDTO;
		  }

	public int getEliminato() {
		return eliminato;
	}

	public void setEliminato(int eliminato) {
		this.eliminato = eliminato;
	}

	
}
