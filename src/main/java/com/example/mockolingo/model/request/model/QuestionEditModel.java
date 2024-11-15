package com.example.mockolingo.model.request.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEditModel {
    private int id;
    private String question;
    private String a;
    private String b;
    private String c;
    private String correctAnswer;

}
