package traveldream.manager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.VoloDTO;

public interface VoloMng {

	/**
	 * salva il volo nel database (volo stand alone)
	 * @param volo
	 * @throws ParseException
	 */
	public void salvaVolo(VoloDTO volo) throws ParseException;

	/**
	 * restituisce tutti i voli disponibili (flag eliminato != 1
	 * @return
	 */
	public List<VoloDTO> getVoli();
	
	/**
	 *aggiorna un volo gia esistente ( volo stand alone)  
	 * @param volo
	 * @throws ParseException
	 */
	public void aggiornaVolo(VoloDTO volo) throws ParseException;
	
	/**
	 * setta a 1 il flag eliminato del volo specificato
	 * @param volo
	 */
	public void deleteVolo(VoloDTO volo);
	
	/**
	 * crea un nuovo volo e lo ritorna al client con l id aggiornato (utile nella creazione del pacchetto)
	 * @param volo
	 * @return
	 * @throws ParseException
	 */
	public VoloDTO aggiungiVoloAPacchetto(VoloDTO volo) throws ParseException;
	
	public ArrayList<VoloDTO> getVoliDisponibili();
	

}
