package com.example.mockolingo.model.data.question;

import com.example.mockolingo.model.request.CourseSubmitRequest;
import com.example.mockolingo.model.request.QuestionSubmitRequest;
import com.example.mockolingo.model.request.SubmitQuestionRequest;
import org.springframework.stereotype.Service;

@Service
public class QuestionFactory {
    public Question createQuestion(QuestionSubmitRequest questionSubmitRequest){
        Question question; //TODO
        question.processDetails(questionSubmitRequest.getDetails());
        return question;
    }

    public QuestionFactory
}
