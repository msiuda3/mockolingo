package com.example.mockolingo.controllers.datamodel;

import com.example.mockolingo.model.User;
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
@Table(name = "_quiz_result")
public class QuizResult {

    @Id
    @GeneratedValue
    private int ID;
   @OneToOne
    private Quiz quiz;
    @OneToOne
    private User user;
   @OneToMany
   private List<QuestionAnswer> questions;
   private int score;
}
