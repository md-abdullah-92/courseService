package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.ReviewDTO;
import com.eduverse.courseservice.entity.Course;
import com.eduverse.courseservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    @Mapping(source = "review.id", target = "id")
    @Mapping(source = "review.studentId", target = "studentId")
    @Mapping(source = "review.courseId", target = "courseId")
    @Mapping(source = "review.rating", target = "rating")
    @Mapping(source = "review.comment", target = "reviewText")
    @Mapping(source = "review.createdAt", target = "createdAt")
    @Mapping(source = "review.updatedAt", target = "updatedAt")
    @Mapping(source = "course.title", target = "courseTitle")
    @Mapping(target = "studentName", ignore = true)
    ReviewDTO toDTO(Review review, Course course);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "studentId", target = "studentId")
    @Mapping(source = "courseId", target = "courseId")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "comment", target = "reviewText")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(target = "courseTitle", ignore = true)
    @Mapping(target = "studentName", ignore = true)
    ReviewDTO toDTO(Review review);
}
