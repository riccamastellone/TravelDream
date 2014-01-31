package registrazione.web;


import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.UtenteDTO;
import traveldream.manager.UtenteMrg;


@ManagedBean(name="registrazioneBean")
@SessionScoped
public class RegistrazioneBean implements Serializable {

	private static final long serialVersionUID = -8695101190385504696L;

	private UtenteDTO user;
	
	private String vecchiaEmail;
	
	@EJB
	private UtenteMrg userMgr;
	
	public RegistrazioneBean() {
		user = new UtenteDTO();
	}

	public void setUser(UtenteDTO user) {
		this.user = user;
	}
	

	public UtenteDTO getUser() {
		return user;
	}
	

	public void validateUsername(String email) {
		if (userMgr.existEmail(email)){
			throw new ValidatorException(new FacesMessage("Username already used.Choose another one."));
		}
	}
	
	
	
	public String getUtenteNome() {
		return userMgr.getUserDTO().getNome();
	}
	
	/**
	 * 
	 * passa al busibess tier l'informazioni del nuovo CLIENTE
	 * @return
	 */
	public String register(){
		try {
			System.out.println("bottone premuto");
			this.validateUsername(user.getEmail());
			userMgr.salvaUtente(user, "cliente");
			return "home?faces-redirect=true";
		} catch (ValidatorException e) {
			RequestContext.getCurrentInstance().execute("errorDialog.show()");
			return null;
		}
		
	}
	
	/**
	 * 
	 * passa al busibess tier l'informazioni del nuovo DIPENDENTE
	 * @return
	 */
	public String aggiungiDipendente(){
		try {
		System.out.println("bottone premuto");
		this.validateUsername(user.getEmail());
		userMgr.salvaUtente(user, "dipendnte");
		this.user = new UtenteDTO();
		return "home?faces-redirect=true";
		} catch (ValidatorException e) {
			
			return null;
		}
	}
	
	/**
	 * 
	 * carica la pagina edita con i campi del dipendente passato
	 * @param dipendente
	 * @return
	 */
	public String goToEdit(UtenteDTO dipendente){
		this.user = dipendente;
		this.vecchiaEmail = dipendente.getEmail();
		return "edita?faces-redirect=true";
	}
	
	//DEBUG
	public void printaUser() {
		System.out.println("--DOPO CARICAMENTO--");
		this.user.printaDati();
	}
	
	public String editDipendente() {
		System.out.println("tato premuto");
		userMgr.aggiornaUtente(user, vecchiaEmail);
		return "home?faces-redirect=true";
	}
	
	public void getGruppoBean(){
		System.out.println(userMgr.getGruppo(userMgr.getUserDTO()));
	}
	
	public void deleteDipendente(UtenteDTO utente) {
		this.userMgr.deleteDipendente(utente);
		
	}

}
