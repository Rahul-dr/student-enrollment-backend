package com.example.student_enroll.service.interfaces;

import com.example.student_enroll.dtos.LoginRequest;
import com.example.student_enroll.dtos.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest registerRequest);

    String login(LoginRequest loginRequest);
}
