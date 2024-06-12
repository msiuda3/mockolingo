package com.example.mockolingo.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmitQuestionRequest {
    int id;
    String answer;
}
