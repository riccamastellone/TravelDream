package common.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



import traveldream.dtos.UtenteDTO;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "commonBean")
@SessionScoped
public class CommonBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6309040245211680506L;
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
	System.out.println(this.gruppo2);
			return this.gruppo2;
		
		
	}
	
	public void aggiorna(){
		System.out.println("prima");
		this.user = userMgr.getUserDTO();
		System.out.println(this.user.getNome());
		this.gruppo2 = userMgr.getGruppo(user);
		System.out.println(this.gruppo2);
		System.out.println("chiamato da js");
	}

	public void setGruppo2(String gruppo2) {
		this.gruppo2 = gruppo2;
	}

}
