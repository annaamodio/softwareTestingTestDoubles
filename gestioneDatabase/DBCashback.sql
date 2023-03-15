CREATE DATABASE CASHBACK;

CREATE TABLE CASHBACK.`Programmi` (
  `idProgramma` int NOT NULL,
  `Inizio` date NOT NULL,
  `Fine` date NOT NULL,
  `AcquistiMinimi` int NOT NULL,
  `TettoMassimo` decimal(10,2) NOT NULL,
  `PercentualeRimborso` decimal(4,3) NOT NULL,
  PRIMARY KEY (`idProgramma`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE CASHBACK.`Cittadini` (
  `Nome` varchar(20) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `CodiceFiscale` varchar(16) NOT NULL,
  `IndirizzoMail` varchar(50) NOT NULL,
  PRIMARY KEY (`CodiceFiscale`),
  UNIQUE KEY `IndirizzoMail_UNIQUE` (`IndirizzoMail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE CASHBACK.`Iscrizioni` (
  `IdCittadino` varchar(15) NOT NULL,
  `Password` varchar(10) DEFAULT NULL,
  `Iban` varchar(27) NOT NULL,
  `RimborsoRicevuto` decimal(10,2) NOT NULL,
  `Programma` int DEFAULT NULL,
  `Cittadino` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`IdCittadino`),
  KEY `Programma_idx` (`Programma`),
  KEY `Cittadino_idx` (`Cittadino`),
  CONSTRAINT `FK_Cittadino` FOREIGN KEY (`Cittadino`) REFERENCES `Cittadini` (`CodiceFiscale`),
  CONSTRAINT `FK_Programma` FOREIGN KEY (`Programma`) REFERENCES `Programmi` (`idProgramma`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE CASHBACK.`CartediCredito` (
  `Numero` varchar(16) NOT NULL,
  `Scadenza` varchar(5) NOT NULL,
  `Iscrizione` int NOT NULL,
  PRIMARY KEY (`Numero`),
  KEY `Iscrizione_idx` (`Iscrizione`),
  CONSTRAINT `FK_Iscrizione` FOREIGN KEY (`Iscrizione`) REFERENCES `Iscrizioni` (`Programma`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE CASHBACK.`Acquisti` (
  `idAcquisto` int NOT NULL,
  `Data` date NOT NULL,
  `Importo` decimal(10,2) NOT NULL,
  `NumeroCarta` varchar(16) DEFAULT NULL,
  `Iscrizione` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`idAcquisto`),
  KEY `NumeroCarta_idx` (`NumeroCarta`),
  KEY `AcquistoIscrizione_idx` (`Iscrizione`),
  CONSTRAINT `FK_AcquistoIscrizione` FOREIGN KEY (`Iscrizione`) REFERENCES `Iscrizioni` (`IdCittadino`),
  CONSTRAINT `FK_NumeroCarta` FOREIGN KEY (`NumeroCarta`) REFERENCES `CartediCredito` (`Numero`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO CASHBACK.Programmi SET idProgramma = 100003, Inizio = '2022-05-11', Fine = '2022-06-11', AcquistiMinimi = 1, TettoMassimo = 12, PercentualeRimborso = 0.2;
INSERT INTO CASHBACK.Cittadini SET Nome = 'Giovanni', Cognome = 'Esposito', CodiceFiscale = 'BRMSVT00L10F839H', IndirizzoMail = 'pippo@gmail.com';
INSERT INTO CASHBACK.Iscrizioni SET IdCittadino = '121314151617181', Password = '1234567890', Iban = "IT47X3608105138230364830372", RimborsoRicevuto = 0, Programma = 100003, Cittadino = 'BRMSVT00L10F839H';
INSERT INTO CASHBACK.CartediCredito SET Numero = '9999888877776666', Scadenza = '03/22', Iscrizione = '100003';
INSERT INTO CASHBACK.Acquisti SET idAcquisto = 1, Data = '2022-06-07', Importo = 393.3, NumeroCarta = '9999888877776666', Iscrizione = '121314151617181';
INSERT INTO CASHBACK.Programmi SET idProgramma = 262022, Inizio='2022-06-10', Fine = '2022-06-20',  AcquistiMinimi = 1, TettoMassimo = 10, PercentualeRimborso = 0.2;

INSERT INTO CASHBACK.Programmi SET idProgramma = 162022, Inizio = '2022-05-01', Fine = '2022-06-01', AcquistiMinimi = 1, TettoMassimo = 10, PercentualeRimborso = 0.2;
INSERT INTO CASHBACK.Cittadini SET Nome = 'Pippo', Cognome = 'Rossi', CodiceFiscale = 'GBNSVT00L23F839H', IndirizzoMail = 'pippopluto@gmail.com';
INSERT INTO CASHBACK.Iscrizioni SET IdCittadino = 'ABCDEFGHI123456', Password = 'qwerty7890', Iban = "IT47X3608105138230364830372", RimborsoRicevuto = 0, Programma = 162022, Cittadino = 'GBNSVT00L23F839H';
INSERT INTO CASHBACK.CartediCredito SET Numero = '7777666655550000', Scadenza = '03/22', Iscrizione = '162022';
INSERT INTO CASHBACK.Acquisti SET idAcquisto = 2, Data = '2022-05-17', Importo = 393.3, NumeroCarta = '7777666655550000', Iscrizione = 'ABCDEFGHI123456';
INSERT INTO CASHBACK.Acquisti SET idAcquisto = 3, Data = '2022-05-01', Importo = 780.3, NumeroCarta = '7777666655550000', Iscrizione = 'ABCDEFGHI123456';
INSERT INTO CASHBACK.Iscrizioni SET idCittadino = 'HHHHHHH12123456', Password = '56789lmnop', Iban = "IT68X3608105143523077648303", RimborsoRicevuto = 0, Programma = 262022, Cittadino = 'BRMSVT00L10F839H';
