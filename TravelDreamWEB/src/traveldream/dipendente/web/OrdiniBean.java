package traveldream.dipendente.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import traveldream.dtos.PrenotazioneDTO;
import traveldream.manager.ListaDesideriMng;
import traveldream.manager.PrenotazioneMng;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "ordiniBean")
@SessionScoped
public class OrdiniBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 781977263201659707L;
	
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	@EJB
	private PrenotazioneMng prenotazioneMng;
	
	private List<PrenotazioneDTO> prenotazioni;
	
	private PrenotazioneDTO dettaglio;
	
	public OrdiniBean(){		
	}

	
	public List<PrenotazioneDTO> getPrenotazioni() {
		prenotazioni = prenotazioneMng.getPrenotazioni();
		return prenotazioni;
	}

	public void setPrenotazioni(List<PrenotazioneDTO> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	public void mostraInfo(AjaxBehaviorEvent actionEvent, PrenotazioneDTO pren) {
		System.out.println(pren.getVoloAndata().getCittaArrivo());
		dettaglio = pren;
	}


	public PrenotazioneDTO getDettaglio() {
		return dettaglio;
	}


	public void setDettaglio(PrenotazioneDTO dettaglio) {
		this.dettaglio = dettaglio;
	}
	

}
