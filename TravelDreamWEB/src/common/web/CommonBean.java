package common.web;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import traveldream.dtos.UtenteDTO;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "commonBean")
@RequestScoped
public class CommonBean {

	private UtenteDTO user;
	private String backup_mail;

	@EJB
	private UtenteMrg userMgr;

	private String gruppo;

	private String gruppo2 = "default";
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/home?faces-redirect=true";
	}

	public UtenteDTO getUser() {
		this.user = userMgr.getUserDTO();
		this.gruppo = userMgr.getGruppo(this.user);
		this.backup_mail = user.getEmail();
		return user;
	}

	public void setUser(UtenteDTO user) {
		this.user = user;
	}

	public String getGruppo() {
		return gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}
	
	public void updateUtente(){
		System.out.println(user.getNome());
		System.out.println(user.getEmail());
		userMgr.aggiornaUtente(user, backup_mail);
	}

	public String getBackup_mail() {
		return backup_mail;
	}

	public void setBackup_mail(String backup_mail) {
		this.backup_mail = backup_mail;
	}

	public String getGruppo2() {
	
			return this.gruppo2;
		
		
	}
	
	public void aggiorna(){
		System.out.println("prima");
		this.user = userMgr.getUserDTO();
		this.gruppo2 = userMgr.getGruppo(user);
		System.out.println("chiamato da js");
	}

	public void setGruppo2(String gruppo2) {
		this.gruppo2 = gruppo2;
	}

}
