package com.example.student_enroll.service.interfaces;

import com.example.student_enroll.dtos.StudentDTO;
import com.example.student_enroll.entity.Student;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAllStudents();

    StudentDTO getLoggedInStudentProfile();
}
