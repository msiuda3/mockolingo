package com.example.mockolingo.repository;

import com.example.mockolingo.controllers.datamodel.Quiz;
import com.example.mockolingo.controllers.datamodel.QuizResult;
import com.example.mockolingo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Integer>{

    @Query("SELECT t FROM QuizResult t WHERE t.user = :user")
    List<QuizResult> findAllQuizesForUser(@Param("user") User user);
    Optional<QuizResult> findById(int id);
}