package com.example.mockolingo.repository;

import com.example.mockolingo.model.data.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer>{

    List<Quiz> findAll();

    @Query(value = "SELECT * FROM _quiz q WHERE q.ID NOT IN (SELECT qr.quiz_id FROM _quiz_result qr WHERE qr.user_id = :userId)", nativeQuery = true)
    List<Quiz> findQuizesWithoutQuizResultForUser(@Param("userId") Integer userId);

    Optional<Quiz> findByID(int id);


}
