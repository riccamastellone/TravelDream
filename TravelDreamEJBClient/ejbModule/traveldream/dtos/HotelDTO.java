package traveldream.dtos;


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

	
}
