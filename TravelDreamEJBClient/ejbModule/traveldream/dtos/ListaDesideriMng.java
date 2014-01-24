package traveldream.dtos;

import java.util.List;

import traveldream.manager.ListaDesideriDTO;

public interface ListaDesideriMng {

	public void addAListaDesideri(ListaDesideriDTO lista);
	
	public List<ListaDesideriDTO> getListaDesisderiUtente(UtenteDTO utenteDTO);
	
}
