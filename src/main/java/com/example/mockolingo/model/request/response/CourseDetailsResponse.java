package com.example.mockolingo.model.request.response;

import com.example.mockolingo.model.request.model.QuestionModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailsResponse {
    private int id;
    private String coursename;
    private List<QuestionModel> questions;
}
