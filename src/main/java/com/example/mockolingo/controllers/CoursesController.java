package com.example.mockolingo.controllers;

import com.example.mockolingo.model.Course;
import com.example.mockolingo.model.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CoursesController {

    @GetMapping("/api/{userid}/courses")
    List<Course> getCourseForUser(@PathVariable String userId){
        return null; //TODO
    }

    @GetMapping("/api/{courseid}/{questionNumber}")
    Question getQuestion(@PathVariable String courseId, @PathVariable String questionNumber){
        return null;//TODO
    }


}
