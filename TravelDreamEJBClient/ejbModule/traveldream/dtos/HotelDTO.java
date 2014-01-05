package dtos;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class HotelDTO {

	@NotEmpty 
	private String nome;

	@NotEmpty
	private String luogo;
	
	@NotNull
    private Integer costo_giornaliero;
	 
	@NotEmpty
	private String PathtoImage; 
	
	@NotNull
	private Integer Stelle;

	public Integer getStelle() {
		return Stelle;
	}

	public void setStelle(Integer stelle) {
		Stelle = stelle;
	}

	public String getPathtoImage() {
		return PathtoImage;
	}

	public void setPathtoImage(String pathtoImage) {
		PathtoImage = pathtoImage;
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

	public Integer getCosto_giornaliero() {
		return costo_giornaliero;
	}

	public void setCosto_giornaliero(Integer costo_giornaliero) {
		this.costo_giornaliero = costo_giornaliero;
	}

	
}
