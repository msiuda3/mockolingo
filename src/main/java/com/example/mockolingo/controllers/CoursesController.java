package com.example.mockolingo.controllers;

import com.example.mockolingo.model.data.Answer;
import com.example.mockolingo.model.data.Quiz;
import com.example.mockolingo.model.data.question.ClosedChoicesQuestion;
import com.example.mockolingo.model.data.question.OpenQuestion;
import com.example.mockolingo.model.data.question.Question;
import com.example.mockolingo.model.request.response.CourseDetailsEditResponse;
import com.example.mockolingo.model.request.response.CourseDetailsResponse;
import com.example.mockolingo.model.request.response.CourseResultResponse;
import com.example.mockolingo.model.request.CourseSubmitRequest;
import com.example.mockolingo.model.request.model.QuizModel;
import com.example.mockolingo.repository.QuizRepository;
import com.example.mockolingo.service.QuizService;
import com.example.mockolingo.model.request.SubmitQuizRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/courses")
public class CoursesController {

    private final QuizService quizService;

    private final QuizRepository quizRepository;


    @GetMapping("/test")
    String getTest(){ // for testing api deployment
       /* ClosedChoicesQuestion question = new ClosedChoicesQuestion("a", "b", "c", Answer.C);
        question.setQuestion("test");*/
        OpenQuestion question = new OpenQuestion("Test answer");
        question.setQuestion("Open Question test");
        Quiz quiz = new Quiz(3, "Test3", Collections.singletonList(question), new ArrayList<>());
        question.setQuiz(quiz);
        quizRepository.save(quiz);
        return "test";
    }


    @GetMapping("/available")
    ResponseEntity<List<QuizModel>> getAvailableQuizes(){
        return ResponseEntity.ok(quizService.getQuizesNotDoneByUser());
    }

    @GetMapping("/details/{id}")
    ResponseEntity<CourseDetailsResponse> getCourseDetails(@PathVariable int id){
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    //TODO implement service methods
/*

    @PostMapping("/submit")
    ResponseEntity<CourseResultResponse> submitQuiz(@RequestBody SubmitQuizRequest submitQuizRequest){
        return ResponseEntity.ok(quizService.submitQuiz(submitQuizRequest));
    }

    @GetMapping("/result/{id}")
    ResponseEntity<CourseResultResponse> getQuizResult(@PathVariable int id){
        return ResponseEntity.ok(quizService.getQuizResult(id));
    }

    @GetMapping("/history")
    ResponseEntity<List<QuizModel>> getHistory(){
        return ResponseEntity.ok(quizService.getQuizesDoneByUser());
    }



    //ADMIN
    @GetMapping("/all")
    ResponseEntity<List<QuizModel>> getAll(){
        return ResponseEntity.ok(quizService.getAllQuizes());
    }

    @GetMapping("/edit/{id}")
    ResponseEntity<CourseDetailsEditResponse> d(@PathVariable int id){
        return ResponseEntity.ok(quizService.getQuiz(id)); //TODO
    }

    @PostMapping("/save")
    ResponseEntity<String> saveQuiz(@RequestBody CourseSubmitRequest courseSubmitRequest){
        if(courseSubmitRequest.getId() != 0){
            quizService.editQuiz(courseSubmitRequest);
        }
        else {
            quizService.submitQuiz(courseSubmitRequest);
        }
        return ResponseEntity.ok("ok");
    }
*/


}
