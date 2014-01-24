package store.web;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.ListaDesideriMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.ShareMng;

@ManagedBean(name = "listaDesideriBean")
@SessionScoped
public class ShareBean implements Serializable{
	
	
	private static final long serialVersionUID = -4029975647697609232L;
	
	@EJB
	private ShareMng shareMng;
	
	@EJB
	private PacchettoMng pkgMng;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	private String idShare;
	private SecureRandom random;
	
	public ShareBean(){
		random = new SecureRandom();
	}
	
	private String generaChiave() {
	    return new BigInteger(130, random).toString(32);
	  }
	

	public String getIdShare() {
		return idShare;
	}

	public void setIdShare(String idShare) {
		int idPacchetto = shareMng.getIdPacchettoFromChiave(idShare);
		FrontendBean fb = new FrontendBean();
		fb.setIdPacchetto(idPacchetto);
		this.idShare = idShare;
	}
	
	public SecureRandom getRandom() {
		return random;
	}

	public void setRandom(SecureRandom random) {
		this.random = random;
	}

}
