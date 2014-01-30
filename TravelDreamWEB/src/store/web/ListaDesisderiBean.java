package store.web;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.expression.impl.ThisExpressionResolver;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.ListaDesideriDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.PrenotazioneDTO;
import traveldream.dtos.UtenteDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.ListaDesideriMng;
import traveldream.manager.PrenotazioneMng;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "listaDesideriBean")
@SessionScoped
public class ListaDesisderiBean implements Serializable{
	
	
	private static final long serialVersionUID = -4029975305697609232L;
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private PrenotazioneMng prenotazioneMng;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	private List<ListaDesideriDTO> listaDesideriUtente;
	
	private String utenteProprietario = "";
	
	private String nomePagante = "";
	
	private ListaDesideriDTO listaDaPagare;

	private List<VoloDTO> listaVoliAndata;
	
	private List<VoloDTO> listaVoliRitorno;
	
	
	private float costo = 0;
	
	private VoloDTO voloAndata;
	
	private VoloDTO voloRitorno;
	
	public ListaDesisderiBean(){
		
		this.listaDesideriUtente = new ArrayList<ListaDesideriDTO>();
		this.voloAndata = new VoloDTO();
		this.voloRitorno = new VoloDTO();
		this.listaVoliAndata = new ArrayList<VoloDTO>();
		this.listaVoliRitorno = new ArrayList<VoloDTO>();
		
	}
	
	public String getNomePagante() {
		return nomePagante;
	}

	public void setNomePagante(String nomePagante) {
		this.nomePagante = nomePagante;
	}

	public ListaDesideriDTO getListaDaPagare() {
		return listaDaPagare;
	}

	public void setListaDaPagare(ListaDesideriDTO listaDaPagare) {
		this.listaDaPagare = listaDaPagare;
	}

	public VoloDTO getVoloAndata() {
		return voloAndata;
	}

	public void setVoloAndata(VoloDTO voloAndata) {
		this.voloAndata = voloAndata;
	}

	public VoloDTO getVoloRitorno() {
		return voloRitorno;
	}

