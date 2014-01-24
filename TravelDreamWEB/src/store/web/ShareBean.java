package store.web;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.PacchettoDTO;
import traveldream.manager.PacchettoMng;
import traveldream.manager.ShareMng;

@ManagedBean(name = "shareBean")
@SessionScoped
public class ShareBean implements Serializable{
	
	
	private static final long serialVersionUID = -4029975647697609232L;
	
	@EJB
	private ShareMng shareMng;
	
	@EJB
	private PacchettoMng pkgMng;
	
	
	private String chiave;
	private SecureRandom random;
	private int idshare;
	private PacchettoDTO pacchetto;
	
	public ShareBean(){
		random = new SecureRandom();
	}
	
	private String generaChiave() {
	    return new BigInteger(130, random).toString(32);
	  }
	

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		int idPacchetto = shareMng.getIdPacchettoFromChiave(chiave);
		idshare = shareMng.getIdShareFromChiave(chiave);
		pacchetto = pkgMng.findPacchettoDTO(idPacchetto);
		System.out.println("Chiave: " + chiave);
		System.out.println("Pacchetto: " + idPacchetto);
		this.chiave = chiave;
	}
	
	public String acceptInvitation() {
		shareMng.acceptInvitation(idshare);
		
		// Mandiamolo alla registrazione
		return "/registrazione?faces-redirect=true";
	}
	
	public SecureRandom getRandom() {
		return random;
	}

	public void setRandom(SecureRandom random) {
		this.random = random;
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	public int getIdshare() {
		return idshare;
	}

	public void setIdshare(int idshare) {
		this.idshare = idshare;
	}

}
