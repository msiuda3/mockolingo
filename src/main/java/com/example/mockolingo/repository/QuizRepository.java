package com.example.mockolingo.repository;

import com.example.mockolingo.controllers.datamodel.Quiz;
import com.example.mockolingo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer>{

    List<Quiz> findAll();

    @Query("SELECT t FROM Test t WHERE t.id NOT IN (SELECT tr.quiz.id FROM QuizResult tr WHERE tr.user = :user)")
    List<Quiz> findQuizesWithoutQuizResultForUser(@Param("user") User user);

    Optional<Quiz> findById(String id);


}
