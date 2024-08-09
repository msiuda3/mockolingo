package com.example.mockolingo.controllers;

import com.example.mockolingo.model.request.AuthenticationRequest;
import com.example.mockolingo.model.request.RegisterRequest;
import com.example.mockolingo.model.request.response.AuthenticationResponse;
import com.example.mockolingo.model.request.response.RoleResponse;
import com.example.mockolingo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @GetMapping("/role")
    public ResponseEntity<RoleResponse> getRole(){
        return ResponseEntity.ok(authenticationService.getRole());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
