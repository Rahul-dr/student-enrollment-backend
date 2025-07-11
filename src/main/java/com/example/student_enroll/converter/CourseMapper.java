package com.example.student_enroll.converter;

import com.example.student_enroll.dtos.CourseDTO;
import com.example.student_enroll.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE=Mappers.getMapper(CourseMapper.class);

    CourseDTO toDto(Course course);
    Course toEntity(CourseDTO courseDTO);

}
