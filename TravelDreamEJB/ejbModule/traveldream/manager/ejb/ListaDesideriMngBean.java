package traveldream.manager.ejb;


import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.ListaDesideri;
import model.Pacchetto;
import model.Utente;
import registrazione.ejb.UtenteMgrBean;
import traveldream.dtos.ListaDesideriMng;
import traveldream.manager.ListaDesideriDTO;

/**
 * Session Bean implementation class ListaDesideriMngBean
 */
@Stateless
public class ListaDesideriMngBean implements ListaDesideriMng{

	
	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
    /**
     * Default constructor. 
     */
    public ListaDesideriMngBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addAListaDesideri(ListaDesideriDTO lista) {
		// TODO Auto-generated method stub
		Pacchetto pacchetto = em.find(Pacchetto.class, lista.getPacchetto().getId());	
		Utente utente = em.find(Utente.class, lista.getUtente().getEmail());
		
		//controllo che un utente non aggiunga 2 volte lo stesso pacchetto
		if (!this.getListaByPacchettoUtente(pacchetto,utente).isEmpty()){
			return;
		}
		else {
			ListaDesideri listaDesideri = new ListaDesideri(pacchetto, utente);
			
			em.persist(listaDesideri);
		}
		
		
	}
	
	private List<ListaDesideri> getListaByPacchettoUtente(Pacchetto pacchetto, Utente utente){
		
		List<ListaDesideri> lista = em.createNamedQuery("ListaDesideri.getListaDesideriByPacchettoUtente", ListaDesideri.class).setParameter("pacchetto", pacchetto).setParameter("utente", utente).getResultList();
		return lista;
		
	}
    
    
    
    

}
