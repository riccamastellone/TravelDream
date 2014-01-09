package traveldream.manager;

import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.AttivitaSecondariaDTO;

public interface AttivitaMng {


	public void salvaAttivita(AttivitaSecondariaDTO attivita);

	public ArrayList<AttivitaSecondariaDTO> getAttivita();
	
	//public void aggiornaVolo(VoloDTO volo) throws ParseException;
	
	public void deleteAttivita(AttivitaSecondariaDTO attivita);
	
	//public VoloDTO aggiungiVoloAPacchetto(VoloDTO volo) throws ParseException;
	

}
