package registrazione.ejb;

import java.util.List;
import java.util.ArrayList;


import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import registrazione.client.UtenteDTO;
import registrazione.client.UtenteMrg;
import model.*;



/**
 * Session Bean implementation class UtenteMgrBean
 */
@Stateless
public class UtenteMgrBean implements UtenteMrg{

	
	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
	/**
	 * creo un utente e gli associo il gruppo CLIENTE o DIPENDENTE a seconda del chiamante
	 */
	@Override
	public void salvaUtente(UtenteDTO utente, String tipo) {
		System.out.println("salvo utente");

		Utente  user = new Utente(utente);	//aggiungo alla tabella Utente una tupla utilizzanto il DTO
		
		UtenteGruppo usergroup = new UtenteGruppo();	//aggiungo una tupla a UtenteGruppo settando manualmente i parametri
		
		//controllo quale gruppo settare
		if (tipo.equals("cliente"))
		{
			usergroup.setGruppo(Gruppo._CLIENTE);
		}
		else
		{
			usergroup.setGruppo(Gruppo._DIPENDENTE);
		}
		usergroup.setUtente(user);
	
		em.persist(user);   //scrivo a database sulla tabella utente
		em.persist(usergroup); //scrivo a database sulla tabella utenteGruppo
		
	}

	
	

	@Override
	public UtenteDTO getUserDTO() {
		UtenteDTO userDTO = convertToDTO(getPrincipalUser());
		return userDTO;
	}
	
	
	/**
	 * serve per non invaidare il paradigma MVC
	 * non scrive direttamente sul modello ma crea dal modello dei data tranfer object (DTO)
	 * 
	 * @param user
	 * @return
	 */
	private UtenteDTO convertToDTO(Utente user) {
		UtenteDTO udto = new UtenteDTO();
		udto.setEmail(user.getEmail());
		udto.setNome(user.getNome());
		udto.setCognome(user.getCognome());
		udto.setDataNascita(user.getDataNascita());
		udto.setIndirizzo(user.getIndirizzo());
		return udto;
	}

	//---Per la ricerca---
	
    public Utente getPrincipalUser()
    {
    	return find(getPrincipalUsername());
    }

	private Utente find(String pusername) {
		return em.find(Utente.class, pusername);
	}

	public String getPrincipalUsername()
	{
		return context.getCallerPrincipal().getName(); // ritorna la chiave specificata nel reame ( EMAIL )
	}

	@Override
	public boolean existEmail(String email) {
		if (em.find(Utente.class,email)!=null){
			return true;
		}
			
		return false;
	}




	/**
	 * 
	 * utile per le funzioni dell'amministratore
	 * restituisce gli utenti appartenenti al grupo selezionato
	 */
	@Override
	public ArrayList<UtenteDTO> getUtentiByGruppi(String gruppo) {
		
		ArrayList<UtenteDTO> utentiDTO = new ArrayList<UtenteDTO>();
		
		//query dichiarate nell entita UtenteGruppo 
		Query queryGetUtentibyGruppo = em.createNamedQuery("UtenteGruppo.findAllByGroup").setParameter("gruppo", gruppo);
		
		List<UtenteGruppo> utenti = queryGetUtentibyGruppo.getResultList();
		
		for (UtenteGruppo utenteGruppo : utenti) {
			Utente utente = utenteGruppo.getUtente();
			utentiDTO.add(convertToDTO(utente));	
		}
		return utentiDTO;
	}




	@Override
	public void aggiornaDipendente(UtenteDTO utente) {
		// TODO Auto-generated method stub
		
	}




}
