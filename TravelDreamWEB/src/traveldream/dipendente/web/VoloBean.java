package traveldream.dipendente.web;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import traveldream.dtos.VoloDTO;
import traveldream.gestioneComponente.ComponenteMng;

@ManagedBean(name="voloBean")
@RequestScoped
public class VoloBean {

	//@EJB
	//private ComponenteMng cmpMng;
	
	private VoloDTO volo;
	
	public VoloBean(){
		this.setVolo(new VoloDTO());
	}
	
	public String aggiungiVolo(){
		return null;
	}

	public VoloDTO getVolo() {
		return volo;
	}

	public void setVolo(VoloDTO volo) {
		this.volo = volo;
	}
}
