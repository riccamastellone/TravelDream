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
	
	//serve per visualizzare i voli esistenti
	private List<VoloDTO> voli;
	
	//serve per l inserimento a db (creare un nuovo volo nella tabella voli)
	private List<VoloDTO> voliNuoviAndata;
	
	//serve per l inserimento a db (creare un nuovo volo nella tabella voli)
	private List<VoloDTO> voliNuoviRitorno;
	
	//serve per l inserimento a db (NON creare un nuovo volo nella tabella voli ma associare e basta)
	private List<VoloDTO> voliEsistentiAndata;
	
	//serve per l inserimento a db (NON creare un nuovo volo nella tabella voli ma associare e basta)
	private List<VoloDTO> voliEsistentiRitorno;
	
	private VoloDTO volo;
	
	private String tipoVolo;
	
	
	public PacchettoBean() {
		this.pacchetto = new PacchettoDTO();
		this.volo = new VoloDTO();
		this.voli = new ArrayList<VoloDTO>();
		this.tipoVolo = "Andata";
		this.voliNuoviAndata = new ArrayList<VoloDTO>();
		this.voliNuoviRitorno = new ArrayList<VoloDTO>();
		this.setVoliEsistentiAndata(new ArrayList<VoloDTO>());
		this.setVoliEsistentiRitorno(new ArrayList<VoloDTO>());
		this.pacchetto.getVoliAndata().clear();
		this.pacchetto.getVoliRitorno().clear();
				
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
		
		return "aggiungiVoli?faces-redirect=true";
	}
	
	/**
	 * aggiunge volo nuovo a lista voliNuovi distinguendo in andata e ritorno
	 * @return
	 * 
	 */
	public String aggiungiVoloNuovoAPacchetto() {
		//mi serve l id aggiornato per scrivere nella tabella VoloPacchetto senza causare errori
		//this.volo = this.voloMng.aggiungiVoloAPacchetto(volo);
		System.out.println(pacchetto.getNome());
		System.out.println(pacchetto.getId());
		System.out.println(volo.getId());
		//controllo il tipo e scelgo di aggiungere il volo all opportuna arraylist
		
		if (this.tipoVolo.equals("Andata")){
			
			this.pacchetto.getVoliNuoviAndata().add((VoloDTO) this.volo.clone());
			//serve solamante per mostrare a schermo
			this.pacchetto.getVoliAndata().put((VoloDTO) this.volo.clone(), "Andata");
			
		}
		else {
			this.voliNuoviRitorno.add((VoloDTO) this.volo.clone());
			//serve solamante per mostrare a schermo
			this.pacchetto.getVoliRitorno().add((VoloDTO) this.volo.clone());
		}
		
		//this.pkgMng.aggiungiVoloAPacchetto(pacchetto, volo, tipoVolo);
		
		
		
		return "aggiungiVoli?faces-redirect=true";
	}
	
	/**
	 * aggiunge volo esistente a lista voliEsistenti distinguendo in andata e ritorno
	 * @param volo
	 */
	public void aggiungiVoloEsistenteAPacchetto(VoloDTO volo) {
		this.voli.remove(volo);
		
		if (this.tipoVolo.equals("Andata")) {

			this.voliEsistentiAndata.add((VoloDTO) volo.clone());
			//serve solamante per mostrare a schermo
			this.pacchetto.getVoliAndata().put((VoloDTO) volo.clone(), "Andata");

		} else {
			this.voliEsistentiRitorno.add((VoloDTO) volo.clone());
			//serve solamante per mostrare a schermo
			this.pacchetto.getVoliRitorno().add((VoloDTO) volo.clone());
		}
		
		
	}
	
	public String aggiungiPacchetto() throws ParseException{
		
		//PRIMO STEP: aggiungo le info generali del pacchetto a db e ricavo l id giudto del pacchetto
		this.pacchetto = pkgMng.salvaInfoGenerali(pacchetto);
		
		//SECONDO STEP: aggiungo i voli di andata nuovi a db e prendo 
		//              l id del volo giusto e lo associo a pacchetto
		for (VoloDTO voloNuovoAndata : this.voliNuoviAndata) {
			voloNuovoAndata = this.voloMng.aggiungiVoloAPacchetto(voloNuovoAndata);
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloNuovoAndata, "Andata");
		}
		
		//TERZO STEP: aggiungo i voli di ritorno nuovi a db e prendo 
		//            l id del volo giusto e lo associo a pacchetto
		for (VoloDTO voloNuovoRitorno : this.voliNuoviRitorno) {
			voloNuovoRitorno = this.voloMng.aggiungiVoloAPacchetto(voloNuovoRitorno);
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloNuovoRitorno, "Ritorno");
		}
		
		//QUARTO STEP: associo i voli di andata esistenti al pacchetto 
		for (VoloDTO voloEsistenteAndata : this.voliEsistentiAndata) {
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloEsistenteAndata, "Andata");
		}
		
		//QUINTO STEP: associo i voli di ritorno esistenti al pacchetto
		for (VoloDTO voloEsistenteRitorno : this.voliEsistentiRitorno) {
			this.pkgMng.aggiungiVoloAPacchetto(pacchetto, voloEsistenteRitorno, "Ritorno");
		}
		
		return "catalogo?faces-redirect=true";
	}
	
	public void eliminaVoloAndata(VoloDTO volo){
		System.out.println(volo);
		this.pacchetto.getVoliNuoviAndata().remove(volo);
		this.voliNuoviAndata.contains(volo);
		this.pacchetto.getVoliAndata().remove(volo);
		
		System.out.println("-------ANDATA---------");
		for (VoloDTO volo3 : this.pacchetto.getVoliAndata().keySet()) {
			System.out.println(volo3);
			volo3.printaDati();
		}
		
		System.out.println("-------NUOVI---------");
		for (VoloDTO volo3 : this.pacchetto.getVoliNuoviAndata()) {
			System.out.println(volo3);
			volo3.printaDati();
		}
		
		System.out.println("-------ESISTENTI---------");
		for (VoloDTO volo3 : this.voliEsistentiAndata) {
			volo3.printaDati();
		}
		
	}
	
	
	
	public void eliminaVoloRitorno(VoloDTO volo){
		this.voliEsistentiRitorno.remove(volo);
		this.voliNuoviRitorno.remove(volo);
		this.pacchetto.getVoliRitorno().remove(volo);
		
		
		System.out.println("-------RITORNO---------");
		for (VoloDTO volo4 : this.pacchetto.getVoliRitorno()) {
			volo4.printaDati();
		}
		
	}


}
