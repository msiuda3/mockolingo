package com.example.mockolingo.model.data.question;

import com.example.mockolingo.model.data.Quiz;
import jakarta.persistence.*;

import java.util.List;

public abstract class Question<T extends QuestionAnswer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String question;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @OneToMany(mappedBy = "question")
    private List<T> questionAnswers;
}
