package traveldream.manager.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Pacchetto;
import model.PacchettoCondiviso;
import model.Utente;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.ShareDTO;
import traveldream.dtos.UtenteDTO;
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
	
	private List<ShareDTO> shareList;

	public ShareMngBean() {
		shareList = new ArrayList<ShareDTO>();
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

	public void createShare(PacchettoDTO pacchetto, UtenteDTO userDTO, String email, String key) {
		PacchettoCondiviso pc = new PacchettoCondiviso();
		pc.setChiave(key);
		pc.setEmailAmico(email);
		pc.setPacchetto(em.find(Pacchetto.class, pacchetto.getId()));
		pc.setUtente(em.find(Utente.class, userDTO.getEmail()));
		pc.setStato("non accettato");
		em.persist(pc);
	}

	public List<ShareDTO> getSharesUtente(UtenteDTO userDTO) {
		Utente utente = em.find(Utente.class, userDTO.getEmail());
		List<PacchettoCondiviso> lista = em.createNamedQuery("PacchettoCondiviso.getByUtente", PacchettoCondiviso.class).setParameter("utente", utente).getResultList();
		for(PacchettoCondiviso p : lista) {
			buildDTO(p);
		}
		return shareList;
	}
	
	
	protected void buildDTO(PacchettoCondiviso s) {
		
		List<String> friend = new ArrayList<String>();
		friend.add(s.getEmailAmico());
		friend.add(s.getStato());
		
		
		int t = 0;
		for(ShareDTO sl : shareList) {
			if(sl.getPacchetto().getId() == s.getPacchetto().getId()) {
				sl.getAmici().add(friend);
				t = 1;
			} 
		}
		if(t == 0) { 
			ShareDTO share = new ShareDTO();
			share.setPacchetto(PacchettoMngBean.convertToDto(s.getPacchetto()));
			List<List<String>> friendList = new ArrayList<List<String>>();
			friendList.add(friend);
			share.setAmici(friendList);
			shareList.add(share);
		}
		
	}
	

}
