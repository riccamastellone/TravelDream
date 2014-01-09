package traveldream.manager;

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

}
