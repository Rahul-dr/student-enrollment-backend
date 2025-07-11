package com.example.student_enroll.controller;

import com.example.student_enroll.dtos.JwtResponse;
import com.example.student_enroll.dtos.LoginRequest;
import com.example.student_enroll.dtos.RegisterRequest;
import com.example.student_enroll.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(new JwtResponse(authService.register(registerRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
//        System.out.println(new BCryptPasswordEncoder().encode("1100"));
        return ResponseEntity.ok(new JwtResponse(authService.login(loginRequest)));
    }
}
