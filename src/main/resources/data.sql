INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES
('admin', '$2a$10$Rs6OCZ1cq2IVVVU0SqUnL.4s3WMDqAf1TgxVavND/NHzLOZfrgWqO', 'admin'),
('user', '$2a$10$S6bhKvW538VGJt/1U05s/uIdsgxvA09Rv3RT/5B0Zr4PkYDdvn8N2', 'user');

INSERT INTO LOCATIONS (NAME) VALUES
('HALL A'),
('HALL B'),
('HALL C'),
('HALL D');

INSERT INTO EXHIBITIONS (theme, start_date, end_date, opening_hour, closing_hour, ticket_price) values
('Earth', '2022-10-01', '2022-10-30', '10:00', '20:00', 45.00),
('Space', '2022-11-01', '2022-11-30', '10:00', '20:00', 40.00),
('Oceans', '2022-12-01', '2022-12-30', '10:00', '20:00', 43.00);

INSERT INTO EXHIBITIONS_LOCATIONS (exhibition_id, location_id) values
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