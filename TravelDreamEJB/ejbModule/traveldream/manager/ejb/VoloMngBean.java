package traveldream.manager.ejb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Hotel;
import model.Pacchetto;
import model.Volo;
import model.VoloPacchetto;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.VoloMng;

@Stateless
public class VoloMngBean implements VoloMng {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private EJBContext context;

	/* VOLI */

	
	protected static VoloDTO convertVoloToDTO(Volo h) {
		VoloDTO vl = new VoloDTO();
		vl.setId(h.getId());
		vl.setArrivo(h.getArrivo());
		vl.setCittaPartenza(h.getCittaPartenza());
		vl.setCittaArrivo(h.getCittaArrivo());
		vl.setCosto(h.getCosto());
		vl.setDisponibilita(h.getDisponibilita());
		vl.setNomeCompagnia(h.getNomeCompagnia());
		vl.setPartenza(h.getPartenza());
		vl.setEliminato(h.getEliminato());
		return vl;
	}

	@Override
	public void salvaVolo(VoloDTO volo) throws ParseException {
		System.out.println("salvo volo");
		Volo voloNuovo = new Volo(volo);
		em.persist(voloNuovo);
	}

	@Override
	public List<VoloDTO> getVoli() {
		// TODO Auto-generated method stub
		ArrayList<VoloDTO> voliDTO = new ArrayList<VoloDTO>();

		// query dichiarate nell entita UtenteGruppo
		List<Volo> voli = em.createNamedQuery("Volo.findAll", Volo.class)
				.getResultList();
		;

		for (Volo volo : voli) {

			voliDTO.add(this.convertVoloToDTO(volo));
		}
		return voliDTO;
	}

	private Volo findVolo(int id) {
		return em.find(Volo.class, id);
	}

	@Override
	public void aggiornaVolo(VoloDTO volo) throws ParseException {
		
		volo.printaDati();
		Volo voloDaModificare = this.findVolo(volo.getId());
		voloDaModificare.setArrivo(volo.getArrivo());
		voloDaModificare.setCittaArrivo(volo.getCittaArrivo());
		voloDaModificare.setCittaPartenza(volo.getCittaPartenza());
		voloDaModificare.setCosto(volo.getCosto());
		voloDaModificare.setDisponibilita(volo.getDisponibilita());
		voloDaModificare.setNomeCompagnia(volo.getNomeCompagnia());
		voloDaModificare.setPartenza(volo.getPartenza());
		em.merge(voloDaModificare);
		List<VoloPacchetto> voloPacchetto = em.createNamedQuery("VoloPacchetto.getPacchettiByVolo", VoloPacchetto.class).setParameter("volo", voloDaModificare).getResultList();
		for (VoloPacchetto voloPacchetto2 : voloPacchetto) {
			if(voloPacchetto2.getPacchetto().getInizioValidita().after(volo.getPartenza()) || voloPacchetto2.getPacchetto().getFineValidita().before(volo.getPartenza())){
				em.remove(voloPacchetto2);
			}
		}
		
		

	}

	@Override
	public void deleteVolo(VoloDTO volo) {
		// TODO Auto-generated method stub
		Volo voloDaCancellare = this.findVolo(volo.getId());
		voloDaCancellare.setEliminato(1);
		em.merge(voloDaCancellare);
		List<VoloPacchetto> voloPacchetto = em.createNamedQuery("VoloPacchetto.getPacchettiByVolo", VoloPacchetto.class).setParameter("volo", voloDaCancellare).getResultList();
		for (VoloPacchetto voloPacchetto2 : voloPacchetto) {
				em.remove(voloPacchetto2);
			
		}
		
	}

	@Override
	public VoloDTO aggiungiVoloAPacchetto(VoloDTO volo) throws ParseException {
		// TODO Auto-generated method stub
		salvaVolo(volo);
		Volo voloDaRitornareVolo = this.getLastVolo();
		volo.setId(voloDaRitornareVolo.getId());
		return volo;
	}

	private Volo getLastVolo() {

		List<Volo> voli = em.createNamedQuery("Volo.selectMax", Volo.class)
				.getResultList();
		return voli.get(0);
	}


	public ArrayList<String> getCittaArrivo() {
		List<Volo> myList;
		ArrayList<String> volo = new ArrayList<String>();
		myList = em.createNamedQuery("Volo.findAll", Volo.class)
				.getResultList();
		for (Volo h : myList) {
			if (!volo.contains("\"" + h.getCittaArrivo() + "\"")) {
				volo.add("\"" + h.getCittaArrivo() + "\"");
			}
		}
		return volo;
	}

	public ArrayList<String> getCittaPartenza() {
		List<Volo> myList;
		ArrayList<String> volo = new ArrayList<String>();
		myList = em.createNamedQuery("Volo.findAll", Volo.class)
				.getResultList();
		for (Volo h : myList) {
			if (!volo.contains("\"" + h.getCittaPartenza() + "\"")) {
				volo.add("\"" + h.getCittaPartenza() + "\"");
			}
		}
		return volo;
	}

	@Override
	public ArrayList<VoloDTO> getVoliDisponibiliECompatibili(PacchettoDTO pacchetto) {
		// TODO Auto-generated method stub
		ArrayList<VoloDTO> voliDTO = new ArrayList<VoloDTO>();

		// query dichiarate nell entita UtenteGruppo
		List<Volo> voli = em.createNamedQuery("Volo.getVoliDisponibiliECompatibili", Volo.class).setParameter("partenza", pacchetto.getInizioValidita()).setParameter("arrivo", pacchetto.getFineValidita()).getResultList();
	
		for (Volo volo : voli) {

			voliDTO.add(this.convertVoloToDTO(volo));
		}
		return voliDTO;
		
	}

	@Override
	public List<VoloDTO> getVoliByAndataERitorno(String andata, String ritorno, int persone, Date dataPartenza, Date dataRitorno) {
		// TODO Auto-generated method stub
		List<Volo> voli = em.createNamedQuery("Volo.getVoliByAndataERitorno", Volo.class).setParameter("partenza", andata).setParameter("arrivo", ritorno).setParameter("persone", persone).setParameter("dataPartenza", dataPartenza).setParameter("dataRitorno", dataRitorno).getResultList();
		List<VoloDTO> voliAndata = new ArrayList<VoloDTO>();
		
		for (Volo volo: voli) {
			voliAndata.add(this.convertVoloToDTO(volo));
		}
		return voliAndata;
	}

}
