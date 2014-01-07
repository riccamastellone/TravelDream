package traveldream.dipendente.web;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.expression.impl.ThisExpressionResolver;

import traveldream.dtos.VoloDTO;
import traveldream.gestioneComponente.ComponenteMng;

@ManagedBean(name="voloBean")
@RequestScoped
public class VoloBean {

	//@EJB
	//private ComponenteMng cmpMng;
	
	private VoloDTO volo;
	
	private String dataPartenza;
	
	private String dataArrivo;
	
	public VoloBean(){
		this.volo = new VoloDTO();

	}
	

	public VoloDTO getVolo() {
		return volo;
	}

	public void setVolo(VoloDTO volo) {
		this.volo = volo;
	}
	
	public void aggiungiVolo() throws ParseException{
		this.volo.setPartenza(this.converti(dataPartenza));
		this.volo.setArrivo(this.converti(dataArrivo));
		System.out.println(this.volo);
		
		this.volo.printaDati();
	}
	
	public Date converti(String data) throws ParseException{
		if (data.equals("")){
			return null;
		}
		 SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		 Date convertedDate = dateFormat.parse(data);
		 return convertedDate;
	}
	


	public String getDataPartenza() {
		return dataPartenza;
	}


	public void setDataPartenza(String dataPartenza) {
		this.dataPartenza = dataPartenza;
	}


	public String getDataArrivo() {
		return dataArrivo;
	}


	public void setDataArrivo(String dataArrivo) {
		this.dataArrivo = dataArrivo;
	}
	
	
}
