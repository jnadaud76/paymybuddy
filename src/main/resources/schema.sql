CREATE DATABASE IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `paymybuddy`;

CREATE TABLE IF NOT EXISTS `PERSON`(
`ID` INT NOT NULL AUTO_INCREMENT,
`FIRSTNAME` VARCHAR(100) NOT NULL,
`LASTNAME` VARCHAR(100) NOT NULL,
`EMAIL` VARCHAR(100) NOT NULL UNIQUE,
`PASSWORD` VARCHAR(255) NOT NULL,
`IBAN` VARCHAR(34) NOT NULL,
`AMOUNT_AVAILABLE` DECIMAL(8,2) NOT NULL DEFAULT 0,
PRIMARY KEY(`ID`)
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `TRANSACTION`(
`ID` INT NOT NULL AUTO_INCREMENT,
`DATE` TIMESTAMP NOT NULL,
`RECIPIENT` INTEGER NOT NULL,
`SENDER` INTEGER NOT NULL,
`AMOUNT` SMALLINT NOT NULL,
`DESCRIPTION` VARCHAR(100),
`FEE_AMOUNT` DECIMAL(5,2) NOT NULL,
PRIMARY KEY(`ID`)
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `PERSON_CONNECTION`(
`PERSON_ID` INT NOT NULL,
`CONNECTION_ID` INT NOT NULL,
PRIMARY KEY(`PERSON_ID`, `CONNECTION_ID`)
) ENGINE=InnoDB;


ALTER TABLE `TRANSACTION`
ADD FOREIGN KEY(`RECIPIENT`) REFERENCES `PERSON`(`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `TRANSACTION`
ADD FOREIGN KEY(`SENDER`) REFERENCES `PERSON`(`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `PERSON_CONNECTION`
ADD FOREIGN KEY(`PERSON_ID`) REFERENCES `PERSON`(`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `PERSON_CONNECTION`
ADD FOREIGN KEY(`CONNECTION_ID`) REFERENCES `PERSON`(`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;