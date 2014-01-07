package traveldream.gestioneComponente;

import java.util.ArrayList;

import traveldream.dtos.VoloDTO;

public interface ComponenteMng {
	
	
	public void salvaVolo(VoloDTO volo);
	
	public ArrayList<VoloDTO> getVoli();
	
	public void aggiornaVolo(VoloDTO volo);

}
