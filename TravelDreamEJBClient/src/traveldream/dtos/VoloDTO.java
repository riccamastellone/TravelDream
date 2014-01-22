package traveldream.dtos;


import java.util.Date;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotEmpty;

public class VoloDTO {

	private int id;
	
	@NotNull
	private Date arrivo;
	
	@NotNull
	private Date partenza;

	@NotEmpty
	private String cittaArrivo;

	@NotEmpty
	private String cittaPartenza;
	
	@Digits(integer=6, fraction=2)
	private float costo;
	
	@Min(0)
	private int disponibilita;

	@NotEmpty
	private String nomeCompagnia;

	private int eliminato;
	

	public VoloDTO() {
		
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	 public void printaDati() {
			System.out.println(this.getCittaArrivo());
			System.out.println(this.getCittaPartenza());
			System.out.println(this.getCosto());
			System.out.println(this.getDisponibilita());
			System.out.println(this.getArrivo());
			System.out.println(this.getPartenza());	
			System.out.println(this.getId());
			System.out.println(this.getNomeCompagnia());
	}
	 
	 public Object clone() {

	    VoloDTO voloDTO = new VoloDTO();
	    voloDTO.id = this.id;
	    voloDTO.arrivo = this.arrivo;
	    voloDTO.partenza = this.partenza;
	    voloDTO.cittaArrivo = this.cittaArrivo;
	    voloDTO.cittaPartenza = this.cittaPartenza;
	    voloDTO.costo = this.costo;
	    voloDTO.disponibilita = this.disponibilita;
	    voloDTO.nomeCompagnia = this.nomeCompagnia;
	    voloDTO.eliminato = this.eliminato;
	    return voloDTO;
	  }
	 
	 
	 
}
