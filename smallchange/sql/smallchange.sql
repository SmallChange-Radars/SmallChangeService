DROP TABLE preferences;
DROP TABLE portfolio;
DROP TABLE trade;
DROP TABLE price;
DROP TABLE orderInstrument;
DROP TABLE instrument;
DROP TABLE clientIdentification;
DROP TABLE client;

CREATE TABLE client (    
    clientId VARCHAR2(20) PRIMARY KEY,
    email VARCHAR2(100),
    dob VARCHAR2(8),
    country VARCHAR2(2),
    postalCode VARCHAR2(6),
    password VARCHAR2(20),
    wallet NUMBER(20,3),
    walletCurrency VARCHAR2(8)
);

CREATE TABLE clientIdentification (
    type VARCHAR2(20),
    value VARCHAR2(50),
    clientId VARCHAR2(20),
    FOREIGN KEY (clientId) REFERENCES client (clientId)
);

CREATE TABLE instrument (
    instrumentId VARCHAR2(20) PRIMARY KEY,
    description VARCHAR2(255),
    externalIdType VARCHAR2(20),
    externalId VARCHAR2(50),
    categoryId VARCHAR2(50),
    minQuantity NUMBER(10,0),
    maxQuantity NUMBER(10,0)
);

CREATE TABLE orderInstrument (
    orderId VARCHAR2(50) PRIMARY KEY,
    quantity NUMBER(10,0),
    targetPrice NUMBER(10,2),
    direction VARCHAR2(1),
    clientId VARCHAR2(20),
    instrumentId VARCHAR2(20),
    FOREIGN KEY (instrumentId) REFERENCES instrument(instrumentId),
    FOREIGN KEY (clientId) REFERENCES client (clientId)
);

CREATE TABLE price (
    instrumentId VARCHAR2(20),
    bidPrice NUMBER(10,2),
    askPrice NUMBER(10,2),
    timestamp VARCHAR2(50),
    FOREIGN KEY (instrumentId) REFERENCES instrument(instrumentId)
);

CREATE TABLE trade (
    tradeId VARCHAR2(50) PRIMARY KEY,
    orderId VARCHAR2(50),
    quantity NUMBER(10,0),
    direction VARCHAR2(1),
    clientId VARCHAR2(20),
    instrumentId VARCHAR2(20),
    executionPrice NUMBER(10,2),
    cashValue NUMBER(10,2),
    FOREIGN KEY (instrumentId) REFERENCES instrument(instrumentId),
    FOREIGN KEY (clientId) REFERENCES client (clientId),
    FOREIGN KEY (orderId) REFERENCES orderInstrument(orderId)
);

CREATE TABLE portfolio (
    clientId VARCHAR2(20),    
    instrumentId VARCHAR2(20),
    quantity NUMBER(10,0),
    value NUMBER(10,2),
    FOREIGN KEY (clientId) REFERENCES client (clientId),
    FOREIGN KEY (instrumentId) REFERENCES instrument(instrumentId)
);

CREATE TABLE preferences (
    clientId VARCHAR2(20),
    investmentPurpose VARCHAR(255),
    riskTolerance VARCHAR2(5),
    incomeCategory VARCHAR2(50),
    lengthOfInvestment VARCHAR2(20),
    FOREIGN KEY (clientId) REFERENCES client (clientId)
);

INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1234', 'aadrs@gmail.com','19900101', 'US','123456','pass123',12345.45,'USD');
INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1235', 'sara@gmail.com','20002512', 'US','123477','pass1234',748295.45,'USD');
INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1236', 'priya@gmail.com','19990303', 'US','123333','pass1234',748295.45,'USD');
INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1237', 'stephen@gmail.com','19801112', 'US','123558','pass1234',748295.45,'USD');
INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1238', 'prabhu@gmail.com','20011230', 'US','123777','pass1234',748295.45,'USD');

INSERT INTO instrument(instrumentId, externalIdType, externalId, categoryId, description, maxQuantity, minQuantity)
     VALUES('Q123', 'CUSIP', '02079K107', 'STOCK', 'Alphabet', 1000, 1);
INSERT INTO instrument(instrumentId, externalIdType, externalId, categoryId, description, maxQuantity, minQuantity)
     VALUES('Q456', 'CUSIP', '88160R101', 'STOCK', 'Tesla, Inc. Common Stock', 1000, 1);
INSERT INTO instrument(instrumentId, externalIdType, externalId, categoryId, description, maxQuantity, minQuantity)
     VALUES('C100', 'CUSIP', '48123Y5A0', 'CD', 'JPMorgan Chase Bank, National Association 01/19', 1000, 100);
INSERT INTO instrument(instrumentId, externalIdType, externalId, categoryId, description, maxQuantity, minQuantity)
     VALUES('T67890', 'CUSIP', '9128285M8', 'GOVT', 'USA, Note 3.125 15nov2028 10Y', 10000, 100);
INSERT INTO instrument(instrumentId, externalIdType, externalId, categoryId, description, maxQuantity, minQuantity)
     VALUES('T67894', 'CUSIP', '9128285Z9', 'GOVT', 'USA, Note 2.5 31jan2024 5Y', 10000, 100);
     

INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1234','Q456',50,450.89);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1236','T67890',70,670.89);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1236','T67894',91,1350.71);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1238','C100',101,4563.67);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1238','Q123',370,10037.32);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1238','T67894',91,10657.32);

COMMIT;