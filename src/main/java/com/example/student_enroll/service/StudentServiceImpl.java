package com.example.student_enroll.service;

import com.example.student_enroll.converter.CourseMapper;
import com.example.student_enroll.converter.StudentMapper;
import com.example.student_enroll.dtos.CourseDTO;
import com.example.student_enroll.dtos.StudentDTO;
import com.example.student_enroll.entity.Student;
import com.example.student_enroll.repository.StudentRepository;
import com.example.student_enroll.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private final StudentMapper studentMapper=StudentMapper.INSTANCE;
    private final CourseMapper courseMapper=CourseMapper.INSTANCE;

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> studentList=studentRepository.findAll();
        List<StudentDTO> studentDTOS = studentList.stream().map(student -> {
            StudentDTO dto = studentMapper.toDto(student);
            List<CourseDTO> courseDTOList = student.getEnrollmentList().stream()
                    .filter(enrollment -> enrollment.isActive() && enrollment.getCourse().isActive())
                    .map(enrollment -> courseMapper.toDto(enrollment.getCourse()))
                    .toList();
            dto.setCourseDTOList(courseDTOList);
            return dto;
        }).toList();

//        List<StudentDTO> studentDTOS=studentList.stream().map(student -> studentMapper.toDto(student)).toList();

        return studentDTOS;
    }

    @Override
    public StudentDTO getLoggedInStudentProfile() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Student student=studentRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("student not found"));
        StudentDTO studentDTO=studentMapper.toDto(student);

//        List<CourseDTO> courseDTOList=student.getEnrollmentList().stream()
//                .map(enrollment -> enrollment.getCourse()).map(course -> courseMapper.toDto(course)).toList();
        List<CourseDTO> courseDTOList = student.getEnrollmentList().stream()
                .filter(e -> e.isActive()) // ✅ Only active
                .map(e -> courseMapper.toDto(e.getCourse()))
                .toList();


        studentDTO.setCourseDTOList(courseDTOList);

        return studentDTO;

    }
}
