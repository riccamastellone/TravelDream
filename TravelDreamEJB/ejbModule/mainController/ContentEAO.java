package mainController;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import org.eclipse.persistence.internal.oxm.schema.model.Content;

/**
 * Session Bean implementation class ContentEAO
 */
@Stateless
public class ContentEAO implements ContentEAOLocal {
       
	 /**
     * Default constructor. 
     */
    public ContentEAO() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@RolesAllowed({"admin"})
	public String getAdminContent() {
		return "Admin super secret info";
	}

	@Override
	@RolesAllowed({"dipendente"})
	public String getDipendenteContent() {
		return "Dipendente super secret info";
	}
	
	@Override
	@RolesAllowed({"cliente"})
	public String getUserContent() {
		return "cliente super secret info";
	}

}