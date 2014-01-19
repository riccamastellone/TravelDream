package traveldream.manager;

import java.text.ParseException;
import java.util.ArrayList;

import traveldream.dtos.HotelDTO;


public interface HotelMng {

	/**
	 * ritorna tutti gli hotel non marcati come eliminati
	 * @return
	 */
	public ArrayList<HotelDTO> getAllHotel();
	
	/**
	 * ritorna tutti gli hotel che hanno la localita ugiale a quella del pacchetto
	 * @param luogo
	 * @return
	 */
	public ArrayList<HotelDTO> getAllHotelCompatibili(String luogo);
	
	/**
	 * salva l hotel a db
	 * @param hotel
	 */
	public void salvaHotel(HotelDTO hotel);
	
	/**
	 * salva le modifche ad un hotel esistente a db
	 * @param hotel
	 */
	public void aggiornaHotel(HotelDTO hotel);

	/**
	 * marca come eliminato l hotel specificato
	 * @param hotel
	 */
	public void deleteHotel(HotelDTO hotel);

	/**
	 * salva l hotel che dovra essere ssociato al pacchetto esistente
	 * @param hotel
	 * @return
	 * @throws ParseException
	 */
	public HotelDTO aggiungiHotelAPacchetto(HotelDTO hotel) throws ParseException;
	



}
