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
import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import traveldream.dtos.HotelDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.HotelMng;
import traveldream.manager.PacchettoMng;
import traveldream.manager.VoloMng;

@ManagedBean(name = "frontendBean")
@SessionScoped
public class FrontendBean implements Serializable {

	private static final long serialVersionUID = 2502108454094834379L;

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

	private int idPacchetto;

	private PacchettoDTO pacchetto;
	
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

	public ArrayList<String> getArrCities() {
		if (arrCities == null) {
			arrCities = voloMng.getCittaArrivo();
		}
		return arrCities;
	}

	public void setArrCities(ArrayList<String> arrCities) {
		this.arrCities = arrCities;
	}

	public ArrayList<String> getDepCities() {
		if (depCities == null) {
			depCities = voloMng.getCittaPartenza();
		}
		return depCities;
	}

	public void setDepCities(ArrayList<String> depCities) {
		this.depCities = depCities;
	}

	/**
	 * Calcoliamo il totale di un pacchetto prendendo il volo di andata e di
	 * ritorno pi�� economici, calcola i giorni di differenza e con questi
	 * calcola il totale dell'hotel (Da pensare una cosa migliore)
	 * 
	 * @param pacchetto
	 * @return
	 */
	public float getTotalePacchetto(PacchettoDTO pacchetto) {

		// per calcolare il costo del pacchetto consideriamo i voli
		// meno costosi
		float voloAndataCosto = 0;
		Date dataAndata = new Date();
		for (VoloDTO volo : pacchetto.getVoliAndata()) {
			// primo ciclo
			if (voloAndataCosto == 0) {
				voloAndataCosto = volo.getCosto();
				dataAndata = volo.getArrivo();
			}
			if (voloAndataCosto > volo.getCosto()) {
				voloAndataCosto = volo.getCosto();
				dataAndata = volo.getArrivo();
			}
		}

		float voloRitornoCosto = 0;
		Date dataRitorno = new Date();
		for (VoloDTO volo : pacchetto.getVoliRitorno()) {
			// primo ciclo
			if (voloRitornoCosto == 0) {
				voloRitornoCosto = volo.getCosto();
				dataRitorno = volo.getPartenza();
			}
			if (voloRitornoCosto > volo.getCosto()) {
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

		// return 12;
		return totalePacchetto;
	}

	/**
	 * Generiamo in run time l'immagine della dimensione che ci serve
	 * 
	 * @param imgName
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public StreamedContent generateImage(String imgName, int width, int height) throws IOException {
		File img = new File("/var/uploads/up/" + imgName);
		System.out.println(img);
		BufferedImage image = ImageIO.read(img);
		int x = 0;
		int y = 0;

		if (image.getWidth() > image.getHeight()) {
			image = Scalr.resize(image, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, (int) (height * 1.45));
			x = (image.getWidth() - width) / 2;
			if (x < 0)
				x = 0;
		} else {
			image = Scalr.resize(image, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, (int) (width * 1.45));
			y = (image.getHeight() - height) / 2;
			if (y < 0)
				y = 0;
		}
		image = Scalr.crop(image, x, y, width, height);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", os);
		return new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
	}

	public int getIdPacchetto() {
		return idPacchetto;
	}

	/**
	 * Quando Pretty Faces chiama questo metodo per settare l'id, carichiamo
	 * anche il pacchetto corrispondente
	 * 
	 * @param idPacchetto
	 */
	public void setIdPacchetto(int idPacchetto) {
		this.idPacchetto = idPacchetto;
		pacchetto = pkgMng.findPacchettoDTO(this.idPacchetto);

	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	/**
	 * A volte e' piu' carino avere qualche <br />
	 * nel testo
	 * 
	 * @param s
	 * @return
	 */
	public static String nl2br(String s) {
		return s.replaceAll("\n", "<br/>");
	}
	

}
