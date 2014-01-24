package store.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.imgscalr.Scalr;
import org.primefaces.expression.impl.ThisExpressionResolver;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.UtenteMrg;
import traveldream.manager.VoloMng;

@ManagedBean(name = "mailBean")
@SessionScoped
public class MailBean implements Serializable {

	private static final long serialVersionUID = 2502104564094834379L;

	private String nome;
	private String telefono;
	private String mail;
	private String messaggio;
	
	@EJB
	private UtenteMrg userMgr;


	public MailBean(){
	}
	
	
	public void sendMail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("localhost");
		email.setSmtpPort(25);
		email.setFrom("traveldream@rmdesign.it");
		email.setSubject("Mail dal sito");
		email.setMsg("Nome " + nome + "\n\rTelefono " + telefono + "\n\rEmail "+ mail + "\n\r\n\r" + messaggio);
		email.addTo("riccardo.mastellone@gmail.com");
		email.addTo("seba0691@gmail.com");
		email.send();
		nome = "";
		telefono = "";
		mail = "";
		messaggio = "";
		

	}
	
	public void sendListaDesideri() throws EmailException {
		System.out.println(this.mail);
		
		Email email = new SimpleEmail();
		email.setHostName("localhost");
		email.setSmtpPort(25);
		email.setFrom("traveldream@rmdesign.it");
		email.setSubject("Lista desideri");
		email.setMsg("Ciao sono " + this.userMgr.getUserDTO().getNome() + " e questa �� la mia lista desideri \n\r http://localhost:8080/TravelDreamWEB/out/visualizzaLista.xhtml?user=" + this.userMgr.getUserDTO().getEmail()) ;
		//email.addTo("riccardo.mastellone@gmail.com");
		email.addTo(this.mail);
		email.send();
		System.out.println("invio");
		
		

	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getMessaggio() {
		return messaggio;
	}


	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}


}
