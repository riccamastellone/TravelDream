package store.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import traveldream.dtos.AttivitaSecondariaDTO;
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

	private ArrayList<PacchettoDTO> topDeals;

	// Da usare per l'autocomplete
	private ArrayList<String> depCities;

	private ArrayList<String> arrCities;
	
	// carichiamo qua dentro l id pacchetto per il dettaglio
	private int idPacchetto;

	private PacchettoDTO pacchetto;
	
	// carichiamo qua l id hotel per il dettaglio
	private int idHotel;
	
	private HotelDTO hotel;
	
	private List<PacchettoDTO> pacchetti;
	
	
		
		
	// Salviamo temporaneamente il nome dell'immagine dato che per qualche strana ragione
	// viene chiamata piu' volte la funzione.
	private String tmpImage;
	
	public FrontendBean(){
		this.pacchetto = new PacchettoDTO();
		this.pacchetti = new ArrayList<PacchettoDTO>();
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
	 * ritorno piu' economici, calcola i giorni di differenza e con questi
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
		float hotelCosto = hotel.getCostoGiornaliero() * diffDays;

		float totalePacchetto = hotelCosto + voloAndataCosto + voloRitornoCosto;

		// return 12;
		return totalePacchetto;
	}
	
	public PacchettoDTO getLastMinute() {
		//List<PacchettoDTO> tmp = ;
		return pkgMng.getAllPacchetti().get(0);
	}
	/**
	 * Quando valutiamo il costo di un pacchetto, controlla tutti i voli e le partenze 
	 * per avere il costo più basso. Questo metodo ritorna la data del volo di partenza 
	 * considerato
	 * @return
	 */
	public String getPartenzaCheap(PacchettoDTO pacchetto){
		
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
		
		
		return new SimpleDateFormat("dd MMM").format(dataAndata).toUpperCase();
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
	private StreamedContent doGenerateImage(String imgName, int width, int height) throws IOException {
		


		File img = new File("/var/uploads/up/" + imgName);
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
	
	/**
	 * Quando Pretty Faces chiama questo metodo per settare l'id, carichiamo
	 * anche l hotel corrispondente
	 * 
	 * @param idPacchetto
	 */
	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
		hotel = hotelMng.findHotelDTO(idHotel);
		this.pacchetto = new PacchettoDTO();
		this.pacchetto.setLocalita(hotel.getLuogo());
		this.pacchetto.setHotel(hotel);

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

	public String getTmpImage() {
		return tmpImage;
	}

	public void setTmpImage(String tmpImage) {
		this.tmpImage = tmpImage;
	}

	public StreamedContent generateImage() throws IOException {
		
		FacesContext context = FacesContext.getCurrentInstance();

	    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
	        // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
	        return new DefaultStreamedContent();
	    }
	    else {
	        // So, browser is requesting the image. Get ID value from actual request param.
	    	String img = context.getExternalContext().getRequestParameterMap().get("img");
	    	String height = context.getExternalContext().getRequestParameterMap().get("height");
	    	String width = context.getExternalContext().getRequestParameterMap().get("width");
	        
	        return this.doGenerateImage(img, Integer.parseInt(width), Integer.parseInt(height));
	    }
	    
	   
	}

	public int getIdHotel() {
		return idHotel;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public List<PacchettoDTO> getPacchetti() {
		if (this.pacchetti.isEmpty()) {
			this.pacchetti = pkgMng.getAllPacchetti();

			for (Iterator<PacchettoDTO> pacchettidaFiltrare = this.pacchetti.iterator(); pacchettidaFiltrare.hasNext();) {
				PacchettoDTO pacchettoDaControllare = pacchettidaFiltrare.next();
				if (pacchettoDaControllare.getOk().equals("X")) {
					pacchettidaFiltrare.remove();
				}

			}
			
			System.out.println("----pacchetti ricavati----");
			System.out.println(pacchetti.size());
			for (PacchettoDTO pacchettoDTO : this.pacchetti) {
				System.out.println(pacchettoDTO.getHotel().getNome());
			}
		}

		return this.pacchetti;
	}

	public void setPacchetti(List<PacchettoDTO> pacchetti) {
		this.pacchetti = pacchetti;
	}
	
	
	


}
