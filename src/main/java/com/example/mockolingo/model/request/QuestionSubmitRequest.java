package com.example.mockolingo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSubmitRequest {
    private String question;
    private String a;
    private String b;
    private String c;
    private String correctAnswer;
}
