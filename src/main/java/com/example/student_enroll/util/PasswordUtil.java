package com.example.student_enroll.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    private static final PasswordEncoder encoder=new BCryptPasswordEncoder();

    public static String encode(String password){
        return encoder.encode(password);
    }

    public static boolean matches(String raw, String encoded){
        return  encoder.matches(raw, encoded);
    }
}
