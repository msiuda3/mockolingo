package com.example.mockolingo.model.data;

import com.example.mockolingo.model.data.question.QuestionAnswer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_quiz_result")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
   @ManyToOne
   @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "quizResult", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   private List<QuestionAnswer> questions;
   private int score;
}
