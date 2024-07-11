CREATE TABLE _user (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE _quiz (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    quiz_name VARCHAR(255)
);

CREATE TABLE _question (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    question VARCHAR(255),
    a VARCHAR(255),
    b VARCHAR(255),
    c VARCHAR(255),
    CORRECT_ANSWER VARCHAR(255),
    quiz_id INT, -- Added foreign key column
    FOREIGN KEY (quiz_id) REFERENCES _quiz(ID) -- Foreign key reference
);

CREATE TABLE _quiz_result (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    quiz_id INT,
    user_id INT,
    score INT,
    FOREIGN KEY (quiz_id) REFERENCES _quiz(ID),
    FOREIGN KEY (user_id) REFERENCES _user(ID)
);


CREATE TABLE _question_answer (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT,
    answer VARCHAR(255),
    correct BOOLEAN,
    quiz_result_id INT,
    FOREIGN KEY (question_id) REFERENCES _question(ID),
    FOREIGN KEY (quiz_result_id) REFERENCES _quiz_result(ID)
);
