package com.example.mockolingo.service;

import com.example.mockolingo.controllers.datamodel.*;
import com.example.mockolingo.model.*;
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

    public List<CourseShort> getQuizesNotDoneByUser() {
        User currentUser = userService.getCurrentUser();
        List<Quiz> quizes = quizRepository.findQuizesWithoutQuizResultForUser(currentUser.getId());
        return quizes.stream().map(quiz -> CourseShort.builder().id(quiz.getID()).coursename(quiz.getQuizName()).build()).toList();
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
                                Q.builder()
                                        .id(question.getID())
                                        .question(question.getQuestion())
                                        .a(question.getA())
                                        .b(question.getB())
                                        .c(question.getC())
                                        .build()

                        ).toList()

                ).build();
    }

    public CourseResultResponse submitQuiz(SubmitQuizRequest submitQuizRequest) {
        Quiz quiz = quizRepository.findById(submitQuizRequest.getId()).orElseThrow();
        QuizResult quizResult = QuizResult.builder()
                .quiz(quiz)
                .user(userService.getCurrentUser())
                .questions(

                        quiz.getQuestions().stream().map(question ->
                                {
                                    SubmitQuestionRequest questionRequest = submitQuizRequest.questions.stream().filter(q -> q.getId() == question.getID()).findFirst().orElseThrow();
                                    return QuestionAnswer.builder()
                                            .question(question)
                                            .correct(Answer.valueOf(questionRequest.getAnswer().toUpperCase()) == question.getCorrectAnswer())
                                            .answer(Answer.valueOf(questionRequest.getAnswer().toUpperCase()))
                                            .build();
                                }


                        ).toList()).user(userService.getCurrentUser())

                .score(
                        (int) quiz.getQuestions().stream().filter(question ->
                                submitQuizRequest.questions.stream()
                                        .anyMatch(q -> Answer.valueOf(q.getAnswer().toUpperCase()) == question.getCorrectAnswer() && q.getId() == question.getID())


                        ).count())
                .build();

        quizResult.getQuestions().forEach(questionAnswer -> questionAnswer.setQuizResult(quizResult));
        return getQuizResult(quizResultRepository.save(quizResult));
    }


    public CourseResultResponse getQuizResult(int id) {
        return getQuizResult(quizResultRepository.findById(id).orElseThrow());
    }


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
                        quiz.getQuestions().stream().map(question ->
                                QEdit.builder()
                                        .id(question.getID())
                                        .question(question.getQuestion())
                                        .a(question.getA())
                                        .b(question.getB())
                                        .c(question.getC())
                                        .correctAnswer(question.getCorrectAnswer().toString())
                                        .build()

                        ).toList()

                ).build();

    }

    public CourseResultResponse getQuizResult(QuizResult quizResult) {

        return CourseResultResponse.builder()
                .id(quizResult.getID())
                .coursename(quizResult.getQuiz().getQuizName())
                .questions(
                        quizResult.getQuestions().stream().map(questionAnswer ->
                                QuestionResult.builder()
                                        .id(questionAnswer.getQuestion().getID())
                                        .question(questionAnswer.getQuestion().getQuestion())
                                        .answer(questionAnswer.getAnswer().name())
                                        .correct(questionAnswer.isCorrect())
                                        .correctAnswer(questionAnswer.getQuestion().getCorrectAnswer().name())
                                        .build()

                                ).toList()

                )
                .score(quizResult.getScore())
                .build();

    }

    public List<CourseShort> getQuizesDoneByUser() {
        User currentUser = userService.getCurrentUser();
        List<QuizResult> quizes = quizResultRepository.findAllQuizesForUser(currentUser);
        return quizes.stream().map(quiz -> CourseShort.builder().id(quiz.getID()).coursename(quiz.getQuiz().getQuizName()).build()).toList();
    }

    public List<CourseShort> getAllQuizes() {
        List<Quiz> quizes = quizRepository.findAll();
        return quizes.stream().map(quiz -> CourseShort.builder().id(quiz.getID()).coursename(quiz.getQuizName()).build()).toList();
    }

    @Transactional
    public void submitQuiz(CourseSubmitRequest submitQuizRequest) {
        Quiz quiz = Quiz.builder()
                .quizName(submitQuizRequest.getCoursename())
                .questions(
                        submitQuizRequest.getQuestions().stream().map(
                                submitQuestionRequest ->
                                    Question.builder()
                                            .question(submitQuestionRequest.getQuestion())
                                            .a(submitQuestionRequest.getA())
                                            .b(submitQuestionRequest.getB())
                                            .c(submitQuestionRequest.getC())
                                            .correctAnswer(Answer.valueOf(submitQuestionRequest.getCorrectAnswer().toUpperCase()))
                                            .build()
                        ).toList()

                )
                .build();
        quiz.getQuestions().forEach(question -> question.setQuiz(quiz));
        quizRepository.save(quiz);
    }

    @Transactional
    public void editQuiz(CourseSubmitRequest submitQuizRequest) {
        Quiz quiz = quizRepository.findById(submitQuizRequest.getId()).orElseThrow();
                quiz.setQuizName(submitQuizRequest.getCoursename());

                quiz.getQuestions().clear();

                quiz.getQuestions().addAll(
                        submitQuizRequest.getQuestions().stream().map(
                                submitQuestionRequest ->
                                        Question.builder()
                                                .question(submitQuestionRequest.getQuestion())
                                                .a(submitQuestionRequest.getA())
                                                .b(submitQuestionRequest.getB())
                                                .c(submitQuestionRequest.getC())
                                                .correctAnswer(Answer.valueOf(submitQuestionRequest.getCorrectAnswer().toUpperCase()))
                                                .quiz(quiz)
                                                .build()
                        ).collect(Collectors.toCollection(ArrayList::new))

                );

        quizRepository.save(quiz);
    }

}
