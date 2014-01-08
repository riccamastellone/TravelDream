package traveldream.gestioneComponente;

import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;

public interface ComponenteMng {

	
	public void salvaVolo(VoloDTO volo) throws ParseException;

	
	public ArrayList<VoloDTO> getVoli();
	
	public void aggiornaVolo(VoloDTO volo) throws ParseException;
	
	public void deleteVolo(VoloDTO volo);
	
	/* HOTEL */
	public ArrayList<HotelDTO> getAllHotel();
	
	public void salvaHotel(HotelDTO hotel);
	
	public void aggiornaHotel(HotelDTO hotel);

}
