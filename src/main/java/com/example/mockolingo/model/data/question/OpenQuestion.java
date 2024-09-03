package com.example.mockolingo.model.data.question;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_question")
public class OpenQuestion extends Question {
    private String correctAnswer;
    @OneToMany(mappedBy = "question")
    private List<QuestionAnswer> questionAnswers;
}
