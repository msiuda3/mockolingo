package com.example.mockolingo.controllers.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "_question_answer")
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @Enumerated(EnumType.STRING)
    private Answer answer;
    private boolean correct;
    @ManyToOne
    @JoinColumn(name = "quiz_result_id")
    private QuizResult quizResult;
}
