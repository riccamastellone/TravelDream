package store.web;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import traveldream.dtos.PacchettoDTO;
import traveldream.manager.PacchettoMng;

@ManagedBean(name = "frontendBean")
public class FrontendBean {
	
	@EJB
	private PacchettoMng pkgMng;
	
	private ArrayList<PacchettoDTO> lastMinute;
	
	private ArrayList<PacchettoDTO> topDeals;

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

}
