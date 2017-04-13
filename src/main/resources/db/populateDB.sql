DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100000, '2017-04-09T10:00', 'Завтрак', 1000),
(100000, '2017-04-09T14:00', 'Обед', 660),
(100000, '2017-04-09T19:00', 'Ужин', 330),
(100001, '2017-04-10T09:00', 'Завтрак', 1000),
(100001, '2017-04-10T13:00', 'Обед', 660),
(100001, '2017-04-10T19:20', 'Ужин', 330);