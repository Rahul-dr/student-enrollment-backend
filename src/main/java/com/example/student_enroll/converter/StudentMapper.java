package com.example.student_enroll.converter;

import com.example.student_enroll.dtos.StudentDTO;
import com.example.student_enroll.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE= Mappers.getMapper(StudentMapper.class);

    StudentDTO toDto(Student student);
    Student toEntity(StudentDTO studentDTO);
}
