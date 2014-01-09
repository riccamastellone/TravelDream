package traveldream.manager;

import java.util.ArrayList;

import traveldream.dtos.AttivitaSecondariaDTO;

public interface AttivitaMng {


	public void salvaAttivita(AttivitaSecondariaDTO attivita);

	public ArrayList<AttivitaSecondariaDTO> getAttivita();
	
	public void aggiornaAttivita(AttivitaSecondariaDTO attivita);
	
	public void deleteAttivita(AttivitaSecondariaDTO attivita);
	
	

}
