package mainController;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ContentBean {
	
	@EJB
	private ContentEAOLocal content;

	public ContentBean() {
		// TODO Auto-generated constructor stub
	}

	public String getAdminContent() {
		return content.getAdminContent();
	}


	public String getUserContent() {
		return content.getUserContent();
	}

	
	

}