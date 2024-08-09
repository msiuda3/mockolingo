package com.example.mockolingo.model.request.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResult {
    private int id;
    private String question;
    private boolean correct;
    private String answer;
    private String correctAnswer;

}
