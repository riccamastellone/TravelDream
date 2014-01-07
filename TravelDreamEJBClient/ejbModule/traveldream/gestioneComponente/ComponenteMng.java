package traveldream.gestioneComponente;

import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.VoloDTO;

public interface ComponenteMng {
	
	
	public void salvaVolo(VoloDTO volo) throws ParseException;
	
	public ArrayList<VoloDTO> getVoli();
	
	public void aggiornaVolo(VoloDTO volo) throws ParseException;
	
	public void deleteVolo(VoloDTO volo);

}
