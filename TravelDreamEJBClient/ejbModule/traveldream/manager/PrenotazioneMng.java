package traveldream.manager;


import java.util.List;

import traveldream.dtos.PrenotazioneDTO;
import traveldream.dtos.UtenteDTO;


public interface PrenotazioneMng {
	
	public void Prenota(PrenotazioneDTO prenotazione);
	
	public List<PrenotazioneDTO> getPrenotazioniUtente(UtenteDTO utente);

	public List<PrenotazioneDTO> getPrenotazioni();

		

}
