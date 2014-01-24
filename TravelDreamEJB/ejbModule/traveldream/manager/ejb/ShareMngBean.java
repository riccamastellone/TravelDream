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
import model.PacchettoCondiviso;
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

	public int getIdPacchettoFromChiave(String chiave) {
		List<PacchettoCondiviso> result = em.createNamedQuery("PacchettoCondiviso.cercaChiave", PacchettoCondiviso.class).setParameter("chiave", chiave).getResultList();
		return result.get(0).getPacchetto().getId();
	}

	public void acceptInvitation(int idShare) {
		PacchettoCondiviso pc = this.findShare(idShare);
		pc.setStato("accettato");
		em.merge(pc);	
		}
	
	
	private PacchettoCondiviso findShare(int id) {
		return em.find(PacchettoCondiviso.class, id);
	}

	public int getIdShareFromChiave(String chiave) {
		List<PacchettoCondiviso> result = em.createNamedQuery("PacchettoCondiviso.cercaChiave", PacchettoCondiviso.class).setParameter("chiave", chiave).getResultList();
		return result.get(0).getId();
	}



}
