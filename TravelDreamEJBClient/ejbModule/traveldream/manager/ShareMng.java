package traveldream.manager;

import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.UtenteDTO;

public interface ShareMng {

	
	public int getIdPacchettoFromChiave(String chiave);

	public void acceptInvitation(int idShare);

	public int getIdShareFromChiave(String chiave);

	public void createShare(PacchettoDTO pacchetto, UtenteDTO userDTO, String email, String key);
	

}
