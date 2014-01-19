package store.web;


import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.VoloMng;

@ManagedBean(name = "bookBean")
@SessionScoped
public class BookBean implements Serializable {

	private static final long serialVersionUID = 2502104564094834379L;

	@EJB
	private PacchettoMng pkgMng;

	@EJB
	private HotelMng hotelMng;

	@EJB
	private VoloMng voloMng;
	
	private int persone;
	
	private Date date1;
	
	private Date date2;

	public int getPersone() {
		return persone;
	}

	public void setPersone(int persone) {
		this.persone = persone;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}


}
