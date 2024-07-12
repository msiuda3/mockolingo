-- Wstawianie użytkowników
INSERT INTO _user (firstname, lastname, username, password, role) VALUES
('John', 'Doe', 'normal_user', '$2a$10$mIg03NoYOT5Tg5/4EuYylO0ag91m/2zkAfHN0XoPGB1TB/Kj7rqpG', 'USER'),
('Admin', 'User', 'admin_user', '$2a$10$mIg03NoYOT5Tg5/4EuYylO0ag91m/2zkAfHN0XoPGB1TB/Kj7rqpG', 'ADMIN');

-- Wstawianie quizu
INSERT INTO _quiz (quiz_name) VALUES
('Quiz 1'),
('Quiz 2');

-- Wstawianie pytań i przypisywanie ich do quizu
INSERT INTO _question (question, a, b, c, correct_answer, quiz_id) VALUES
('Jeść', 'to eat', 'to sleep', 'to jump', 'A', 1),
('Pić', 'to sleep', 'to eat', 'to drink', 'C', 1);
('Pies', 'cat', 'dog', 'horse', 'B', 2);
('Ptak', 'bird', 'cat', 'fish', 'A', 2);
