package com.example.mockolingo.model.request.response;

import com.example.mockolingo.model.request.model.QuestionEditModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailsEditResponse {
    private int id;
    private String coursename;
    private List<QuestionEditModel> questions;
}
