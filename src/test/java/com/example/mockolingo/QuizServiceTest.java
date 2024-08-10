package com.example.mockolingo;

import com.example.mockolingo.model.data.*;
import com.example.mockolingo.model.request.QuestionSubmitRequest;
import com.example.mockolingo.model.request.model.QuestionModel;
import com.example.mockolingo.model.request.model.QuizModel;
import com.example.mockolingo.model.request.response.CourseDetailsResponse;
import com.example.mockolingo.model.request.response.CourseResultResponse;
import com.example.mockolingo.model.request.CourseSubmitRequest;
import com.example.mockolingo.model.request.SubmitQuestionRequest;
import com.example.mockolingo.model.request.SubmitQuizRequest;
import com.example.mockolingo.repository.QuizRepository;
import com.example.mockolingo.repository.QuizResultRepository;
import com.example.mockolingo.service.QuizService;
import com.example.mockolingo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizResultRepository quizResultRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuizesNotDoneByUser() {
        User mockUser = User.builder().id(1).build();
        List<Quiz> mockQuizes = List.of(
                Quiz.builder().ID(1).quizName("Quiz 1").build(),
                Quiz.builder().ID(2).quizName("Quiz 2").build()
        );

        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(quizRepository.findQuizesWithoutQuizResultForUser(mockUser.getId())).thenReturn(mockQuizes);

        List<QuizModel> result = quizService.getQuizesNotDoneByUser();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Quiz 1", result.get(0).getCoursename());
        verify(userService).getCurrentUser();
        verify(quizRepository).findQuizesWithoutQuizResultForUser(mockUser.getId());
    }

    @Test
    void testGetQuizById_WhenQuizExists() {
        Quiz mockQuiz = Quiz.builder()
                .ID(1)
                .quizName("Test Quiz")
                .questions(List.of(Question.builder()
                        .ID(1)
                        .question("Question 1")
                        .a("A")
                        .b("B")
                        .c("C")
                        .correctAnswer(Answer.A)
                        .build()))
                .build();

        when(quizRepository.findById(1)).thenReturn(Optional.of(mockQuiz));

        CourseDetailsResponse response = quizService.getQuizById(1);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Test Quiz", response.getCoursename());
        assertEquals(1, response.getQuestions().size());
        verify(quizRepository).findById(1);
    }



    @Test
    @Disabled("TODO: Decide what to do if cant find quiz with given id")
    void testGetQuizById_WhenQuizDoesNotExist() {
        when(quizRepository.findById(1)).thenReturn(Optional.empty());

        CourseDetailsResponse response = quizService.getQuizById(1);

        assertNotNull(response);
        assertNull(response.getId());
        verify(quizRepository).findById(1);
    }



    @Test
    void testSubmitQuiz() {
        SubmitQuizRequest submitQuizRequest = SubmitQuizRequest.builder()
                .id(1)
                .questions(List.of(SubmitQuestionRequest.builder()
                        .id(1)
                        .answer("a")
                        .build()))
                .build();

        Quiz mockQuiz = Quiz.builder()
                .ID(1)
                .quizName("Test Quiz")
                .questions(List.of(Question.builder()
                        .ID(1)
                        .question("Question 1")
                        .a("A")
                        .b("B")
                        .c("C")
                        .correctAnswer(Answer.A)
                        .build()))
                .build();

        User mockUser = User.builder().id(1).build();

        when(quizRepository.findById(1)).thenReturn(Optional.of(mockQuiz));
        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(quizResultRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CourseResultResponse response = quizService.submitQuiz(submitQuizRequest);

        assertNotNull(response);
        assertEquals(1, response.getQuestions().size());
        assertEquals(1, response.getScore());
        verify(quizRepository).findById(1);
        verify(quizResultRepository).save(any(QuizResult.class));
    }

    @Test
    void testGetQuizResult() {
        Quiz mockQuiz = Quiz.builder()
                .ID(1)
                .quizName("Test Quiz")
                .build();

        Question mockQuestion = Question.builder()
                .ID(1)
                .question("Question 1")
                .a("A")
                .b("B")
                .c("C")
                .correctAnswer(Answer.A)
                .build();

        QuizResult mockQuizResult = QuizResult.builder()
                .ID(1)
                .quiz(mockQuiz)
                .questions(List.of(QuestionAnswer.builder()
                        .question(mockQuestion)
                        .correct(true)
                        .answer(Answer.A)
                        .build()))
                .score(1)
                .build();

        when(quizResultRepository.findById(1)).thenReturn(Optional.of(mockQuizResult));

        CourseResultResponse response = quizService.getQuizResult(1);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Test Quiz", response.getCoursename());
        verify(quizResultRepository).findById(1);
    }

    @Test
    void testSubmitQuiz_Transactional() {
        // Przygotowanie
        CourseSubmitRequest submitQuizRequest = CourseSubmitRequest.builder()
                .coursename("New Quiz")
                .questions(List.of(
                        QuestionSubmitRequest.builder()
                                .question("Question 1")
                                .a("A")
                                .b("B")
                                .c("C")
                                .correctAnswer("A")
                                .build()
                ))
                .build();

        quizService.submitQuiz(submitQuizRequest);

        verify(quizRepository).save(any(Quiz.class));
    }

    @Test
    void testEditQuiz() {
        CourseSubmitRequest submitQuizRequest = CourseSubmitRequest.builder()
                .id(1)
                .coursename("Updated Quiz")
                .questions(List.of(
                        QuestionSubmitRequest.builder()
                                .question("Updated Question 1")
                                .a("A")
                                .b("B")
                                .c("C")
                                .correctAnswer("A")
                                .build()
                ))
                .build();

        Quiz mockQuiz = Quiz.builder()
                .ID(1)
                .quizName("Old Quiz")
                .questions(
                        new ArrayList<>(
                        List.of(
                        Question.builder()
                                .question("Base Question 1")
                                .a("A1")
                                .b("B1")
                                .c("C1")
                                .correctAnswer(Answer.A)
                                .build()
                )))
                .build();

        when(quizRepository.findById(1)).thenReturn(Optional.of(mockQuiz));

        quizService.editQuiz(submitQuizRequest);

        verify(quizRepository).save(any(Quiz.class));
        assertEquals("Updated Quiz", mockQuiz.getQuizName());
    }


}
