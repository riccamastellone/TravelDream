package traveldream.manager;

import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;

public interface VoloMng {

	
	public void salvaVolo(VoloDTO volo) throws ParseException;

	
	public ArrayList<VoloDTO> getVoli();
	
	public void aggiornaVolo(VoloDTO volo) throws ParseException;
	
	public void deleteVolo(VoloDTO volo);
	
	

}
