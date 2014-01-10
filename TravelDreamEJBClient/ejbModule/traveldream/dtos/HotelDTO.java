package traveldream.dtos;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class HotelDTO {
	
	private int id;

	@NotEmpty 
	private String nome;

	@NotEmpty
	private String luogo;
	
	@NotNull
    private float costoGiornaliero;
	 
	@NotEmpty
	private String pathtoImage; 
	
	@NotNull
	private Integer stelle;
	
	@NotNull
	private Integer disponibilita;
	
	private String descrizione;

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
		    //HotelDTO.eliminato = this.eliminato;
		    return HotelDTO;
		  }

	
}
