package traveldream.manager;

import java.util.ArrayList;

import traveldream.dtos.UtenteDTO;



public interface UtenteMrg {
	
	/**
	 * salva l utente sul database(fase di registrazione)
	 * 
	 * @param utente
	 */
	public void salvaUtente(UtenteDTO utente, String tipo);
	
	public UtenteDTO getUserDTO();
	
	/**
	 * controlla se esiste un utente gia registrato con la stessa email
	 * 
	 * @param email
	 * @return
	 */
	public boolean existEmail(String email);
	
	/**
	 * restituisce la lista di tutti gli utenti del gruppo specificato
	 * 
	 * @param gruppo
	 * @return
	 */
	public ArrayList<UtenteDTO> getUtentiByGruppi(String gruppo);
	
	/**
	 * 
	 * aggiorna il utente
	 * se la password e stata cambiata fa l hash e la cambia se no lascia l hash vecchio
	 */
	public void aggiornaUtente(UtenteDTO utente, String vecchiaEmail);
	
	public String getGruppo(UtenteDTO utente);
	

}
