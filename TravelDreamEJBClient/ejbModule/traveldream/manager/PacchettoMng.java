package traveldream.manager;

import java.util.Date;
import java.util.List;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;

public interface PacchettoMng {
	
	/**
	 * salva le info generali del pacchetto (primo step creazione)
	 * @param pacchetto
	 * @return
	 */
	public PacchettoDTO salvaInfoGenerali(PacchettoDTO pacchetto);
	
	/**
	 * associa al pacchetto il volo appena creato
	 * @param pacchetto
	 * @param volo
	 * @param tipo
	 */
	public void aggiungiVoloAPacchetto(PacchettoDTO pacchetto, VoloDTO volo, String tipo);
	
	/**
	 * associa l hotel al pacchetto specificato
	 * @param pacchetto
	 * @param hotel
	 */
	public void aggiungiHotelAPacchetto(PacchettoDTO pacchetto, HotelDTO hotel);
	
	/**
	 * associa l attivita al pacchetto specificato
	 * @param pacchetto
	 * @param attivita
	 */
	public void aggiungiAttivitaAPacchetto(PacchettoDTO pacchetto, AttivitaSecondariaDTO attivita);
	
	/**
	 * ritorna tutti i pacchetti non marcati come eliminati
	 * @return
	 */
	public List<PacchettoDTO> getAllPacchetti();
	
	/**
	 * edita le info generali del pacchetto
	 * @param pacchetto
	 */
	public void editInfoGenerali(PacchettoDTO pacchetto);

	/**
	 * disassocia il volo dal paccchetto selezionato
	 * @param pacchettoDTO
	 * @param volo
	 */
	public void eliminaVoloDaPacchetto(PacchettoDTO pacchettoDTO, VoloDTO volo);
	
	/**
	 * disassocia l attivita dal paccchetto selezionato
	 * @param pacchettoDTO
	 * @param attivita
	 */
	public void eliminaAttivitaDaPacchetto(PacchettoDTO pacchettoDTO, AttivitaSecondariaDTO attivita);
	
	/**
	 * ritorna i campi aggiornati del pacchetto ignorando la cache del db
	 * @param pacchetto
	 * @return
	 */
	public PacchettoDTO getPacchettoAggiornato(PacchettoDTO pacchetto);
	
	
	public PacchettoDTO findPacchettoDTO(int id);

	/**
	 * marca come eliminato un pacchetto
	 * @param pacchetto
	 */
	public void deletePacchetto(PacchettoDTO pacchetto);
	
	public List<PacchettoDTO> ricercaPacchetto(String destinazione, Date partenza, Date ritorno, int persone);
	
}
