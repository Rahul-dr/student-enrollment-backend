package com.example.student_enroll.service.interfaces;

import com.example.student_enroll.dtos.CourseDTO;
import com.example.student_enroll.entity.Enrollment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EnrollmentService {
    void enrollCourse(Long courseId);

    List<Enrollment> getAllEnrollments();

    List<CourseDTO> getEnrolledActiveCourses();

    void unenrollCourse(Long courseId);
}
