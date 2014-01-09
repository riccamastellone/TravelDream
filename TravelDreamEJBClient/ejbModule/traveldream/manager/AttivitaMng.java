package traveldream.manager;

import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;

public interface AttivitaMng {


	//public void salvaVolo(VoloDTO volo) throws ParseException;

	public ArrayList<AttivitaSecondariaDTO> getAttivita();
	
	//public void aggiornaVolo(VoloDTO volo) throws ParseException;
	
	public void deleteAttivita(AttivitaSecondariaDTO attivita);
	
	//public VoloDTO aggiungiVoloAPacchetto(VoloDTO volo) throws ParseException;
	

}
