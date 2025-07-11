package com.example.student_enroll.controller;

import com.example.student_enroll.dtos.CourseDTO;
import com.example.student_enroll.dtos.StudentDTO;
import com.example.student_enroll.repository.StudentRepository;
import com.example.student_enroll.service.interfaces.EnrollmentService;
import com.example.student_enroll.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<String> enrollCourse(@PathVariable Long courseId){
        enrollmentService.enrollCourse(courseId);
        return ResponseEntity.ok("Enrolled Successfully");
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getEnrolledCourses(){
        return ResponseEntity.ok(enrollmentService.getEnrolledActiveCourses());
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentDTO> getStudentProfile() {
        StudentDTO profile = studentService.getLoggedInStudentProfile(); // implement based on logged-in user
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/unenroll/{courseId}")
    public ResponseEntity<String> unenrollFromCourse(@PathVariable Long courseId) {
        enrollmentService.unenrollCourse(courseId); // Soft delete logic
        return ResponseEntity.ok("Unenrolled from course successfully");
    }


}
