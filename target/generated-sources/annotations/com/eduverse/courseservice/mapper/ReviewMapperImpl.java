package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.ReviewDTO;
import com.eduverse.courseservice.entity.Course;
import com.eduverse.courseservice.entity.Review;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T18:44:11+0600",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDTO toDTO(Review review, Course course) {
        if ( review == null && course == null ) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();

        if ( review != null ) {
            reviewDTO.setId( review.getId() );
            reviewDTO.setStudentId( review.getStudentId() );
            reviewDTO.setCourseId( review.getCourseId() );
            reviewDTO.setRating( review.getRating() );
            reviewDTO.setReviewText( review.getComment() );
            reviewDTO.setCreatedAt( review.getCreatedAt() );
            reviewDTO.setUpdatedAt( review.getUpdatedAt() );
        }
        if ( course != null ) {
            reviewDTO.setCourseTitle( course.getTitle() );
        }

        return reviewDTO;
    }

    @Override
    public ReviewDTO toDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId( review.getId() );
        reviewDTO.setStudentId( review.getStudentId() );
        reviewDTO.setCourseId( review.getCourseId() );
        reviewDTO.setRating( review.getRating() );
        reviewDTO.setReviewText( review.getComment() );
        reviewDTO.setCreatedAt( review.getCreatedAt() );
        reviewDTO.setUpdatedAt( review.getUpdatedAt() );

        return reviewDTO;
    }
}
