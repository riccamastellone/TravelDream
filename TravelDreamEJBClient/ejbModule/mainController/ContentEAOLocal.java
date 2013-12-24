package mainController;

import javax.ejb.Local;

@Local
public interface ContentEAOLocal {
	
	public String getAdminContent();
	
	public String getUserContent();
	
	public String getDipendenteContent();

}
