package store.web;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.ShareDTO;
import traveldream.dtos.UtenteDTO;
import traveldream.manager.ListaDesideriDTO;
import traveldream.manager.PacchettoMng;
import traveldream.manager.ShareMng;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "shareBean")
@SessionScoped
public class ShareBean implements Serializable {

	private static final long serialVersionUID = -4029975647697609232L;

	@EJB
	private ShareMng shareMng;

	@EJB
	private PacchettoMng pkgMng;

	@EJB
	private UtenteMrg userMgr;

	private String chiave;
	private SecureRandom random;
	private int idshare;
	private PacchettoDTO pacchetto;
	private String email;

	private List<ShareDTO> shares;

	public ShareBean() {
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

	public void shareVacation(PacchettoDTO pacchetto) throws EmailException {
		System.out.println(email);

		String key = generaChiave();
		System.out.println(key);

		shareMng.createShare(pacchetto, userMgr.getUserDTO(), email, key);

		// Generiamo l'url
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String requestURL = request.getRequestURL().toString();
		String url = requestURL.substring(0, requestURL.substring(0, requestURL.lastIndexOf("/")).lastIndexOf("/")) + "/invitation/" + key;

		System.out.println(url);
		System.out.println(userMgr.getUserDTO().getNome());

		Email s_email = new SimpleEmail();
		s_email.setHostName("localhost");
		s_email.setSmtpPort(25);
		s_email.setFrom("traveldream@rmdesign.it");
		s_email.setSubject("TravelDream Invitation");
		s_email.setMsg("You have been invited to join your friend " + userMgr.getUserDTO().getNome() + " on TravelDream\n" + "Click here to view the package he'd like you to join: " + url);
		s_email.addTo(email);
		s_email.send();

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ShareDTO> getShares() {
		if (shares == null) {
			shares = shareMng.getSharesUtente(this.userMgr.getUserDTO());

			for (ShareDTO s : shares) {
				for (List<String> ss : s.getAmici()) {
					if (ss.get(1).equals("accettato")) {
						ss.add("<span class='glyphicon glyphicon-ok'></span> Accepted");
					} else {
						ss.add("<span class='glyphicon glyphicon-remove'></span> Still waiting");
					}
				}

			}
		}
		return shares;
	}

	public void setShares(List<ShareDTO> shares) {
		this.shares = shares;
	}

}
