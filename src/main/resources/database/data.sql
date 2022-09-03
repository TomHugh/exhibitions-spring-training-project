-- liquibase formatted sql
-- changeset tsliwinski:1

INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES
('admin', '$2a$10$Rs6OCZ1cq2IVVVU0SqUnL.4s3WMDqAf1TgxVavND/NHzLOZfrgWqO', 'admin'),
('user', '$2a$10$S6bhKvW538VGJt/1U05s/uIdsgxvA09Rv3RT/5B0Zr4PkYDdvn8N2', 'user');

INSERT INTO LOCATIONS (NAME) VALUES
('HALL A'),
('HALL B'),
('HALL C'),
('HALL D');

INSERT INTO EXHIBITIONS (theme, start_date, end_date, opening_hour, closing_hour, ticket_price, is_active) values
('Space', '2022-10-01', '2022-10-30', '10:00', '20:00', 45.00, FALSE),
('Earth', '2022-11-01', '2022-11-30', '10:00', '20:00', 40.00, TRUE),
('Oceans', '2022-12-01', '2022-12-30', '10:00', '20:00', 43.00, TRUE),
('Forest', '2023-01-01', '2023-01-31', '09:00', '18:00', 34.00, TRUE),
('Animals', '2023-02-01', '2023-02-28', '09:00', '18:00', 30.00, TRUE),
('People', '2023-03-01', '2023-03-31', '09:00', '18:00', 28.00, TRUE),
('Sky', '2023-04-01', '2023-04-30', '09:00', '18:00', 50.00, TRUE),
('Mountains', '2023-05-01', '2023-05-31', '09:00', '18:00', 32.00, TRUE),
('Nature', '2023-06-01', '2023-06-30', '09:00', '18:00', 31.00, TRUE),
('Rivers', '2023-07-01', '2023-07-31', '09:00', '18:00', 47.00, TRUE),
('Cities', '2023-08-01', '2023-08-31', '09:00', '18:00', 20.00, TRUE),
('Big Constructions', '2023-09-01', '2023-09-30', '09:00', '18:00', 32.00, TRUE),
('Cars', '2023-10-01', '2023-10-31', '09:00', '18:00', 35.00, TRUE);

INSERT INTO EXHIBITIONS_LOCATIONS (exhibition_id, location_id) values
(1, 1),
(1, 2),
(1, 4),
(2, 3),
(2, 2),
(3, 4),
(3, 1),
(4, 1),
(4, 3),
(4, 2),
(5, 3),
(5, 1),
(6, 4),
(7, 2),
(7, 1),
(8, 2),
(8, 3),
(9, 3),
(10, 2),
(11, 1),
(11, 2),
(11, 3),
(11, 4),
(12, 2),
(12, 3),
(13, 1),
(13, 4),
(13, 2);

INSERT INTO ORDERS (user_id, exhibition_id) values
(2,1),
(2,2),
(1,1),
(1,3);