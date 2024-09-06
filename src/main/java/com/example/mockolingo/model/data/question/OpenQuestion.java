package com.example.mockolingo.model.data.question;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OpenQuestion extends Question<OpenQuestionAnswer> {
    private String correctAnswer;
}
