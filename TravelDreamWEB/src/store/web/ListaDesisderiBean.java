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

import org.primefaces.context.RequestContext;
import org.primefaces.expression.impl.ThisExpressionResolver;

import traveldream.dtos.AttivitaSecondariaDTO;
import traveldream.dtos.ListaDesideriDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.UtenteDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.ListaDesideriMng;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "listaDesideriBean")
@SessionScoped
public class ListaDesisderiBean implements Serializable{
	
	
	private static final long serialVersionUID = -4029975305697609232L;
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	private List<ListaDesideriDTO> listaDesideriUtente;
	
	private String utenteProprietario = "";
	
	private String nomePagante = "";
	
	private ListaDesideriDTO listaDaPagare;

	
	private String date1;
	
	private String date2;
	
	private Date inizio;
	
	private Date fine;
	
	private float costo = 0;
	
	private VoloDTO voloAndata;
	
	private VoloDTO voloRitorno;
	
	public ListaDesisderiBean(){
		
		this.listaDesideriUtente = new ArrayList<ListaDesideriDTO>();
		this.voloAndata = new VoloDTO();
		this.voloRitorno = new VoloDTO();
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
		RequestContext.getCurrentInstance().execute("ciaoDialog.hide()");
		System.out.println("pagato");
		System.out.println(nomePagante);
		//this.listaDesideriMng.pagaPacchttoInListaDesideri(lista, "bruno");
		
	}
	
	public void goToPaga() throws ParseException{
		//this.listaDaPagare = lista;
		/*
		if (this.voloAndata.getCittaPartenza() == null || this.voloRitorno.getCittaPartenza() == null){
			RequestContext.getCurrentInstance().execute("errorDialog.show()");
			return;
		}

		else {
		*/
		//System.out.println(this.voloAndata.getCittaArrivo());
			//this.costo = this.calcolaCostoPrenotazione(lista.getPacchetto()); 
			//System.out.println(this.costo);
			RequestContext.getCurrentInstance().execute("errorDialog.show()");
		
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


	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
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

}
