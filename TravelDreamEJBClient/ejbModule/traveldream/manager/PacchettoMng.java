package traveldream.manager;

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
	
	public void aggiungiHotelAPacchetto(PacchettoDTO pacchetto, HotelDTO hotel);
	
	public void aggiungiAttivitaAPacchetto(PacchettoDTO pacchetto, AttivitaSecondariaDTO attivita);
	
	public List<PacchettoDTO> getAllPacchetti();
	
	public void editInfoGenerali(PacchettoDTO pacchetto);

	public void eliminaVoloDaPacchetto(PacchettoDTO pacchettoDTO, VoloDTO volo);
	
	public PacchettoDTO getPacchettoAggiornato(PacchettoDTO pacchetto);
	
	public void deletePacchetto(PacchettoDTO pacchetto);
	
}
