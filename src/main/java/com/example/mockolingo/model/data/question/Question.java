package com.example.mockolingo.model.data.question;

import com.example.mockolingo.model.data.Quiz;
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
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question<T extends QuestionAnswer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String question;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @OneToMany(mappedBy = "question")
    private List<T> questionAnswers;

    public abstract String toJsonRepresantion();
    public abstract String toJsonRepresationEdit();

    public abstract void processDetails(String jsonDetails);
}
