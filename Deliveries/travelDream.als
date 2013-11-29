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
	tipo: one Testo,
	voloAndata: one Trasporto,
	voloRitorno: one Trasporto,
	hotel: one Hotel,
	attivitaSecondaria: one AttivitaSecondaria,
	inizioValidita: one Int,
	fineValidita: one Int,
}

sig Volo extends Trasporto{
	codiceVolo: one Testo,
	
}
sig Hotel extends ComponentePacchetto{
	localita: one Testo,
	nome: one Testo,
	indirizzo: one Testo,
	stelle: one Int,
}

sig AttivitaSecondaria extends ComponentePacchetto{
	nome: one Testo,
	localitaValida: one Testo,
}

sig Prenotazione{
	cliente: one Cliente,
	volo: lone Volo,
	hotel: lone Hotel,
	attivitaSecondaria: lone AttivitaSecondaria,
	pacchetto: lone Pacchetto,
}{ ((#pacchetto >0) implies (#volo=0 and #hotel=0 and #attivitaSecondaria = 0)) and  
	((#volo>0 or #hotel>0 or #attivitaSecondaria > 0) implies (#pacchetto =0))}

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
	codiceUfficio: one Int,
}
	

//----- DEFINIZIONE DEI FATTI -----

//non ci possono essere 2 utenti registrati con la stessa mail
fact NoEmailDuplicate{
	no disj u1, u2: UtenteRegistrato | u1.email = u2.email 
}

//non ci possono essere 2 pacchetti con lo stesso nome
fact NoPacchettiUguali{
	no disj p1, p2: Pacchetto | (p1.nome = p2.nome) and ( ( (p1.voloAndata = p2.voloAndata) and (p1.voloRitorno = p2.voloRitorno) )  or 
																						(p1.attivitaSecondaria = p2.attivitaSecondaria) or
																						(p1.hotel = p2.hotel) )
}

//non ci possono essere 2 voli con lo stesso codice
fact NoVoliDuplicati{
	no disj v1, v2: Volo | v1.codiceVolo = v2.codiceVolo
}

//non ci possono essere 2 hotel uguali nella stessa località (stesso nome, indirizzo, località)
fact NoHotelDuplicati{
	no disj h1, h2: Hotel | (h1.indirizzo = h2.indirizzo) and 
									  (h1.nome = h2.nome) and 
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
 all p : Prenotazione | ( (#p.pacchetto = 1) or 
										( #p.volo = 1) or 
										(#p.hotel = 1) or 
										(#p.attivitaSecondaria = 1) )
}

//tutte le prenotazioni devono avere i componenti disponibili
//il vincolo sul pacchetto non c'è perchè non possono esistere pacchetti non disponibili
fact NoPrenotazioniNonDisponibili{
	all p : Prenotazione | (#p.volo = 1 implies isTrue[p.volo.disponibilita]) and
									(#p.hotel = 1 implies isTrue[p.hotel.disponibilita]) and
									(#p.attivitaSecondaria = 1 implies isTrue[p.attivitaSecondaria.disponibilita])
}

//non posso aggiungere 2 volte lo stesso pacchetto alla lista desideri
fact NoDuplicatiInListaDesideri{
	no disj p1, p2 : Pacchetto, c : Cliente | (p1 in c.listaDesideri) and 
															 (p2 in c.listaDesideri) and 
															 (p1 = p2) 
}

//il volo non puo partire e arrivare nella stessa citta
//NB: questo fatto è solo su volo e non su Trasporto, un ipotetico pullman potrebbe partire ed arrivare nella stessa citta
fact CittaDistinteInVolo{
	all v:  Volo | v.cittaPartenza != v.cittaArrivo
}

//non può esistere un componente pacchetto che non sia almeno in un pacchetto
fact NoComponentePacchettoStandAlone{
	(all a : AttivitaSecondaria |
    some p: Pacchetto | a in p.attivitaSecondaria)
	and
	(all v : Volo |
    some p: Pacchetto | (v in p.voloAndata) or (v in p.voloRitorno))
	and
	(all h : Hotel |
    some p: Pacchetto | h  in p.hotel)
}

//tutti i pacchetti devono avere tutte le componenti disponibili
fact NoPacchettiComponentiNonDisponibili{
	all p: Pacchetto | (isTrue[p.voloAndata.disponibilita] ) and
							  (isTrue[p.voloAndata.disponibilita] ) and
							  (isTrue[p.hotel.disponibilita]) and 
							  (isTrue[p.attivitaSecondaria.disponibilita]) 
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

//il pacchetto deve avere i voli di andata e ritorno che arrivano e partono nella stessa citta rispettivamente
//le date del  volo di andata e del volo di partenza devono essere comprese tra l'inizio e la fine della validita del pacchetto
//il volo di andata e ritorno devono arrivare e partire nella stessa citta dell'hotel del pachetto
//il volo di ritorno e partenza devono arrivare e partire nella stessa citta di partenza
fact PacchettoCoerente{
	all p: Pacchetto | (p.voloRitorno.cittaPartenza in p.voloAndata.cittaArrivo) and
							 (p.voloAndata.dataPartenza >= p.inizioValidita) and (p.voloRitorno.dataPartenza <= p.fineValidita) and
							 (p.hotel.localita = p.voloAndata.cittaArrivo) and ( (p.hotel.localita = p.voloRitorno.cittaPartenza)) and 
							 (p.voloAndata.cittaPartenza = p.voloRitorno.cittaArrivo) and
							 (p.attivitaSecondaria.localitaValida = p.hotel.localita)
						
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
	(one p.cliente) implies ( (one p.volo) or (one p.hotel) or (one p.attivitaSecondaria) or (one p.pacchetto) )
}

check NoPrenotazioneVuota for 10

assert TuttePrenotazioniInStoricoOrdini{
	no p : Prenotazione |
	p not in StoricoOrdini.insiemePrenotazioni
}

//check TuttePrenotazioniInStoricoOrdini for 10

//verifico che non ci siano pacchetti con componenti non disponibili
assert NoPacchettiNonDisponibili{
	no p: Pacchetto | ((not isTrue[p.voloAndata.disponibilita] ) and
							  (not isTrue[p.voloRitorno.disponibilita] )) or
							  (not isTrue[p.hotel.disponibilita]) or 
							  (not isTrue[p.attivitaSecondaria.disponibilita]) 
}

check NoPacchettiNonDisponibili for 10

//verifico che non ci siano prenotazioni su componenti non disponibili
assert NoPrenotazioneDiComponentiNonDisponibili{
	no p : Prenotazione | (#p.volo = 1 and not isTrue[p.volo.disponibilita]) or
									(#p.hotel = 1 and not isTrue[p.hotel.disponibilita]) or 
									(#p.attivitaSecondaria = 1 and not isTrue[p.attivitaSecondaria.disponibilita])

}

check NoPrenotazioneDiComponentiNonDisponibili for 10

//verifico che tutti i pacchetti siano consistenti
assert TuttiPacchettiConsistenti{
	no p: Pacchetto | (p.voloRitorno.cittaPartenza != p.voloAndata.cittaArrivo) or
							 (p.voloAndata.dataPartenza < p.inizioValidita) or (p.voloRitorno.dataPartenza > p.fineValidita) or
							 (p.hotel.localita != p.voloAndata.cittaArrivo) or ( (p.hotel.localita != p.voloRitorno.cittaPartenza)) or 
							 (p.voloAndata.cittaPartenza != p.voloRitorno.cittaArrivo) or
							 (p.attivitaSecondaria.localitaValida != p.hotel.localita)
}

check TuttiPacchettiConsistenti for 10

//----- DEFINIZIONE DEI PREDICATI -----

pred show() {
#Prenotazione = 2
#Pacchetto = 2}

run show for 4

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








	


	

	
