package com.example.student_enroll.service.interfaces;

import com.example.student_enroll.dtos.CourseDTO;
import com.example.student_enroll.entity.Course;

import java.util.List;

public interface CourseService {
    CourseDTO addCourse(CourseDTO courseDTO);

    List<Course> getAllActiveCourses();

    List<CourseDTO> getCourse(Long studentId);


    void deleteCourse(Long courseId);

    CourseDTO updateCourse(Long courseId, CourseDTO courseDTO);
}
