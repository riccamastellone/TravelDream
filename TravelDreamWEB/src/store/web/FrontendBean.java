package store.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
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
	
	/**
	 * Calcoliamo il totale di un pacchetto prendendo il volo di andata e di ritorno più economici,
	 * calcola i giorni di differenza e con questi calcola il totale dell'hotel
	 * (Da pensare una cosa migliore)
	 * @param pacchetto
	 * @return
	 */
	public float getTotalePacchetto(PacchettoDTO pacchetto) {
		
		
		// per calcolare il costo del pacchetto consideriamo i voli
		// meno costosi
		float voloAndataCosto = 0;
		Date dataAndata = new Date();
		for(VoloDTO volo : pacchetto.getVoliAndata()) {
			// primo ciclo
			if(voloAndataCosto == 0) {
				voloAndataCosto = volo.getCosto();
				dataAndata = volo.getArrivo();
			}
			if(voloAndataCosto > volo.getCosto()) {
				voloAndataCosto = volo.getCosto();
				dataAndata = volo.getArrivo();
			}
		}
		
		float voloRitornoCosto = 0;
		Date dataRitorno = new Date();
		for(VoloDTO volo : pacchetto.getVoliRitorno()) {
			// primo ciclo
			if(voloRitornoCosto == 0) {
				voloRitornoCosto = volo.getCosto();
				dataRitorno = volo.getPartenza();
			}
			if(voloRitornoCosto > volo.getCosto()) {
				voloRitornoCosto = volo.getCosto();
				dataRitorno = volo.getPartenza();
			}
		}
		
		long diff = Math.abs(dataRitorno.getTime() - dataAndata.getTime());
		int diffDays = (int) Math.ceil((diff + 12 * 60 * 60 * 1000) / (24 * 60 * 60 * 1000));
		
		System.out.println("Giorni di differenza: " + diffDays);
		
		HotelDTO hotel = pacchetto.getHotel();
		System.out.println(hotel);
		float hotelCosto = hotel.getCostoGiornaliero() * diffDays;
		
		float totalePacchetto = hotelCosto + voloAndataCosto + voloRitornoCosto;

		//return 12;
		return totalePacchetto;
	}

}
