package traveldream.manager;

public interface ShareMng {

	
	public int getIdPacchettoFromChiave(String chiave);

	public void acceptInvitation(int idShare);

	public int getIdShareFromChiave(String chiave);
	

}
