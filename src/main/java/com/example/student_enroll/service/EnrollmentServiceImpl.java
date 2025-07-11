package com.example.student_enroll.service;

import com.example.student_enroll.converter.CourseMapper;
import com.example.student_enroll.dtos.CourseDTO;
import com.example.student_enroll.entity.Course;
import com.example.student_enroll.entity.Enrollment;
import com.example.student_enroll.entity.Student;
import com.example.student_enroll.repository.CourseRepository;
import com.example.student_enroll.repository.EnrollmentRepository;
import com.example.student_enroll.repository.StudentRepository;
import com.example.student_enroll.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private final CourseMapper courseMapper=CourseMapper.INSTANCE;

    public void enrollCourse(Long courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .filter(Course::isActive) // ✅ Check active course only
                .orElseThrow(() -> new RuntimeException("Course is not available or inactive"));

        // ✅ Prevent enrolling twice if already active
        boolean alreadyEnrolled = student.getEnrollmentList().stream()
                .anyMatch(e -> e.getCourse().getId().equals(courseId) && e.isActive());

        if (alreadyEnrolled) {
            throw new RuntimeException("Already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setActive(true);
        enrollmentRepository.save(enrollment);
    }


    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public List<CourseDTO> getEnrolledActiveCourses() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
//        List<CourseDTO> courseDTOS=student.getEnrollmentList().stream()
//                .map(enrollment -> enrollment.getCourse())
//                .filter(course -> course.isActive())
//                .map(course -> courseMapper.toDto(course)).toList();

        List<CourseDTO> courseDTOS = student.getEnrollmentList().stream()
                .filter(Enrollment::isActive) // ✅ filter enrollment isActive
                .map(Enrollment::getCourse)
                .filter(Course::isActive)     // ✅ also check if course is still active
                .map(courseMapper::toDto)
                .toList();
        return courseDTOS;
    }

    @Override
    public void unenrollCourse(Long courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Enrollment enrollment=student.getEnrollmentList().stream()
                .filter(e -> e.getCourse().getId().equals(courseId) && e.isActive())
                .findFirst().orElseThrow(()-> new RuntimeException("course not found"));

        enrollment.setActive(false);
        enrollmentRepository.save(enrollment);
    }
}
