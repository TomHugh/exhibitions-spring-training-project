INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES
('tsliwinski', 'password', 'admin'),
('user1', 'user1', 'user');

INSERT INTO HALLS (NAME) VALUES
('HALL A'),
('HALL B'),
('HALL C'),
('HALL D');

INSERT INTO EXHIBITIONS (theme, start_of_operation, end_of_operation, working_start, working_end, ticket_price) values
('Earth', '2022-10-01', '2022-10-30', '10:00', '20:00', 45.00),
('Space', '2022-11-01', '2022-11-30', '10:00', '20:00', 40.00),
('Oceans', '2022-12-01', '2022-12-30', '10:00', '20:00', 43.00);

INSERT INTO EXHIBITIONS_HALLS (exhibition_id, hall_id) values
(1, 1),
(1, 2),
(1, 4),
(2, 3),
(2, 2),
(3, 4);

INSERT INTO ORDERS (user_id, exhibition_id) values
(2,1),
(2,2),
(1,1),
(1,3);