package com.example.student_enroll.service;

import com.example.student_enroll.converter.CourseMapper;
import com.example.student_enroll.dtos.CourseDTO;
import com.example.student_enroll.entity.Course;
import com.example.student_enroll.entity.Enrollment;
import com.example.student_enroll.entity.Student;
import com.example.student_enroll.repository.CourseRepository;
import com.example.student_enroll.repository.StudentRepository;
import com.example.student_enroll.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private final CourseMapper courseMapper=CourseMapper.INSTANCE;

    public CourseDTO addCourse(CourseDTO courseDTO){
        if(courseRepository.findByName(courseDTO.getName()).isPresent()){
            throw new RuntimeException("Course already exist");
        }
        Course course=courseMapper.toEntity(courseDTO);
        return courseMapper.toDto(courseRepository.save(course));
    }

    public List<Course> getAllActiveCourses(){
        return courseRepository.findByActiveTrue();
    }

    public List<CourseDTO> getCourse(Long student_id){
        Student student=studentRepository.findById(student_id).orElseThrow(()-> new RuntimeException("student not found"));
        return student.getEnrollmentList().stream().map(enrollment -> courseMapper.toDto(enrollment.getCourse())).distinct().toList();
    }

    @Override
    public CourseDTO updateCourse(Long courseId, CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setName(courseDTO.getName());
        return courseMapper.toDto(courseRepository.save(course));
    }


    @Override
    public void deleteCourse(Long courseId) {
        Course course=courseRepository.findById(courseId).orElseThrow(()-> new RuntimeException("Course is not found"));
        course.setActive(false);
        courseRepository.save(course);
    }
}
