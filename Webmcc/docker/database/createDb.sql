# noinspection SqlNoDataSourceInspectionForFile
CREATE DATABASE IF NOT EXISTS Webmcc;
CREATE TABLE IF NOT EXISTS Webmcc.user (
  userID VARCHAR(40)  NOT NULL UNIQUE ,
  name   VARCHAR(100) NOT NULL,
  email VARCHAR(200)  NOT NULL,
  PRIMARY KEY (userID)
);

CREATE TABLE IF NOT EXISTS Webmcc.command (
  commandID       INT         NOT NULL UNIQUE AUTO_INCREMENT,
  result          TEXT        NOT NULL,
  launchedCommand TEXT        NOT NULL,
  userID          VARCHAR(40) NOT NULL,
  commandName VARCHAR(100)    NOT NULL ,
  standardOutput BLOB    NOT NULL ,
  standardError BLOB    NOT NULL ,
  launchedDate DATETIME       NOT NULL ,
  PRIMARY KEY (commandID),
  FOREIGN KEY (userID) REFERENCES Webmcc.user (userID)
);

CREATE USER 'username'@'%' IDENTIFIED BY 'password';
GRANT SELECT,INSERT,UPDATE,DELETE on Webmcc.* TO 'username'@'%';