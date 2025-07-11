package com.example.student_enroll.controller;

import com.example.student_enroll.dtos.CourseDTO;
import com.example.student_enroll.dtos.StudentDTO;
import com.example.student_enroll.entity.Course;
import com.example.student_enroll.entity.Enrollment;
import com.example.student_enroll.service.interfaces.CourseService;
import com.example.student_enroll.service.interfaces.EnrollmentService;
import com.example.student_enroll.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO){
        return ResponseEntity.ok(courseService.addCourse(courseDTO));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllActiveCourses());
    }

    @GetMapping("/courses/{student_id}")
    public ResponseEntity<List<CourseDTO>> getCourse(@PathVariable Long student_id){
        return ResponseEntity.ok(courseService.getCourse(student_id));
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, courseDTO));
    }

    @DeleteMapping("/courses/{course_id}")
    public  ResponseEntity<String> deleteCourse(@PathVariable Long course_id){
        courseService.deleteCourse(course_id);
        return  ResponseEntity.ok("Course is successfully delete");
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
}
