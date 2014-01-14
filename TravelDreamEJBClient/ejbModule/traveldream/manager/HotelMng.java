package traveldream.manager;

import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.HotelDTO;


public interface HotelMng {

	public ArrayList<HotelDTO> getAllHotel();
	
	public ArrayList<HotelDTO> getAllHotelCompatibili(String luogo);
	
	public void salvaHotel(HotelDTO hotel);
	
	public void aggiornaHotel(HotelDTO hotel);

	public void deleteHotel(HotelDTO hotel);

	public HotelDTO aggiungiHotelAPacchetto(HotelDTO hotel) throws ParseException;



}
