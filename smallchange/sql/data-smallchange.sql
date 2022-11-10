INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1234', 'aadrs@gmail.com','19900101', 'US','123456','pass13',12345.45,'USD');
INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1235', 'sara@gmail.com','20002512', 'US','123477','pass1234',748295.45,'USD');
INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1236', 'priya@gmail.com','19990303', 'US','123333','pass1234',748295.45,'USD');
INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1237', 'stephen@gmail.com','19801112', 'US','123558','pass1234',748295.45,'USD');
INSERT INTO client (clientId, email, dob, country, postalCode, password, wallet, walletCurrency) VALUES ('1238', 'prabhu@gmail.com','20011230', 'US','123777','pass1234',748295.45,'USD');

INSERT INTO preferences (clientId, investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment) 
    VALUES ('1235', 'Savings', 5, 3, 4);    
INSERT INTO preferences (clientId, investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment) 
    VALUES ('1236', 'Savings', 1, 1, 1);
INSERT INTO preferences (clientId, investmentPurpose, riskTolerance, incomeCategory, lengthOfInvestment) 
    VALUES ('1238', 'Savings', 2, 4, 2);
    
INSERT INTO clientidentification (clientId, type, value) VALUES ('1235', 'SSN', 'SSNVal1');

INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1234','Q456',50,450.89);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1236','T67890',70,670.89);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1236','T67894',91,1350.71);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1238','C100',101,4563.67);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1238','Q123',370,10037.32);
INSERT INTO portfolio (clientId, instrumentId,quantity,value) VALUES ('1238','T67894',91,10657.32);

COMMIT;