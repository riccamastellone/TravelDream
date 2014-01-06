

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
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `utenteGruppo`;
CREATE TABLE `UtenteGruppo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `gruppo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome_UNIQUE` (`email`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Hotel`;
CREATE TABLE `Hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `disponibilita` int(11) DEFAULT 0 NULL,
  `stelle` int(11) DEFAULT NULL,
  `costo_giornaliero` int(11) DEFAULT NULL,
  `immagine` varchar(255) DEFAULT NULL,
  `luogo` varchar(255) DEFAULT NULL,
  `descrizione` text DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `UtenteGruppo`
  ADD CONSTRAINT `FK_UtenteGruppo_idutente` FOREIGN KEY (`email`) REFERENCES `Utente` (`email`);
