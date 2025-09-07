package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.EnrollmentDTO;
import com.eduverse.courseservice.entity.Course;
import com.eduverse.courseservice.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnrollmentMapper {

    @Mapping(source = "enrollment.id", target = "id")
    @Mapping(source = "enrollment.studentId", target = "studentId")
    @Mapping(source = "enrollment.courseId", target = "courseId")
    @Mapping(source = "enrollment.createdAt", target = "enrollmentDate")
    @Mapping(source = "enrollment.progressPercentage", target = "progress")
    @Mapping(source = "enrollment.updatedAt", target = "lastAccessed")
    @Mapping(source = "course.title", target = "courseTitle")
    @Mapping(source = "course.description", target = "courseDescription")
    @Mapping(source = "course.instructorId", target = "instructorId")
    @Mapping(source = "course.averageRating", target = "averageRating")
    EnrollmentDTO toDTO(Enrollment enrollment, Course course);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "studentId", target = "studentId")
    @Mapping(source = "courseId", target = "courseId")
    @Mapping(source = "createdAt", target = "enrollmentDate")
    @Mapping(source = "progressPercentage", target = "progress")
    @Mapping(source = "updatedAt", target = "lastAccessed")
    @Mapping(target = "courseTitle", ignore = true)
    @Mapping(target = "courseDescription", ignore = true)
    @Mapping(target = "instructorId", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    EnrollmentDTO toDTO(Enrollment enrollment);
}
