package com.example.mockolingo.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmitQuizRequest {
    int id;
    List<SubmitQuestionRequest> questions;

}
