package com.example.mockolingo.controllers;

import com.example.mockolingo.model.Course;
import com.example.mockolingo.model.CourseResult;
import com.example.mockolingo.model.Question;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CoursesController {

    @GetMapping("/api/test")
    String getTest(){ // for testing api deployment
        return "test";
    }


    @GetMapping("/api/{userid}/courses")
    List<Course> getCourseForUser(@PathVariable String userId){
        return null; //TODO
    }

    @GetMapping("/api/{languageId}/courses")
    List<Course> getAllCoursesForLanguage(@PathVariable String languageId){
        return null; //TODO
    }

    @GetMapping("/api/{courseid}/questions")
    List<Question> getQuestions(@PathVariable String courseId){
        return null;//TODO
    }

    @PostMapping(path = "/api/{courseid}/questions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    CourseResult postAnswers(@PathVariable String courseId){
        return null;//TODO
    }

    @GetMapping("/api/{courseid}/{userid}/result")
    CourseResult getResult(@PathVariable String courseId, @PathVariable String userId){
        return null;//TODO
    }

    @GetMapping("/api/{userid}/history")
    List<CourseResult> getHistory(@PathVariable String userId){
        return null;//TODO
    }


}
