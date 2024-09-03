package com.example.mockolingo.model.data.question;

import com.example.mockolingo.model.data.Answer;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OpenQuestionAnswer extends QuestionAnswer<OpenQuestion> {
    private String answer;
    private boolean correct;
}
