package traveldream.manager.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.AttivitaSecondariaPacchetto;
import model.Pacchetto;
import model.PacchettoCondiviso;
import model.Utente;
import model.VoloPacchetto;
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
		//servono per impostare lo stato di ok o x
		boolean attivitaTutteVuote = true;
		boolean voliAndataTuttiVuoti = true;
		boolean voliRitornoTuttiVuoti = true;
		
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
			
			//ricavo tutti i voli e li distinguo tra andata e ritorno
			for (VoloPacchetto voloPacchetto : s.getPacchetto().getVoliPacchetto()) {

				if (voloPacchetto.getTipo().equals("Andata")) {
					share.getPacchetto().getVoliAndata().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
					if (voloPacchetto.getVolo().getDisponibilita() != 0) {
						voliAndataTuttiVuoti = false;
					}
				} else {
					share.getPacchetto().getVoliRitorno().add(VoloMngBean.convertVoloToDTO(voloPacchetto.getVolo()));
					if (voloPacchetto.getVolo().getDisponibilita() != 0) {
						voliRitornoTuttiVuoti = false;
					}
				}

				
			}
			
			//ricavo l hotel
			if (s.getPacchetto().getHotel() == null) {
				share.getPacchetto().setHotel(null);
			}
			else {
				share.getPacchetto().setHotel(HotelMngBean.HotelToDTO(s.getPacchetto().getHotel()));
			}
			
			
			//ricavo la lista delle attivita secondarie asociate al paccchetto
			for (AttivitaSecondariaPacchetto attivitaSecondariaPacchetto : s.getPacchetto().getAttivitaSecondariePacchetto()) {
				share.getPacchetto().getAttivitaSecondarie().add(AttivitaMngBean.AttivitaToDTO(attivitaSecondariaPacchetto.getAttivitaSecondariaBean()));
				if (attivitaSecondariaPacchetto.getAttivitaSecondariaBean().getDisponibilita() != 0) {
					attivitaTutteVuote = false;
				}
			}
			
			if (share.getPacchetto().getVoliAndata().isEmpty() || share.getPacchetto().getVoliRitorno().isEmpty() || share.getPacchetto().getHotel() == null || share.getPacchetto().getAttivitaSecondarie().isEmpty() || voliAndataTuttiVuoti || voliRitornoTuttiVuoti || attivitaTutteVuote || share.getPacchetto().getHotel().getDisponibilita() == 0){
				share.getPacchetto().setOk("X");
			}
			else {
				share.getPacchetto().setOk("OK");
			}
			
			
		
			List<List<String>> friendList = new ArrayList<List<String>>();
			friendList.add(friend);
			share.setAmici(friendList);
			shareList.add(share);
		}
		
	}
	

}
