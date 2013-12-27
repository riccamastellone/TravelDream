package registrazione.client;

import java.util.List;


public interface UtenteMrg {
	
	/**
	 * salva l utente sul database(fase di registrazione)
	 * 
	 * @param utente
	 */
	public void salvaUtente(UtenteDTO utente);
	
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
	public List<UtenteDTO> getUtentiByGruppi(String gruppo);

}
