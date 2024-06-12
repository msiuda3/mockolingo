package com.example.mockolingo.controllers.datamodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_question_answer")
public class QuestionAnswer {
    @Id
    @GeneratedValue
    private int ID;
    @OneToOne
    private Question question;
    @Enumerated(EnumType.STRING)
    private Answer answer;
    private boolean correct;
}
