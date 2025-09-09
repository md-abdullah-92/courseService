package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.EnrollmentDTO;
import com.eduverse.courseservice.entity.Course;
import com.eduverse.courseservice.entity.Enrollment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-09T06:03:47+0600",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class EnrollmentMapperImpl implements EnrollmentMapper {

    @Override
    public EnrollmentDTO toDTO(Enrollment enrollment, Course course) {
        if ( enrollment == null && course == null ) {
            return null;
        }

        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();

        if ( enrollment != null ) {
            enrollmentDTO.setId( enrollment.getId() );
            enrollmentDTO.setStudentId( enrollment.getStudentId() );
            enrollmentDTO.setCourseId( enrollment.getCourseId() );
            enrollmentDTO.setEnrollmentDate( enrollment.getCreatedAt() );
            enrollmentDTO.setProgress( enrollment.getProgressPercentage() );
            enrollmentDTO.setLastAccessed( enrollment.getUpdatedAt() );
        }
        if ( course != null ) {
            enrollmentDTO.setCourseTitle( course.getTitle() );
            enrollmentDTO.setCourseDescription( course.getDescription() );
            enrollmentDTO.setInstructorId( course.getInstructorId() );
            enrollmentDTO.setAverageRating( course.getAverageRating() );
        }

        return enrollmentDTO;
    }

    @Override
    public EnrollmentDTO toDTO(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }

        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();

        enrollmentDTO.setId( enrollment.getId() );
        enrollmentDTO.setStudentId( enrollment.getStudentId() );
        enrollmentDTO.setCourseId( enrollment.getCourseId() );
        enrollmentDTO.setEnrollmentDate( enrollment.getCreatedAt() );
        enrollmentDTO.setProgress( enrollment.getProgressPercentage() );
        enrollmentDTO.setLastAccessed( enrollment.getUpdatedAt() );

        return enrollmentDTO;
    }
}
