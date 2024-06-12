package com.example.mockolingo.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmitQuizRequest {
    int quizId;
    List<SubmitQuestionRequest> questions;

}
