package store.web;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.PacchettoDTO;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.VoloMng;

@ManagedBean(name = "frontendBean")
@SessionScoped
public class FrontendBean {

	@EJB
	private PacchettoMng pkgMng;

	@EJB
	private HotelMng hotelMng;

	@EJB
	private VoloMng voloMng;
	
	private ArrayList<PacchettoDTO> lastMinute;

	private ArrayList<PacchettoDTO> topDeals;

	// Da usare per l'autocomplete
	private ArrayList<String> depCities;

	private ArrayList<String> arrCities;

	private String test;

	public ArrayList<PacchettoDTO> getLastMinute() {
		return lastMinute;
	}

	public void setLastMinute(ArrayList<PacchettoDTO> lastMinute) {
		this.lastMinute = lastMinute;
	}

	public ArrayList<PacchettoDTO> getTopDeals() {
		return topDeals;
	}

	public void setTopDeals(ArrayList<PacchettoDTO> topDeals) {
		this.topDeals = topDeals;
	}

	public String getStarsImage(int stars) {
		System.out.println("images/rating-" + stars + ".png");
		return "images/rating-" + stars + ".png";
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public ArrayList<String> getArrCities() {
		if(arrCities == null) {
			arrCities = voloMng.getCittaArrivo();
		}
		return arrCities;
	}

	public void setArrCities(ArrayList<String> arrCities) {
		this.arrCities = arrCities;
	}

	public ArrayList<String> getDepCities() {
		if(depCities == null) {
			depCities = voloMng.getCittaPartenza();
		}
		return depCities;
	}

	public void setDepCities(ArrayList<String> depCities) {
		this.depCities = depCities;
	}

}
