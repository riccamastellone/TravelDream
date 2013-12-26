package registrazione.client;

public interface UtenteMrg {
	
	public void salvaUtente(UtenteDTO utente);
	
	public UtenteDTO getUserDTO();
	
	public boolean existEmail(String email);

}
