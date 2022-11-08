DROP TABLE preferences;
DROP TABLE portfolio;
DROP TABLE price;
DROP TABLE trade;
DROP TABLE orderInstrument cascade constraints;
DROP TABLE clientIdentification;
DROP TABLE fmtsToken;
DROP TABLE client cascade constraints;

CREATE TABLE client (    
    clientId VARCHAR2(40) PRIMARY KEY,
    email VARCHAR2(100),
    dob VARCHAR2(8),
    country VARCHAR2(2),
    postalCode VARCHAR2(6),
    password VARCHAR2(200),
    wallet NUMBER(20,3),
    walletCurrency VARCHAR2(8),
    role VARCHAR2(40)
);

CREATE TABLE clientIdentification (
    type VARCHAR2(20),
    value VARCHAR2(200),
    clientId VARCHAR2(20),
    FOREIGN KEY (clientId) REFERENCES client (clientId)
);

CREATE TABLE fmtsToken (
    token VARCHAR2(50),
    timestamp VARCHAR2(50),
    clientId VARCHAR2(20),
    FOREIGN KEY (clientId) REFERENCES client (clientId)
);


CREATE TABLE orderInstrument (
    orderId varchar2(50) PRIMARY KEY,
    quantity NUMBER(10,0),
    targetPrice NUMBER(10,2),
    direction VARCHAR2(1),
    clientId VARCHAR2(20),
    instrumentId VARCHAR2(20),
    FOREIGN KEY (clientId) REFERENCES client (clientId)
);

CREATE TABLE price (
    instrumentId VARCHAR2(20),
    bidPrice NUMBER(10,2),
    askPrice NUMBER(10,2),
    timestamp VARCHAR2(50)
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
    timestamp VARCHAR2(50),
    FOREIGN KEY (clientId) REFERENCES client (clientId),
    FOREIGN KEY (orderId) REFERENCES orderInstrument(orderId)
);

CREATE TABLE portfolio (
    clientId VARCHAR2(20),    
    instrumentId VARCHAR2(20),
    quantity NUMBER(10,0),
    value NUMBER(10,2),
    FOREIGN KEY (clientId) REFERENCES client (clientId)
);

CREATE TABLE preferences (
    clientId VARCHAR2(20) UNIQUE,
    investmentPurpose VARCHAR(255),
    riskTolerance int,
    incomeCategory int,
    lengthOfInvestment int,
    FOREIGN KEY (clientId) REFERENCES client (clientId)
);

COMMIT;