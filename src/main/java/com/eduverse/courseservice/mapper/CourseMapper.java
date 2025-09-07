package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.CourseDto;
import com.eduverse.courseservice.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    
    public CourseDto toDto(Course course) {
        if (course == null) {
            return null;
        }
        
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setPrice(course.getPrice());
        courseDto.setCoverPhotoUrl(course.getCoverPhotoUrl());
        courseDto.setLevel(course.getLevel());
        courseDto.setTopic(course.getTopic());
        courseDto.setInstructorId(course.getInstructorId());
        courseDto.setAverageRating(course.getAverageRating());
        courseDto.setCreatedAt(course.getCreatedAt());
        courseDto.setUpdatedAt(course.getUpdatedAt());
        
        // Set counts if collections are loaded
        if (course.getEnrollments() != null) {
            courseDto.setEnrollmentCount(course.getEnrollments().size());
        } else {
            courseDto.setEnrollmentCount(0);
        }
        
        if (course.getReviews() != null) {
            courseDto.setReviewCount(course.getReviews().size());
        } else {
            courseDto.setReviewCount(0);
        }
        
        return courseDto;
    }
    
    public Course toEntity(CourseDto courseDto) {
        if (courseDto == null) {
            return null;
        }
        
        Course course = new Course();
        course.setId(courseDto.getId());
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setPrice(courseDto.getPrice());
        course.setCoverPhotoUrl(courseDto.getCoverPhotoUrl());
        course.setLevel(courseDto.getLevel());
        course.setTopic(courseDto.getTopic());
        course.setInstructorId(courseDto.getInstructorId());
        course.setAverageRating(courseDto.getAverageRating());
        
        return course;
    }
}
