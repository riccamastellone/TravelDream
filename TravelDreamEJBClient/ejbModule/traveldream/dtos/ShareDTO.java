package traveldream.dtos;

import java.util.List;

public class ShareDTO {

	private int id;
	
	private PacchettoDTO pacchetto;
	
	private List<List<String>> amici;
	
	
	public ShareDTO(){

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PacchettoDTO getPacchetto() {
		return pacchetto;
	}

	public void setPacchetto(PacchettoDTO pacchetto) {
		this.pacchetto = pacchetto;
	}

	public List<List<String>> getAmici() {
		return amici;
	}

	public void setAmici(List<List<String>> amici) {
		this.amici = amici;
	}


}
