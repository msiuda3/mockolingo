package com.example.mockolingo.service;

import com.example.mockolingo.controllers.JwtService;
import com.example.mockolingo.model.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
