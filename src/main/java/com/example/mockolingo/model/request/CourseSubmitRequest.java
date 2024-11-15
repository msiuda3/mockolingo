package com.example.mockolingo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseSubmitRequest {
    private int id;
    private String coursename;
    private List<QuestionSubmitRequest> questions;
}
