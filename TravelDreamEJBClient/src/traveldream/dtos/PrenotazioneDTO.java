package traveldream.dtos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class PrenotazioneDTO {
	

	private int id;

	private float costoPersona;
	
	private Timestamp dataCreazione;

	private HotelDTO hotel;

	private int persone;

	private UtenteDTO utente;

	private VoloDTO voloAndata;

	private VoloDTO voloRitorno;

	private List<AttivitaSecondariaDTO> listAttivitaSecondarie;
	
	
	public PrenotazioneDTO(){
		this.listAttivitaSecondarie = new ArrayList<AttivitaSecondariaDTO>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCostoPersona() {
		return this.costoPersona;
	}

	public void setCostoPersona(float costoPersona) {
		this.costoPersona = costoPersona;
	}

	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public HotelDTO getHotel() {
		return this.hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public int getPersone() {
		return this.persone;
	}

	public void setPersone(int persone) {
		this.persone = persone;
	}

	public UtenteDTO getUtente() {
		return this.utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	public VoloDTO getVoloAndata() {
		return this.voloAndata;
	}

	public void setVoloAndata(VoloDTO voloAndata) {
		this.voloAndata = voloAndata;
	}

	public VoloDTO getVoloRitorno() {
		return this.voloRitorno;
	}

	public void setVoloRitorno(VoloDTO voloRitorno) {
		this.voloRitorno = voloRitorno;
	}

	public List<AttivitaSecondariaDTO> getListAttivitaSecondarie() {
		return listAttivitaSecondarie;
	}

	public void setListAttivitaSecondarie(List<AttivitaSecondariaDTO> listAttivitaSecondarie) {
		this.listAttivitaSecondarie = listAttivitaSecondarie;
	}

}
