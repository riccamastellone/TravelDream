

DROP TABLE IF EXISTS `utente`;

CREATE TABLE `utente` (
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
CREATE TABLE `utenteGruppo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idutente` varchar(255) NOT NULL,
  `gruppo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome_UNIQUE` (`idutente`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;