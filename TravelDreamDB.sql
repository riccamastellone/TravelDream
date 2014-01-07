-- Scherma del database per TravelDream

CREATE SCHEMA IF NOT EXISTS `traveldream` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `traveldream` ;

DROP TABLE IF EXISTS `utente`;

CREATE TABLE `Utente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `cognome` varchar(30) NOT NULL,
  `dataNascita` varchar(20) NOT NULL,
  `indirizzo` varchar(120) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `utenteGruppo`;
CREATE TABLE `UtenteGruppo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `utente` varchar(255) NOT NULL,
  `gruppo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`utente`) REFERENCES `Utente` (`id`)
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
  `id_hotel` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_hotel`) REFERENCES `Hotel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `VoloPacchetto`; 

CREATE TABLE `VoloPacchetto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_volo` int(11) NOT NULL,
  `id_pacchetto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_volo`) REFERENCES `Volo` (`id`),
  FOREIGN KEY (`id_pacchetto`) REFERENCES `Pacchetto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `Prenotazione`; 

CREATE TABLE `Prenotazione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `persone` int(4) NOT NULL,
  `costo_persona` float(6,2) NOT NULL COMMENT 'Costo totale per persona',
  `utente` int(11) NOT NULL,
  `volo_andata` int(11) NOT NULL,
  `volo_ritorno` int(11) NOT NULL,
  `hotel` int(11) NOT NULL,
  `data_creazione` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`volo_andata`) REFERENCES `Volo` (`id`),
  FOREIGN KEY (`volo_ritorno`) REFERENCES `Volo` (`id`),
  FOREIGN KEY (`hotel`) REFERENCES `Hotel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
