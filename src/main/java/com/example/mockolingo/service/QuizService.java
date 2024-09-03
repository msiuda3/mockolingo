package com.example.mockolingo.service;

import com.example.mockolingo.model.data.*;
import com.example.mockolingo.model.data.question.ClosedChoicesQuestion;
import com.example.mockolingo.model.data.question.QuestionAnswer;
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
                        quiz.getClosedChoicesQuestions().stream().map(closedChoicesQuestion ->
                                QuestionModel.builder()
                                        .id(closedChoicesQuestion.getID())
                                        .question(closedChoicesQuestion.getQuestion())
                                        .a(closedChoicesQuestion.getA())
                                        .b(closedChoicesQuestion.getB())
                                        .c(closedChoicesQuestion.getC())
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

                        quiz.getClosedChoicesQuestions().stream().map(closedChoicesQuestion ->
                                {
                                    SubmitQuestionRequest questionRequest = submitQuizRequest.getQuestions().stream().filter(q -> q.getId() == closedChoicesQuestion.getID()).findFirst().orElseThrow();
                                    return QuestionAnswer.builder()
                                            .closedChoicesQuestion(closedChoicesQuestion)
                                            .correct(Answer.valueOf(questionRequest.getAnswer().toUpperCase()) == closedChoicesQuestion.getCorrectAnswer())
                                            .answer(Answer.valueOf(questionRequest.getAnswer().toUpperCase()))
                                            .build();
                                }


                        ).toList()).user(userService.getCurrentUser())

                .score(
                        (int) quiz.getClosedChoicesQuestions().stream().filter(closedChoicesQuestion ->
                                submitQuizRequest.getQuestions().stream()
                                        .anyMatch(q -> Answer.valueOf(q.getAnswer().toUpperCase()) == closedChoicesQuestion.getCorrectAnswer() && q.getId() == closedChoicesQuestion.getID())


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
                        quiz.getClosedChoicesQuestions().stream().map(closedChoicesQuestion ->
                                QuestionEditModel.builder()
                                        .id(closedChoicesQuestion.getID())
                                        .question(closedChoicesQuestion.getQuestion())
                                        .a(closedChoicesQuestion.getA())
                                        .b(closedChoicesQuestion.getB())
                                        .c(closedChoicesQuestion.getC())
                                        .correctAnswer(closedChoicesQuestion.getCorrectAnswer().toString())
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

    }

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
                .closedChoicesQuestions(
                        submitQuizRequest.getQuestions().stream().map(
                                submitQuestionRequest ->
                                    ClosedChoicesQuestion.builder()
                                            .question(submitQuestionRequest.getQuestion())
                                            .a(submitQuestionRequest.getA())
                                            .b(submitQuestionRequest.getB())
                                            .c(submitQuestionRequest.getC())
                                            .correctAnswer(Answer.valueOf(submitQuestionRequest.getCorrectAnswer().toUpperCase()))
                                            .build()
                        ).toList()

                )
                .build();
        quiz.getClosedChoicesQuestions().forEach(closedChoicesQuestion -> closedChoicesQuestion.setQuiz(quiz));
        quizRepository.save(quiz);
    }

    @Transactional
    public void editQuiz(CourseSubmitRequest submitQuizRequest) {
        Quiz quiz = quizRepository.findById(submitQuizRequest.getId()).orElseThrow();
                quiz.setQuizName(submitQuizRequest.getCoursename());

                quiz.getClosedChoicesQuestions().clear();

                quiz.getClosedChoicesQuestions().addAll(
                        submitQuizRequest.getQuestions().stream().map(
                                submitQuestionRequest ->
                                        ClosedChoicesQuestion.builder()
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
