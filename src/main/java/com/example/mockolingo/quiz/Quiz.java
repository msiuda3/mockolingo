package com.example.mockolingo.quiz;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "quiz")
public class Quiz {
    @Id
    private String id;
    private String quizName;
    private List<Question> questions;

}
