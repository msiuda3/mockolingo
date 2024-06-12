package com.example.mockolingo.controllers.datamodel;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_quiz")
public class Quiz {

    private int ID;
    private String quizName;
    @OneToMany(mappedBy = "_test")
    private List<Question> questions;

}
