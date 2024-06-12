package com.example.mockolingo.repository;

import com.example.mockolingo.controllers.datamodel.Question;
import com.example.mockolingo.controllers.datamodel.Quiz;
import com.example.mockolingo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
    Optional<Question> findById(String id);

}
