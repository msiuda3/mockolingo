package com.example.mockolingo.controllers.datamodel;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_question")
public class Question {
    @Id
    @GeneratedValue
    private int ID;
    private String question;
    private String a;
    private String b;
    private String c;
    @Enumerated(EnumType.STRING)
    private Answer correctAnswer;
}
