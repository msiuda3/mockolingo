-- Wstawianie użytkowników
INSERT INTO _user (firstname, lastname, username, password, role) VALUES
('John', 'Doe', 'john_doe', '$2a$10$mIg03NoYOT5Tg5/4EuYylO0ag91m/2zkAfHN0XoPGB1TB/Kj7rqpG', 'USER'),
('Admin', 'User', 'admin_user', '$2a$10$mIg03NoYOT5Tg5/4EuYylO0ag91m/2zkAfHN0XoPGB1TB/Kj7rqpG', 'ADMIN'),
('Manager', 'User', 'manager_user', '$2a$10$mIg03NoYOT5Tg5/4EuYylO0ag91m/2zkAfHN0XoPGB1TB/Kj7rqpG', 'MANAGER');

-- Wstawianie quizu
INSERT INTO _quiz (quiz_name) VALUES
('Geography Quiz');

-- Wstawianie pytań i przypisywanie ich do quizu
INSERT INTO _question (question, a, b, c, correct_answer, quiz_id) VALUES
('What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'C', 1),
('What is the largest ocean?', 'Atlantic', 'Indian', 'Pacific', 'C', 1);
