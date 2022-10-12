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
    postalCode VARCHAR2(6)
);

CREATE TABLE clientIdentification (
    type VARCHAR2(20),
    value VARCHAR2(50),
    clientId VARCHAR2(20),
    FOREIGN KEY (clientId) REFERENCES client(clientId)
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
    FOREIGN KEY (clientId) REFERENCES client(clientId)
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
    FOREIGN KEY (clientId) REFERENCES client(clientId),
    FOREIGN KEY (orderId) REFERENCES orderInstrument(orderId)
);

CREATE TABLE portfolio (
    clientId VARCHAR2(20),    
    instrumentId VARCHAR2(20),
    quantity NUMBER(10,0),
    value NUMBER(10,2),
    FOREIGN KEY (clientId) REFERENCES client(clientId),
    FOREIGN KEY (instrumentId) REFERENCES instrument(instrumentId)
);

CREATE TABLE preferences (
    clientId VARCHAR2(20),
    investmentPurpose VARCHAR(255),
    riskTolerance VARCHAR2(5),
    incomeCategory VARCHAR2(50),
    lengthOfInvestment VARCHAR2(20),
    FOREIGN KEY (clientId) REFERENCES client(clientId)
);