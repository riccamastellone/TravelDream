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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.VoloMng;

@ManagedBean(name = "mailBean")
@SessionScoped
public class MailBean implements Serializable {

	private static final long serialVersionUID = 2502104564094834379L;

	private String nome;
	private String telefono;
	private String mail;
	private String messaggio;
	private String js;


	public MailBean(){
	}
	
	
	public void sendMail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("localhost");
		email.setSmtpPort(25);
		email.setFrom("traveldream@rmdesign.it");
		email.setSubject("Mail dal sito");
		email.setMsg("Nome " + nome + "\n\rTelefono " + telefono + "\n\rEmail "+ mail + "\n\r\n\r" + messaggio);
		//email.addTo("riccardo.mastellone@gmail.com");
		email.addTo("seba0691@gmail.com");
		email.send();
		nome = "";
		telefono = "";
		mail = "";
		messaggio = "";
		

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
