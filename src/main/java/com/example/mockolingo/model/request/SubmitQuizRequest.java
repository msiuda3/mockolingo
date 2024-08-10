package com.example.mockolingo.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubmitQuizRequest {
    int id;
    List<SubmitQuestionRequest> questions;

}
