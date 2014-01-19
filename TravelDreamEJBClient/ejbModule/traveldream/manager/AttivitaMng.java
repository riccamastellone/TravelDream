package traveldream.manager;

import java.util.List;
import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.PacchettoDTO;

public interface AttivitaMng {


	/**
	 * salva l attivita inserita a db
	 * @param attivita
	 */
	public void salvaAttivita(AttivitaSecondariaDTO attivita);

	/**
	 * ritorna tutte le attivita che non sono marcate come eliinate
	 * @return
	 */
	public ArrayList<AttivitaSecondariaDTO> getAttivita();
	
	/**
	 * aggiorna un attivita esistente e la mette a db
	 * @param attivita
	 */
	public void aggiornaAttivita(AttivitaSecondariaDTO attivita);
	
	/**
	 * marca come eliminata l attivita specificata
	 * @param attivita
	 */
	public void deleteAttivita(AttivitaSecondariaDTO attivita);
	
	/**
	 * salva l attivita che dovra poi essere associata al pacchetto esistente
	 * @param attivita
	 * @return
	 * @throws ParseException
	 */
	public AttivitaSecondariaDTO aggiungiAttivitaAPacchetto(AttivitaSecondariaDTO attivita) throws ParseException;
	
	/**
	 * ritorna tutte le attivita non eliminate e che hanno come localita quella del pacchetto
	 * @param pacchetto
	 * @return
	 */
	public List<AttivitaSecondariaDTO> getAttivitaCompatibiliPacchetto(PacchettoDTO pacchetto);
	
	

}
