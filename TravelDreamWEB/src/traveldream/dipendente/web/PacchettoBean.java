package traveldream.dipendente.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.expression.impl.ThisExpressionResolver;

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
	
	private List<VoloDTO> voliNuoviAndata;
	
	private List<VoloDTO> voliNuoviRitorno;
	
	private List<VoloDTO> voliEsistentiAndata;
	
	private List<VoloDTO> voliEsistentiRitorno;
	
	private VoloDTO volo;
	
	private String tipoVolo;
	
	
	public PacchettoBean() {
		this.pacchetto = new PacchettoDTO();
		this.volo = new VoloDTO();
		this.voli = new ArrayList<VoloDTO>();
		this.tipoVolo = "Andata";
				
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}
	
	public List<VoloDTO> getVoliNuoviAndata() {
		return voliNuoviAndata;
	}

	public void setVoliNuoviAndata(List<VoloDTO> voliAndata) {
		this.voliNuoviAndata = voliAndata;
	}

	public List<VoloDTO> getVoliNuoviRitorno() {
		return voliNuoviRitorno;
	}

	public void setVoliNuoviRitorno(List<VoloDTO> voliRitorno) {
		this.voliNuoviRitorno = voliRitorno;
	}
		
	public List<VoloDTO> getVoliEsistentiAndata() {
		return voliEsistentiAndata;
	}

	public void setVoliEsistentiAndata(List<VoloDTO> voliEsistentiAndata) {
		this.voliEsistentiAndata = voliEsistentiAndata;
	}

	public List<VoloDTO> getVoliEsistentiRitorno() {
		return voliEsistentiRitorno;
	}

	public void setVoliEsistentiRitorno(List<VoloDTO> voliEsistentiRitorno) {
		this.voliEsistentiRitorno = voliEsistentiRitorno;
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
	
	public String goToAggiungiVoli(){
		//mi serve l id aggiornato per scrivere nella tabella VoloPacchetto senza causare errori
		//this.pacchetto = pkgMng.salvaInfoGenerali(pacchetto);
		
		//serve per precaricare la tabella di AggiungiVoloEsistente
		this.voli = voloMng.getVoli();
		this.voliNuoviAndata = new ArrayList<VoloDTO>();
		this.voliNuoviRitorno = new ArrayList<VoloDTO>();
		this.setVoliEsistentiAndata(new ArrayList<VoloDTO>());
		this.setVoliEsistentiRitorno(new ArrayList<VoloDTO>());
		this.pacchetto.getVoliAndata().clear();
		this.pacchetto.getVoliRitorno().clear();
		return "aggiungiVoli?faces-redirect=true";
	}
	
	public String aggiungiVoloNuovoAPacchetto() throws ParseException {
		//mi serve l id aggiornato per scrivere nella tabella VoloPacchetto senza causare errori
		//this.volo = this.voloMng.aggiungiVoloAPacchetto(volo);
		System.out.println(pacchetto.getNome());
		System.out.println(pacchetto.getId());
		System.out.println(volo.getId());
		//controllo il tipo e scelgo di aggiungere il volo all opportuna arraylist
		
		if (this.tipoVolo.equals("Andata")){
			
			this.voliNuoviAndata.add((VoloDTO) this.volo.clone());
			this.pacchetto.getVoliAndata().add((VoloDTO) this.volo.clone());
			
		}
		else {
			this.voliNuoviRitorno.add((VoloDTO) this.volo.clone());
			this.pacchetto.getVoliRitorno().add((VoloDTO) this.volo.clone());
		}
		
		//this.pkgMng.aggiungiVoloAPacchetto(pacchetto, volo, tipoVolo);
		
		System.out.println("-------ANDATA---------");
		for (VoloDTO volo3 : this.pacchetto.getVoliAndata()) {
			volo3.printaDati();
		}
		System.out.println("-------RITORNO---------");
		for (VoloDTO volo4 : this.pacchetto.getVoliRitorno()) {
			volo4.printaDati();
		}
		
		return "aggiungiVoli?faces-redirect=true";
	}
	
	public void aggiungiVoloEsistenteAPacchetto(VoloDTO volo) {
		this.voli.remove(volo);
		
		if (this.tipoVolo.equals("Andata")) {

			this.voliEsistentiAndata.add((VoloDTO) volo.clone());
			this.pacchetto.getVoliAndata().add((VoloDTO) volo.clone());

		} else {
			this.voliEsistentiRitorno.add((VoloDTO) volo.clone());
			this.pacchetto.getVoliRitorno().add((VoloDTO) volo.clone());
		}
		
		System.out.println("-------ANDATA---------");
		for (VoloDTO volo3 : this.pacchetto.getVoliAndata()) {
			volo3.printaDati();
		}
		System.out.println("-------RITORNO---------");
		for (VoloDTO volo4 : this.pacchetto.getVoliRitorno()) {
			volo4.printaDati();
		}
		// this.pkgMng.aggiungiVoloAPacchetto(pacchetto, volo, tipoVolo);
		System.out.println("tastopremuto");
	}
	


}
