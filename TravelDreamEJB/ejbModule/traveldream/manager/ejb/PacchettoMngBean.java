package traveldream.manager.ejb;


import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.emptyType;

import model.Pacchetto;
import traveldream.dtos.PacchettoDTO;
import traveldream.manager.PacchettoMng;

/**
 * Session Bean implementation class PacchettoMngBean
 */
@Stateless
public class PacchettoMngBean implements PacchettoMng {

	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	/**
	 * Default constructor.
	 */
	public PacchettoMngBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvaInfoGenerali(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub		
		Pacchetto pacchettoDaSalvare = new Pacchetto(pacchetto);
		em.persist(pacchettoDaSalvare);
		System.out.println("salvo info generali");
	}
    
 

}
