INSERT INTO `account` (account_name, email, password, current_balance, role, enabled) VALUES
('Random1', 'random1@names.com', '123456', 10000.00, 'USER', 1),
('Random2', 'random2@names.com', '123456', 32000.00, 'USER', 1),
('Random3', 'random3@names.com', '123456', 4000.00, 'USER', 1),
('Random4', 'random4@names.com', '123456', 154354.00, 'USER', 1),
('Random5', 'random5@names.com', '123456', 8677.00, 'USER', 1);

INSERT INTO `transaction` (id_account, transaction_amount, transaction_type, transaction_timestamp) VALUES
(2, 6000.00, 'WIRE_TRANSFER', {ts '2022-10-09 20:10:00'}),
(3, 2000.00, 'DEPOSIT', {ts '2022-10-09 20:10:00'}),
(1, 400.00, 'ONLINE_PAYMENT', {ts '2022-10-09 20:10:00'}),
(2, 0, 'CHECK', {ts '2022-10-09 20:10:00'});