	public void setVoloRitorno(VoloDTO voloRitorno) {
		this.voloRitorno = voloRitorno;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public List<VoloDTO> getListaVoliAndata() {
		return listaVoliAndata;
	}

	public void setListaVoliAndata(List<VoloDTO> listaVoliAndata) {
		this.listaVoliAndata = listaVoliAndata;
	}

	public List<VoloDTO> getListaVoliRitorno() {
		return listaVoliRitorno;
	}

	public void setListaVoliRitorno(List<VoloDTO> listaVoliRitorno) {
		this.listaVoliRitorno = listaVoliRitorno;
	}
	
	public void addAListaDesideri(PacchettoDTO pacchetto){
		
		ListaDesideriDTO lista = new ListaDesideriDTO();
		lista.setUtente(this.userMgr.getUserDTO());
		lista.setPacchetto(pacchetto);
		this.listaDesideriMng.addAListaDesideri(lista);
		System.out.println(pacchetto.getNome());
		
	}
	
	
	public void getListaDesideriUtenteProprietario(){
		this.listaDesideriUtente.clear();
		UtenteDTO utente = new UtenteDTO();
		utente.setEmail(this.utenteProprietario);
		List<ListaDesideriDTO> listaDaScremare = new ArrayList<ListaDesideriDTO>(); 
		listaDaScremare = this.listaDesideriMng.getListaDesisderiUtente(utente);
		
		for (ListaDesideriDTO listaDesideriDTO : listaDaScremare) {
			if (listaDesideriDTO.getPagatoDa() == null ){
				this.listaDesideriUtente.add(listaDesideriDTO);
			}
		}
		
		for (Iterator<ListaDesideriDTO> s = this.listaDesideriUtente.iterator(); s.hasNext(); ) {
			
			ListaDesideriDTO listaDaControllare = s.next();
			if (listaDaControllare.getPacchetto().getOk().equals("X") || listaDaControllare.getPacchetto().getEliminato() == 1) {
				s.remove();
			}
		}
		
		this.listaDesideriUtente.size();
		
	}

	

	public List<ListaDesideriDTO> getListaDesideriUtente() {
		
		this.getListaDesideriUtenteProprietario();
		
		return listaDesideriUtente;
	}

	public void setListaDesideriUtente(List<ListaDesideriDTO> listaDesideriUtente) {
		this.listaDesideriUtente = listaDesideriUtente;
	}

	public String getUtenteProprietario() {
		return utenteProprietario;
	}

	public void setUtenteProprietario(String utenteProprietario) {
		this.utenteProprietario = utenteProprietario;
	}
	
	public void pagaPacchetto(){
		
		RequestContext.getCurrentInstance().execute("costoDialog.hide()");
		System.out.println("pagato");
		System.out.println(nomePagante);
		PrenotazioneDTO prenotazione = new PrenotazioneDTO();
		prenotazione.setHotel(this.listaDaPagare.getPacchetto().getHotel());
		prenotazione.setPersone(1);
		prenotazione.setUtente(this.userMgr.getUtenteByMail(this.utenteProprietario));
		prenotazione.setVoloAndata(this.voloAndata);
		prenotazione.setVoloRitorno(this.voloRitorno);
		prenotazione.setCostoPersona(this.costo);
		prenotazione.setListAttivitaSecondarie(listaDaPagare.getPacchetto().getAttivitaSecondarie());
		this.prenotazioneMng.Prenota(prenotazione);
		this.voloAndata = new VoloDTO();
		this.voloRitorno = new VoloDTO();
		this.listaDesideriMng.pagaPacchttoInListaDesideri(this.listaDaPagare, nomePagante);
		
	}
	
	public void goToPaga(ActionEvent action, ListaDesideriDTO lista) {
		this.listaDaPagare = lista;
		
		this.voloAndata = new VoloDTO();
		this.voloRitorno = new VoloDTO();
		this.listaVoliAndata = lista.getPacchetto().getVoliAndata();
		this.listaVoliRitorno = lista.getPacchetto().getVoliRitorno();
	
		RequestContext.getCurrentInstance().execute("scegliDialog.show()");
		
		
	}
	
	public void printa(ActionEvent action) {
		
		if (this.voloAndata == null || this.voloRitorno == null){
			RequestContext.getCurrentInstance().execute("errorDialog.show()");
			return;
		}

		else {
			
			System.out.println(this.voloAndata.getCittaArrivo());
			System.out.println(this.voloRitorno.getCittaPartenza());
			this.costo = calcolaCostoPrenotazione(this.listaDaPagare.getPacchetto());
			System.out.println(this.costo);
			RequestContext.getCurrentInstance().execute("scegliDialog.hide()");
			RequestContext.getCurrentInstance().execute("costoDialog.show()");
		}
		
	}
	
	public float calcolaCostoPrenotazione(PacchettoDTO pacchetto) {
		float costo = 0;
		try {
			// Costo dei voli

				costo += this.voloAndata.getCosto() + this.voloRitorno.getCosto();

				// Costo dell'hotel

				// Calcolo la durata della permanenza
				long diff = Math.abs(this.voloRitorno.getPartenza().getTime() - this.voloAndata.getArrivo().getTime());
				int diffDays = (int) Math.ceil((diff + 12 * 60 * 60 * 1000) / (24 * 60 * 60 * 1000));

				costo += pacchetto.getHotel().getCostoGiornaliero() * diffDays;

				// Costo delle attivita secondarie
				for (AttivitaSecondariaDTO attivita : pacchetto.getAttivitaSecondarie()) {
					costo += attivita.getCosto();
				}

			
			return costo;
		} catch (NullPointerException e) {
			return 0;		
		}
		
	}

	

}
