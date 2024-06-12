package com.example.mockolingo.controllers;

import com.example.mockolingo.model.*;
import com.example.mockolingo.service.QuizService;
import com.example.mockolingo.service.SubmitQuizRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CoursesController {

    QuizService quizService;


    @GetMapping("/api/test")
    String getTest(){ // for testing api deployment
        return "test";
    }


    @GetMapping("/list")
    ResponseEntity<List<CourseShort>> getCourseForUser(){
        return ResponseEntity.ok(quizService.getQuizesDoneByUser());
    }

    @GetMapping("/details/{$id}")
    ResponseEntity<CourseDetailsResponse> getCourseDetails(@PathVariable int id){
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @PostMapping("/submit")
    ResponseEntity<CourseResultResponse> submitQuiz(@RequestBody SubmitQuizRequest submitQuizRequest){
        return ResponseEntity.ok(quizService.submitQuiz(submitQuizRequest));
    }

    @GetMapping("/result/{$id}")
    ResponseEntity<CourseResultResponse> getQuizResult(@PathVariable int id){
        return ResponseEntity.ok(quizService.getQuizResult(id));
    }

    @GetMapping("/history")
    ResponseEntity<List<CourseShort>> getHistory(){
        return ResponseEntity.ok(quizService.getQuizesDoneByUser());
    }



    //ADMIN
    @GetMapping("/all")
    ResponseEntity<List<CourseShort>> getAll(){
        return ResponseEntity.ok(quizService.getAllQuizes());
    }

    @PostMapping("/quiz/save")
    ResponseEntity<String> saveQuiz(@RequestBody CourseSubmitRequest courseSubmitRequest){
        quizService.submitQuiz(courseSubmitRequest);
        return ResponseEntity.ok("ok");
    }


}
