package com.example.student_enroll.service;

import com.example.student_enroll.dtos.LoginRequest;
import com.example.student_enroll.dtos.RegisterRequest;
import com.example.student_enroll.entity.Student;
import com.example.student_enroll.repository.StudentRepository;
import com.example.student_enroll.service.interfaces.AuthService;
import com.example.student_enroll.util.JwtUtil;
import com.example.student_enroll.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest registerRequest){
        Student student=new Student();
        student.setUsername(registerRequest.getUsername());
        student.setPassword(PasswordUtil.encode(registerRequest.getPassword()));
        student.setRole("ROLE_STUDENT");
        studentRepository.save(student);
        return jwtUtil.generateToken(student.getUsername(), student.getRole());
    }

    public String login(LoginRequest loginRequest){
        Student student=studentRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new RuntimeException("user not found"));
        if(PasswordUtil.matches(loginRequest.getPassword(), student.getPassword())){
            return jwtUtil.generateToken(student.getUsername(), student.getRole());
        }
        throw new RuntimeException("Invalid Credentials");
    }
}
