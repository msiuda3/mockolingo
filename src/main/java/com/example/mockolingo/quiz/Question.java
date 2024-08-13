package com.example.mockolingo.quiz;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

 public abstract class Question {
    @Id
    private String id;
    private String questionText;
    private String questionType;
}
