package traveldream.dtos;



import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotEmpty;

public class AttivitaSecondariaDTO {

	
	private int id;
	
	@Min(value = 0, message = "Non deve essere negativo")
	@Digits(integer=6, fraction=2)
	private float costo;
	
	@NotEmpty(message= "Non deve essere vuoto") 
	private String descrizione;
	
	@Min(value = 0, message = "Non deve essere negativo") 
	private int disponibilita;
	
	@NotEmpty(message= "Non deve essere vuoto")
	private String localita;

	@NotEmpty(message= "Non deve essere vuoto")
	private String nome;
	
	private int eliminato;



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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	 public Object clone() {

		    AttivitaSecondariaDTO AttivitaSecondariaDTO = new AttivitaSecondariaDTO();
		    AttivitaSecondariaDTO.id = this.id;
		    AttivitaSecondariaDTO.nome = this.nome;
		    AttivitaSecondariaDTO.localita = this.localita;
		    AttivitaSecondariaDTO.costo = this.costo;
		    AttivitaSecondariaDTO.disponibilita = this.disponibilita;
		    AttivitaSecondariaDTO.descrizione = this.descrizione;
		    AttivitaSecondariaDTO.eliminato = this.eliminato;
		    return AttivitaSecondariaDTO;
		  }
	
}
