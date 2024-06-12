package com.example.mockolingo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResultResponse {
    private int id;
    private String coursename;
    private List<QuestionResult> questions;
    private int score;
}
