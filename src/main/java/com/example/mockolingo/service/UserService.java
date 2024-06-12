package com.example.mockolingo.service;

import com.example.mockolingo.controllers.JwtService;
import com.example.mockolingo.controllers.datamodel.*;
import com.example.mockolingo.model.*;
import com.example.mockolingo.repository.QuestionRepository;
import com.example.mockolingo.repository.QuizRepository;
import com.example.mockolingo.repository.QuizResultRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User getCurrentUser(){
        final String authHeader = request.getHeader("Authorization");
        final String token = authHeader.substring(7);
        final String username = jwtService.extractUsername(token);
        return userRepository.findByUsername(username).orElseThrow();
    }


}
