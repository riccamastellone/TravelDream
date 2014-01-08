package traveldream.manager;

import java.util.ArrayList;

import traveldream.dtos.HotelDTO;

public interface HotelMng {

	public ArrayList<HotelDTO> getAllHotel();
	
	public void salvaHotel(HotelDTO hotel);
	
	public void aggiornaHotel(HotelDTO hotel);
}
