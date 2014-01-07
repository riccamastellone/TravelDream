package traveldream.gestioneComponente;

import java.util.ArrayList;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;

public interface ComponenteMng {
	
	/* VOLI */
	public void salvaVolo(VoloDTO volo);
	
	public ArrayList<VoloDTO> getVoli();
	
	public void aggiornaVolo(VoloDTO volo);
	
	public void deleteVolo(VoloDTO volo);
	
	/* HOTEL */
	public ArrayList<HotelDTO> getAllHotel();
	
	public void salvaHotel(HotelDTO hotel);

}
