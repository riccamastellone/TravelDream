package traveldream.dipendente.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.PacchettoMng;
import traveldream.manager.VoloMng;

@ManagedBean(name = "pacchettoBean")
@SessionScoped
public class PacchettoBean {
	
	@EJB
	private PacchettoMng pkgMng;
	
	@EJB
	private VoloMng voloMng;
	
	
	private PacchettoDTO pacchetto;
	
	private List<VoloDTO> voli;
	
	private VoloDTO volo;
	
	private String tipoVolo;
	
	
	public PacchettoBean() {
		this.pacchetto = new PacchettoDTO();
		this.volo = new VoloDTO();
		this.voli = new ArrayList<VoloDTO>();
		
		
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}
	
	
	public String agiungiNuovoVoloAPacchetto() {
		return null;
	}
	
	public String goToAggiungiVoli(){
		//mi serve l id aggiornato per scrivere nella tabella VoloPacchetto senza causare errori
		this.pacchetto = pkgMng.salvaInfoGenerali(pacchetto);
		
		//serve per precaricare la tabella di AggiungiVoloEsistente
		this.voli = voloMng.getVoli();
		return "aggiungiVoli?faces-redirect=true";
	}

	public List<VoloDTO> getVoli() {
		return voli;
	}

	public void setVoli(List<VoloDTO> voli) {
		this.voli = voli;
	}

	public VoloDTO getVolo() {
		return volo;
	}

	public void setVolo(VoloDTO voloDTO) {
		this.volo = voloDTO;
	}
	
	public String getTipoVolo() {
		return tipoVolo;
	}

	public void setTipoVolo(String tipoVolo) {
		this.tipoVolo = tipoVolo;
	}
	
	public String aggiungiVoloNuovoAPacchetto() throws ParseException {
		//mi serve l id aggiornato per scrivere nella tabella VoloPacchetto senza causare errori
		this.volo = this.voloMng.aggiungiVoloAPacchetto(volo);
		System.out.println(pacchetto.getNome());
		System.out.println(pacchetto.getId());
		System.out.println(volo.getId());
		//controllo il tipo e scelgo di aggiungere il volo all opportuna arraylist
		
		if (this.tipoVolo.equals("Andata")){
			this.pacchetto.getVoliAndata().add(volo);
		}
		else {
			this.pacchetto.getVoliRitorno().add(volo);
		}
		
		this.pkgMng.aggiungiVoloAPacchetto(pacchetto, volo, tipoVolo);
		
		
		return "aggiungiVoli?faces-redirect=true";
	}
	
	public void aggiungiVoloEsistenteAPacchetto(VoloDTO volo){
		this.voli.remove(volo);
		this.pkgMng.aggiungiVoloAPacchetto(pacchetto, volo, tipoVolo);
		System.out.println("tastopremuto");
	}
	
	


}
