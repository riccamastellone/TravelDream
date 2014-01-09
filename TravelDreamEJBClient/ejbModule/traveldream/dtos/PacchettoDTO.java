package traveldream.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import com.sun.xml.xsom.impl.scd.Iterators.Map;

public class PacchettoDTO {

	private int id;
	
	private String descrizione;
	
	@Temporal(TemporalType.DATE)
	private Date inizioValidita;
	
	@Temporal(TemporalType.DATE)
	private Date fineValidita;
	
	@NotEmpty
	private HotelDTO hotel;
	
	@NotEmpty
	private String immagine;
	
	@NotEmpty
	private String localita;
	
	@NotEmpty
	private String nome;

	private HashMap<VoloDTO, String> voliAndata;
	
	private List<VoloDTO> voliRitorno;
	
	private List<VoloDTO> voliNuoviAndata;
	
	private List<AttivitaSecondariaDTO> attivitaSecondarie;
	
	public PacchettoDTO(){
		this.setVoliAndata(new HashMap<VoloDTO, String>());
		
		this.voliRitorno = new ArrayList<VoloDTO>();
		this.attivitaSecondarie = new ArrayList<AttivitaSecondariaDTO>();
		this.voliNuoviAndata = new ArrayList<VoloDTO>();
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

	public Date getInizioValidita() {
		return inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public Date getFineValidita() {
		return fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
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


	public List<AttivitaSecondariaDTO> getAttivitaSecondarie() {
		return attivitaSecondarie;
	}

	public void setAttivitaSecondarie(List<AttivitaSecondariaDTO> attivitaSecondarie) {
		this.attivitaSecondarie = attivitaSecondarie;
	}

	/*
	public List<VoloDTO> getVoliAndata() {
		return voliAndata;
	}

	public void setVoliAndata(List<VoloDTO> voliAndata) {
		this.voliAndata = voliAndata;
	}
    */
	public List<VoloDTO> getVoliRitorno() {
		return voliRitorno;
	}

	public void setVoliRitorno(List<VoloDTO> voliRitorno) {
		this.voliRitorno = voliRitorno;
	}

	public List<VoloDTO> getVoliNuoviAndata() {
		return voliNuoviAndata;
	}

	public void setVoliNuoviAndata(List<VoloDTO> voliNuoviAndata) {
		this.voliNuoviAndata = voliNuoviAndata;
	}

	public HashMap<VoloDTO, String> getVoliAndata() {
		return voliAndata;
	}

	public void setVoliAndata(HashMap<VoloDTO, String> voliAndata) {
		this.voliAndata = voliAndata;
	}
	
	

}
