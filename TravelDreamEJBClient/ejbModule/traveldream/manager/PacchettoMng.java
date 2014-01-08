package traveldream.manager;

import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;

public interface PacchettoMng {
	
	public PacchettoDTO salvaInfoGenerali(PacchettoDTO pacchetto);
	
	public void aggiungiVoloAPacchetto(PacchettoDTO pacchetto, VoloDTO volo, String tipo);

}
