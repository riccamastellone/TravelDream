package traveldream.manager;

import java.util.List;

import traveldream.dtos.ListaDesideriDTO;
import traveldream.dtos.UtenteDTO;

public interface ListaDesideriMng {

	public void addAListaDesideri(ListaDesideriDTO lista);
	
	public List<ListaDesideriDTO> getListaDesisderiUtente(UtenteDTO utenteDTO);
	
	public void eliminaDaListaDesideri(ListaDesideriDTO lista);
	
	public void pagaPacchttoInListaDesideri(ListaDesideriDTO lista, String nome);
	
}
