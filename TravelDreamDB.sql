-- Scherma del database per TravelDream

CREATE SCHEMA IF NOT EXISTS `traveldream` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `traveldream` ;

DROP TABLE IF EXISTS `utente`;

CREATE TABLE `Utente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `cognome` varchar(30) NOT NULL,
  `indirizzo` varchar(120) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `utenteGruppo`;
CREATE TABLE `UtenteGruppo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utente` varchar(255) NOT NULL,
  `gruppo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`utente`) REFERENCES `Utente` (`email`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Hotel`;
CREATE TABLE `Hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `disponibilita` int(11) DEFAULT 0 NULL,
  `stelle` int(11) DEFAULT NULL,
  `costo_giornaliero` float(6,2) DEFAULT NULL,
  `immagine` varchar(255) DEFAULT NULL,
  `luogo` varchar(255) DEFAULT NULL,
  `descrizione` text DEFAULT NULL,
  `eliminato` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `Volo`;

CREATE TABLE `Volo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disponibilita` int(11) NOT NULL DEFAULT '0',
  `costo` float(6,2) NOT NULL,
  `citta_partenza` varchar(255) NOT NULL,
  `citta_arrivo` varchar(255) NOT NULL,
  `partenza` datetime NOT NULL,
  `arrivo` datetime NOT NULL,
  `nome_compagnia` varchar(255) NOT NULL,
  `eliminato` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `Pacchetto`;

CREATE TABLE `Pacchetto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `immagine` varchar(255) DEFAULT NULL,
  `localita` varchar(255) NOT NULL,
  `descrizione` text NOT NULL,
  `inizio_validita` date NOT NULL,
  `fine_validita` date NOT NULL,
  `hotel` int(11) NOT NULL,
  `eliminato` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`hotel`) REFERENCES `Hotel` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `VoloPacchetto`; 

CREATE TABLE `VoloPacchetto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `volo` int(11) NOT NULL,
  `pacchetto` int(11) NOT NULL,
  `tipo` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`volo`) REFERENCES `Volo` (`id`)
  ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`pacchetto`) REFERENCES `Pacchetto` (`id`)
  ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `Prenotazione`; 

CREATE TABLE `Prenotazione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `persone` int(4) NOT NULL,
  `costo_persona` float(6,2) NOT NULL COMMENT 'Costo totale per persona',
  `utente` varchar(255) NOT NULL,
  `volo_andata` int(11) NOT NULL,
  `volo_ritorno` int(11) NOT NULL,
  `hotel` int(11) NOT NULL,
  `data_creazione` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `eliminato` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`utente`) REFERENCES `Utente` (`email`),
  FOREIGN KEY (`volo_andata`) REFERENCES `Volo` (`id`),
  FOREIGN KEY (`volo_ritorno`) REFERENCES `Volo` (`id`),
  FOREIGN KEY (`hotel`) REFERENCES `Hotel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


DROP TABLE IF EXISTS `AttivitaSecondaria`; 
CREATE TABLE `AttivitaSecondaria` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `disponibilita` int(11) NOT NULL DEFAULT '0',
  `costo` float(6,2) NOT NULL,
  `localita` varchar(255) NOT NULL,
  `descrizione` TEXT DEFAULT NULL,
  `eliminato` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `AttivitaSecondariaPacchetto`; 
CREATE TABLE IF NOT EXISTS `AttivitaSecondariaPacchetto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pacchetto` INT NOT NULL,
  `attivita_secondaria` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`pacchetto`) REFERENCES `Pacchetto` (`id`),
  FOREIGN KEY (`attivita_secondaria`) REFERENCES `AttivitaSecondaria` (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `AttivitaSecondariaPrenotazione`; 
CREATE TABLE IF NOT EXISTS `AttivitaSecondariaPrenotazione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prenotazione` INT NOT NULL,
  `attivita_secondaria` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`prenotazione`) REFERENCES `Prenotazione` (`id`),
  FOREIGN KEY (`attivita_secondaria`) REFERENCES `AttivitaSecondaria` (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ListaDesideri`; 
CREATE TABLE IF NOT EXISTS `ListaDesideri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utente` varchar(255) NOT NULL,
  `pacchetto` int(11) NOT NULL,
  `pagato_da` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`utente`) REFERENCES `Utente` (`email`)
  ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`pacchetto`) REFERENCES `Pacchetto` (`id`)
  ON UPDATE CASCADE ON DELETE CASCADE
  )ENGINE = InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `PacchettoCondiviso`; 
CREATE TABLE IF NOT EXISTS `PacchettoCondiviso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utente` varchar(255) NOT NULL,
  `pacchetto` int(11) NOT NULL,
  `email_amico` varchar(255) NOT NULL,
  `chiave` varchar(255) NOT NULL,
  `stato` varchar(65) DEFAULT 'non accettato',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`utente`) REFERENCES `Utente` (`email`)
  ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (`pacchetto`) REFERENCES `Pacchetto` (`id`)
  ON UPDATE CASCADE ON DELETE CASCADE
  )ENGINE = InnoDB DEFAULT CHARSET=utf8;
  
LOCK TABLES `Utente` WRITE;
/*!40000 ALTER TABLE `Utente` DISABLE KEYS */;
INSERT INTO `Utente` VALUES (1,'admin','admin','copernico','admin@gmail.com','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');
/*!40000 ALTER TABLE `Utente` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `UtenteGruppo` WRITE;
/*!40000 ALTER TABLE `UtenteGruppo` DISABLE KEYS */;
INSERT INTO `UtenteGruppo` VALUES (1,'admin@gmail.com','AMMINISTRATORE');
/*!40000 ALTER TABLE `UtenteGruppo` ENABLE KEYS */;
UNLOCK TABLES;
