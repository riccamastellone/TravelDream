package registrazione.ejb;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.codec.digest.DigestUtils;

import traveldream.dtos.UtenteDTO;
import traveldream.manager.UtenteMrg;
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
	public void aggiornaUtente(UtenteDTO utente, String vecchiaEmail) {
		// TODO Auto-generated method stub
		System.out.println("aggiorno utente");
		
		Utente user = this.findUtente(vecchiaEmail);
		

		//se cambia l'email, essendo chiave primaria creo una nuova tupla ed elimino quella vecchia
		if (!user.getEmail().equals(utente.getEmail())) {
			
			//ricavo il gruppo di appartenenza per poi reinserirlo uguale nella nuova tupla
			UtenteGruppo utenteGruppo = this.getUtenteGruppoByEmail(user);
			String gruppo = utenteGruppo.getGruppo();
			
			//se la password e rimasta uguale devo rimetterla gia hashata
			if (user.getPassword().equals(utente.getPassword())) {
				this.salvaUtente(utente, gruppo.toLowerCase());
				Utente userNuovo = this.findUtente(utente.getEmail());
				userNuovo.setPassword(utente.getPassword());
				em.merge(userNuovo);	
			}
			
			//se no salvo semplicemente il nuovo utente
			else {
				this.salvaUtente(utente, gruppo.toLowerCase());
			}
			
			//rimuovo le vecchie tuple
			em.remove(utenteGruppo);
			em.remove(user);
		}
		
		else {
			
			//se la password e cambiatadevo fare l hash sul nuovo valore 
			if (!user.getPassword().equals(utente.getPassword())) {
				user.setPassword(DigestUtils.sha256Hex(utente.getPassword()));
			}
			
			user.setCognome(utente.getCognome());
			user.setNome(utente.getNome());
			user.setIndirizzo(utente.getIndirizzo());
			em.merge(user);			
		}
		
				
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
	public static UtenteDTO convertToDTO(Utente user) {
		UtenteDTO udto = new UtenteDTO();
		udto.setEmail(user.getEmail());
		udto.setNome(user.getNome());
		udto.setCognome(user.getCognome());
		udto.setIndirizzo(user.getIndirizzo());
		udto.setPassword(user.getPassword());
		return udto;
	}

	//---Per la ricerca---
	
    public Utente getPrincipalUser()
    {
    	return findUtente(getPrincipalUsername());
    }
    
	private Utente findUtente(String email) {
		return em.find(Utente.class, email);
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


	//DEBUG
	public String getGruppo(UtenteDTO utente){
		Utente user = this.findUtente(utente.getEmail());
		
		UtenteGruppo gruppo = this.getUtenteGruppoByEmail(user);
		return gruppo.getGruppo();
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
	
	public UtenteGruppo getUtenteGruppoByEmail(Utente utente) {
		
		//query dichiarate nell entita UtenteGruppo 
		Query queryGetUtenteGruppoByUtente = em.createNamedQuery("UtenteGruppo.findByIdUtente").setParameter("utente", utente);
		List<UtenteGruppo> utenti = queryGetUtenteGruppoByUtente.getResultList();
		return utenti.get(0);
	}

	@Override
	public UtenteDTO getUtenteByMail(String email) {
		// TODO Auto-generated method stub
		Utente utente = em.find(Utente.class, email);
		
		return convertToDTO(utente);
	}





}
