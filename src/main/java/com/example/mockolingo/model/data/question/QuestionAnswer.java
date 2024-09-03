package com.example.mockolingo.model.data.question;

import com.example.mockolingo.model.data.Answer;
import com.example.mockolingo.model.data.QuizResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class QuestionAnswer<T extends Question> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private T closedChoicesQuestion;
    @ManyToOne
    @JoinColumn(name = "quiz_result_id")
    private QuizResult quizResult;
}
