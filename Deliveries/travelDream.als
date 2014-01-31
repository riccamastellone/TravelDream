open util/boolean
open util/integer

//----- TIPI DI DATO ------

sig Testo{}


//----- ENTITA' RELATIVE AL PACCHETTO ------

//attributi comuni a tutti gli elementi del pacchetto
abstract sig ComponentePacchetto{
	id: one Int,
	disponibilita: one Bool,
	descrizione: one Testo,
	prezzo: one Int,
}

//attributi comuni a tutte le classi di trasporto (utile per le future implementazioni)
abstract sig Trasporto extends ComponentePacchetto {
	cittaPartenza: one Testo,
	cittaArrivo: one Testo,
	dataPartenza: one Int,
	dataArrivo:one Int,
	oraPartenza: one Int,
	oraArrivo: one Int,
}

sig Pacchetto{
    id: one Int,
	descrizione: one Testo,
	prezzo: one Int,
	nome: one Testo,
	voloAndata: some Trasporto,
	voloRitorno: some Trasporto,
	hotel: one Hotel,
	attivitaSecondaria: some AttivitaSecondaria,
	inizioValidita: one Int,
	fineValidita: one Int,
	localita: one Testo,
}

sig Volo extends Trasporto{	
}

sig Hotel extends ComponentePacchetto{
	localita: one Testo,
	nome: one Testo,
	stelle: one Int,
}

sig AttivitaSecondaria extends ComponentePacchetto{
	nome: one Testo,
	localitaValida: one Testo,
}

sig Prenotazione{
	cliente: one Cliente,
	voloAndata: one Volo,
	voloRitorno: one Volo,
	hotel: one Hotel,
	attivitaSecondaria: set AttivitaSecondaria,
}

//il catalogo deve essere unico
one sig Catalogo{
	insiemePacchetti: set Pacchetto
}

//lo storico ordini deve essere unico
one sig StoricoOrdini{
	insiemePrenotazioni: set Prenotazione,
}

//----- ENTITA' RELATIVE AGLI UTENTI REGISTRATI -----

//attributi comuni a clienti e dipendenti
abstract sig UtenteRegistrato{
	id: one Int,
	email: one Testo,
	password: one Testo,
	nome: one Testo,
	cognome: one Testo,
	indirizzo: one Testo,
}

//l' amministratore è unico
one sig Amministratore extends UtenteRegistrato{}

sig Cliente extends UtenteRegistrato{
	listaDesideri: set Pacchetto,
}

sig Dipendente extends UtenteRegistrato{
}
	

//----- DEFINIZIONE DEI FATTI -----

//non ci possono essere 2 utenti registrati con la stessa mail
fact NoEmailDuplicate{
	no disj u1, u2: UtenteRegistrato | u1.email = u2.email 
}

//non ci possono essere 2 voli con lo stesso codice
fact NoVoliDuplicati{
	no disj v1, v2: Volo | v1.id = v2.id
}

//non ci possono essere 2 hotel uguali nella stessa località (stesso nome, indirizzo, località)
fact NoHotelDuplicati{
	no disj h1, h2: Hotel |(h1.nome = h2.nome) and 
									  (h1.localita = h2.localita) 
}

//non ci possono essere 2 attività con lo stesso nome con la stessa località di svolgimento
fact NoAttivitaSecondarieDuplicate{
	no disj a1, a2: AttivitaSecondaria | (a1.nome = a2.nome) and 
														(a1.localitaValida = a2.localitaValida)
}

//tutti pacchetti devono appartenere al catalogo
fact PacchettiInCatalogo{
	all p: Pacchetto | p in Catalogo.insiemePacchetti
}

//tutte le prenotazioni devono appartenere allo StoricoOrdini
fact PrenotazioniInStoricoOrdini{
	all p: Prenotazione | p in StoricoOrdini.insiemePrenotazioni
}

