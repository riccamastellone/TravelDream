package traveldream.manager;

import java.util.List;

import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.ShareDTO;
import traveldream.dtos.UtenteDTO;

public interface ShareMng {

	
	public int getIdPacchettoFromChiave(String chiave);

	public void acceptInvitation(int idShare);

	public int getIdShareFromChiave(String chiave);

	public void createShare(PacchettoDTO pacchetto, UtenteDTO userDTO, String email, String key);

	public List<ShareDTO> getSharesUtente(UtenteDTO userDTO);
	

}
