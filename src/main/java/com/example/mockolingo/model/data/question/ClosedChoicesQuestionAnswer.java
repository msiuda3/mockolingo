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
@Builder
@Table(name = "_question_answer")
public class ClosedChoicesQuestionAnswer extends QuestionAnswer<ClosedChoicesQuestion> {
    @Enumerated(EnumType.STRING)
    private Answer answer;
    private boolean correct;
}