//tutte le prenotazioni devono avere almeno un componente prenotato
fact NoPrenotazioniVuote{
 all p : Prenotazione | ( (#p.voloAndata = 1) and 
									( #p.voloRitorno = 1) and 
									(#p.hotel = 1) and
									(#p.attivitaSecondaria >= 1) )
}

//tutte le prenotazioni devono avere i componenti disponibili
//il vincolo sul pacchetto non c'è perchè non possono esistere pacchetti non disponibili
fact NoPrenotazioniNonDisponibili{
	all p : Prenotazione | (#p.voloAndata = 1 implies isTrue[p.voloAndata.disponibilita]) and
									(#p.voloRitorno = 1 implies isTrue[p.voloRitorno.disponibilita]) and
									(#p.hotel = 1 implies isTrue[p.hotel.disponibilita]) and
									(all a: AttivitaSecondaria |a in p.attivitaSecondaria  implies isTrue[a.disponibilita])
}

//non posso aggiungere 2 volte lo stesso pacchetto alla lista desideri
fact NoDuplicatiInListaDesideri{
	no disj p1, p2 : Pacchetto, c : Cliente | (p1 in c.listaDesideri) and 
															 (p2 in c.listaDesideri) and 
															 (p1 = p2) 
}

//tutti i pacchetti devono avere tutte le componenti disponibili
fact NoPacchettiComponentiNonDisponibili{
	all p: Pacchetto | (some va: Volo | va in p.voloAndata and isTrue[va.disponibilita] ) and
							  (some vr: Volo | vr in p.voloRitorno and isTrue[vr.disponibilita] ) and
							  (isTrue[p.hotel.disponibilita]) and 
							  (some a:AttivitaSecondaria | a in p.attivitaSecondaria and isTrue[a.disponibilita]) 
}

//fatto che modellizza tutte le date in gioco nel nostro modello
fact DateValidePacchetto{
	//fine validita del pacchetto deve essere > inizio validita (entrambe positive)
	(all p: Pacchetto | (p.fineValidita > p.inizioValidita) and (p.fineValidita >0 ) and (p.inizioValidita >0) and 
								(p.voloAndata.dataArrivo < p.voloRitorno.dataPartenza))
	and
	//dataArrivo di un volo deve essere >= di quella di partenza, nel caso siano uguali l'ora di arrivo deve essere > di quella di partenza
	(all v: Volo | (v.dataArrivo >= v.dataPartenza) and (v.dataArrivo > 0) and (v.dataPartenza > 0) and
						(v.oraArrivo > 0) and (v.oraPartenza > 0)
						and (v.dataArrivo = v.dataPartenza implies v.oraArrivo > v.oraPartenza))
}


//le date del  volo di andata e del volo di partenza devono essere comprese tra l'inizio e la fine della validita del pacchetto
fact PacchettoCoerente{
	all p: Pacchetto | (all v:Volo | v in p.voloAndata and v.dataPartenza >= p.inizioValidita) and 
							 (all v1:Volo | v1 in p.voloRitorno and v1.dataPartenza <= p.fineValidita) and
							 (p.hotel.localita = p.localita) and
							 (all a:AttivitaSecondaria | a in p.attivitaSecondaria and a.localitaValida = p.localita)
}


//----- DEFINIZIONE ASSERT -----


//se aggiungo un pacchetto gia esistente il catalogo deve restarmi uguale 
//( inserimento duplicato in un insieme da insieme stesso)
assert NoAggiuntaPacchettoEsistente{
	all p: Pacchetto, c, c1: Catalogo |
	(p in c.insiemePacchetti and aggiungiPacchetto[c,c1,p])
	implies (c.insiemePacchetti = c1.insiemePacchetti)
}

check NoAggiuntaPacchettoEsistente for 10

//non può esistere una prenotazione contenete solo il cliente
assert NoPrenotazioneVuota{
	all p: Prenotazione |
	(one p.cliente) implies ( (one p.voloAndata) and (one p.voloRitorno) and (one p.hotel)  )
}

check NoPrenotazioneVuota for 10

assert TuttePrenotazioniInStoricoOrdini{
	no p : Prenotazione |
	p not in StoricoOrdini.insiemePrenotazioni
}

check TuttePrenotazioniInStoricoOrdini for 10

//verifico che non ci siano pacchetti con componenti non disponibili
assert NoPacchettiNonDisponibili{
	no p: Pacchetto | (all va:Volo | va in p.voloAndata and not isTrue[va.disponibilita] ) or
							  (all vr:Volo | vr in p.voloRitorno and not isTrue[vr.disponibilita] ) or
							  (not isTrue[p.hotel.disponibilita]) or 
							  (all a:AttivitaSecondaria | a in p.attivitaSecondaria and not isTrue[a.disponibilita]) 
}

check NoPacchettiNonDisponibili for 10

//verifico che non ci siano prenotazioni su componenti non disponibili
assert NoPrenotazioneDiComponentiNonDisponibili{
	no p : Prenotazione | (#p.voloAndata = 1 and not isTrue[p.voloAndata.disponibilita]) or
									(#p.voloRitorno = 1 and not isTrue[p.voloRitorno.disponibilita]) or
									(#p.hotel = 1 and not isTrue[p.hotel.disponibilita]) or 
									(all a:AttivitaSecondaria | a in p.attivitaSecondaria and not isTrue[a.disponibilita])

}

check NoPrenotazioneDiComponentiNonDisponibili for 10

//verifico che tutti i pacchetti siano consistenti
assert TuttiPacchettiConsistenti{
	no p: Pacchetto | (some va:Volo | va in p.voloAndata and va.dataPartenza < p.inizioValidita) or
							 (some vr:Volo | vr in p.voloRitorno and vr.dataPartenza > p.fineValidita) or
							 (p.hotel.localita != p.localita) or 
							 (some a: AttivitaSecondaria | a in p.attivitaSecondaria and a.localitaValida != p.localita)
}

check TuttiPacchettiConsistenti for 10

//----- DEFINIZIONE DEI PREDICATI -----

pred show() {
#Prenotazione = 2
#Pacchetto = 2}
run show for 10

pred aggiungiPacchetto( c, c1 : Catalogo, p: Pacchetto ){
	c1.insiemePacchetti = c.insiemePacchetti + p 
}

run aggiungiPacchetto for 10

pred aggiungiAListaDesideri(c, c1: Cliente, p : Pacchetto){
	c1.listaDesideri = c.listaDesideri + p
}

run aggiungiAListaDesideri for 10

pred eliminaDaListaDesideri(c,c1: Cliente, p: Pacchetto){
	c1.listaDesideri = c.listaDesideri - p
}

run eliminaDaListaDesideri for 10








	


	

	
