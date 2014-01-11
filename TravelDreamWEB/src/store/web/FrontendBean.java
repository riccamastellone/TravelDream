package store.web;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.PacchettoDTO;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;

@ManagedBean(name = "frontendBean")
@SessionScoped
public class FrontendBean {

	@EJB
	private PacchettoMng pkgMng;
	
	@EJB
	private HotelMng hotelMng;
	
	private ArrayList<PacchettoDTO> lastMinute;

	private ArrayList<PacchettoDTO> topDeals;

	// Da usare per l'autocomplete
	private ArrayList<String> airports;

	// Da usare per l'autocomplete (citta prese da alberghi)
	private ArrayList<String> cities;

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

	public ArrayList<String> getAirports() {
		return airports;
	}

	public void setAirports(ArrayList<String> airports) {
		this.airports = airports;
	}

	public ArrayList<String> getCities() {
		if(cities != null){
			this.setCities(hotelMng.getCitta());
		}
		return cities;
	}

	public void setCities(ArrayList<String> cities) {
		this.cities = cities;
	}

}
