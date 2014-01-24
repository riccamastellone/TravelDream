package traveldream.manager.ejb;


import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Hotel;
import model.ListaDesideri;
import model.Pacchetto;
import model.Utente;
import registrazione.ejb.UtenteMgrBean;
import traveldream.dtos.ListaDesideriMng;
import traveldream.manager.ListaDesideriDTO;
import traveldream.manager.ShareMng;

/**
 * Session Bean implementation class ListaDesideriMngBean
 */
@Stateless
public class ShareMngBean implements ShareMng {

	
	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
    public ShareMngBean() {
    }

	@Override
	public int getIdPacchettoFromChiave(String chiave) {
		em.find(Pacchetto.class, chiave);
		return 0;
	}
    


}
