package com.example.mockolingo.service;

import com.example.mockolingo.model.data.*;
import com.example.mockolingo.model.data.question.ClosedChoicesQuestion;
import com.example.mockolingo.model.data.question.Question;
import com.example.mockolingo.model.data.question.QuestionAnswer;
import com.example.mockolingo.model.data.question.QuestionFactory;
import com.example.mockolingo.model.request.model.QuestionResult;
import com.example.mockolingo.model.request.response.CourseDetailsEditResponse;
import com.example.mockolingo.model.request.response.CourseDetailsResponse;
import com.example.mockolingo.model.request.response.CourseResultResponse;
import com.example.mockolingo.model.request.CourseSubmitRequest;
import com.example.mockolingo.model.request.SubmitQuestionRequest;
import com.example.mockolingo.model.request.SubmitQuizRequest;
import com.example.mockolingo.model.request.model.QuestionEditModel;
import com.example.mockolingo.model.request.model.QuestionModel;
import com.example.mockolingo.model.request.model.QuizModel;
import com.example.mockolingo.repository.QuizRepository;
import com.example.mockolingo.repository.QuizResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;
    private final UserService userService;
    private final QuestionFactory questionFactory;
    public List<QuizModel> getQuizesNotDoneByUser() {
        User currentUser = userService.getCurrentUser();
        List<Quiz> quizes = quizRepository.findQuizesWithoutQuizResultForUser(currentUser.getId());
        return quizes.stream().map(quiz -> QuizModel.builder().id(quiz.getID()).coursename(quiz.getQuizName()).build()).toList();
    }

    public CourseDetailsResponse getQuizById(int id) {
        Optional<Quiz> result = quizRepository.findById(id);
        if (result.isEmpty()) {
            return CourseDetailsResponse.builder().build();
        }
        Quiz quiz = result.get();

        return CourseDetailsResponse.builder()
                .id(quiz.getID())
                .coursename(quiz.getQuizName())
                .questions(
                        quiz.getQuestions().stream().map(question ->
                                QuestionModel.builder()
                                        .id(question.getID())
                                        .question(question.getQuestion())
                                        .details(question.toJsonRepresantion())
                                        .build()

                        ).toList()

                ).build();
    }

    //TODO rewrite this
   public CourseResultResponse submitQuiz(SubmitQuizRequest submitQuizRequest) {
        Quiz quiz = quizRepository.findById(submitQuizRequest.getId()).orElseThrow();
        QuizResult quizResult = QuizResult.builder()
                .quiz(quiz)
                .user(userService.getCurrentUser())
                .questions(

                        quiz.getQuestions().stream().map(question ->
                                {
                                    SubmitQuestionRequest submitQuestionRequest = submitQuizRequest.getQuestions().stream().filter(q -> q.getId() == question.getID()).findFirst().orElseThrow();
                                    return questionFactory.createQuestionAnswer(submitQuestionRequest);
                                }


                        ).toList()).user(userService.getCurrentUser())

                .score( //TODO this next, counting score
                        (int) quiz.getQuestions().stream().filter(closedChoicesQuestion ->
                                submitQuizRequest.getQuestions().stream()
                                        .filter(q -> q.getId() == closedChoicesQuestion.getID())
                                        .mapToInt(i -> closedChoicesQuestion.getScore(i))


                        ).count())
                .build();

        quizResult.getQuestions().forEach(questionAnswer -> questionAnswer.setQuizResult(quizResult));
        return getQuizResult(quizResultRepository.save(quizResult));
    }


    //TODO rewrite this
    /*public CourseResultResponse getQuizResult(int id) {
        return getQuizResult(quizResultRepository.findById(id).orElseThrow());
    }*/


    public CourseDetailsEditResponse getQuiz(int id) {
        Optional<Quiz> result = quizRepository.findById(id);
        if (result.isEmpty()) {
            return CourseDetailsEditResponse.builder().build();
        }
        Quiz quiz = result.get();

        return CourseDetailsEditResponse.builder()
                .id(quiz.getID())
                .coursename(quiz.getQuizName())
                .questions(
                        quiz.getQuestions().stream().map(closedChoicesQuestion ->
                                QuestionEditModel.builder()
                                        .id(closedChoicesQuestion.getID())
                                        .question(closedChoicesQuestion.getQuestion())
                                        .details(closedChoicesQuestion.toJsonRepresationEdit())
                                        .build()

                        ).toList()

                ).build();

    }

    //TODO rewrite this
   /* public CourseResultResponse getQuizResult(QuizResult quizResult) {

        return CourseResultResponse.builder()
                .id(quizResult.getID())
                .coursename(quizResult.getQuiz().getQuizName())
                .questions(
                        quizResult.getQuestions().stream().map(questionAnswer ->
                                QuestionResult.builder()
                                        .id(questionAnswer.getClosedChoicesQuestion().getID())
                                        .question(questionAnswer.getClosedChoicesQuestion().getQuestion())
                                        .answer(questionAnswer.getAnswer().name())
                                        .correct(questionAnswer.isCorrect())
                                        .correctAnswer(questionAnswer.getClosedChoicesQuestion().getCorrectAnswer().name())
                                        .build()

                                ).toList()

                )
                .score(quizResult.getScore())
                .build();

    }*/

    public List<QuizModel> getQuizesDoneByUser() {
        User currentUser = userService.getCurrentUser();
        List<QuizResult> quizes = quizResultRepository.findAllQuizesForUser(currentUser);
        return quizes.stream().map(quiz -> QuizModel.builder().id(quiz.getID()).coursename(quiz.getQuiz().getQuizName()).build()).toList();
    }

    public List<QuizModel> getAllQuizes() {
        List<Quiz> quizes = quizRepository.findAll();
        return quizes.stream().map(quiz -> QuizModel.builder().id(quiz.getID()).coursename(quiz.getQuizName()).build()).toList();
    }

    @Transactional
    public void submitQuiz(CourseSubmitRequest submitQuizRequest) {
        Quiz quiz = Quiz.builder()
                .quizName(submitQuizRequest.getCoursename())
                .questions(
                        submitQuizRequest.getQuestions().stream().map(
                                submitQuestionRequest  -> questionFactory.createQuestion(submitQuestionRequest)
                        ).collect(Collectors.toList())
                )
                .build();
        quiz.getQuestions().forEach(question -> question.setQuiz(quiz));
    }

    @Transactional
    public void editQuiz(CourseSubmitRequest submitQuizRequest) {
        Quiz quiz = quizRepository.findById(submitQuizRequest.getId()).orElseThrow();
                quiz.setQuizName(submitQuizRequest.getCoursename());

                quiz.getQuestions().clear();

                quiz.getQuestions().addAll(
                        submitQuizRequest.getQuestions().stream().map(
                                submitQuestionRequest -> questionFactory.createQuestion(submitQuestionRequest)
                        ).collect(Collectors.toList())

                );
        quizRepository.save(quiz);
    }
}
