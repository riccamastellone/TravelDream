package registrazione.web;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import registrazione.client.UtenteDTO;
import registrazione.client.UtenteMrg;

@ManagedBean(name="registrazioneBean")
@SessionScoped
public class RegistrazioneBean {
	
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
	

	public void validateUsername(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (userMgr.existEmail((String)value)){
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
		System.out.println("bottone premuto");
		userMgr.salvaUtente(user, "cliente");
		return "home?faces-redirect=true";
	}
	
	/**
	 * 
	 * passa al busibess tier l'informazioni del nuovo DIPENDENTE
	 * @return
	 */
	public String aggiungiDipendente(){
		System.out.println("bottone premuto");
		userMgr.salvaUtente(user, "dipendnte");
		return "homeAdmin?faces-redirect=true";
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
	
	public void editDipendente() {
		System.out.println("tato premuto");
		userMgr.aggiornaUtente(user, vecchiaEmail);
	}
	
	public void getGruppoBean(){
		System.out.println(userMgr.getGruppo(userMgr.getUserDTO()));
	}
	

}
