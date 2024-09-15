package com.example.mockolingo.model.data.question;


import com.example.mockolingo.model.data.Answer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ClosedChoicesQuestion extends Question<ClosedChoicesQuestionAnswer> {
    private String a;
    private String b;
    private String c;
    @Enumerated(EnumType.STRING)
    private Answer correctAnswer;

    @Override
    public String toJsonRepresantion() {
        return "{\"test2\": \"value2\"]"; //TODO
    }

    @Override
    public void fromDetails(String details) {
        //TODO
    }
}
