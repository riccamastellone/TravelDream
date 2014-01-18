package traveldream.manager;

import java.util.List;
import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.PacchettoDTO;

public interface AttivitaMng {


	public void salvaAttivita(AttivitaSecondariaDTO attivita);

	public ArrayList<AttivitaSecondariaDTO> getAttivita();
	
	public void aggiornaAttivita(AttivitaSecondariaDTO attivita);
	
	public void deleteAttivita(AttivitaSecondariaDTO attivita);
	
	public AttivitaSecondariaDTO aggiungiAttivitaAPacchetto(AttivitaSecondariaDTO attivita) throws ParseException;
	
	public List<AttivitaSecondariaDTO> getAttivitaCompatibiliPacchetto(PacchettoDTO pacchetto);
	
	

}
