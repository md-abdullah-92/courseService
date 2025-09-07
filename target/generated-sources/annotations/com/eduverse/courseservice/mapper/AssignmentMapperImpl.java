package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.AssignmentDTO;
import com.eduverse.courseservice.entity.Assignment;
import com.eduverse.courseservice.entity.Lesson;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T18:44:11+0600",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class AssignmentMapperImpl implements AssignmentMapper {

    @Override
    public AssignmentDTO toDTO(Assignment assignment) {
        if ( assignment == null ) {
            return null;
        }

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setLessonId( assignmentLessonId( assignment ) );
        assignmentDTO.setId( assignment.getId() );
        assignmentDTO.setTitle( assignment.getTitle() );
        assignmentDTO.setDescription( assignment.getDescription() );
        assignmentDTO.setTeacherId( assignment.getTeacherId() );
        assignmentDTO.setTeacherName( assignment.getTeacherName() );
        assignmentDTO.setDueDate( assignment.getDueDate() );
        assignmentDTO.setMaxMarks( assignment.getMaxMarks() );
        assignmentDTO.setIsPublished( assignment.getIsPublished() );
        assignmentDTO.setInstructions( assignment.getInstructions() );
        assignmentDTO.setLessonTitle( assignment.getLessonTitle() );
        assignmentDTO.setCreatedAt( assignment.getCreatedAt() );
        assignmentDTO.setUpdatedAt( assignment.getUpdatedAt() );

        return assignmentDTO;
    }

    @Override
    public Assignment toEntity(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }

        Assignment assignment = new Assignment();

        assignment.setTitle( assignmentDTO.getTitle() );
        assignment.setDescription( assignmentDTO.getDescription() );
        assignment.setTeacherId( assignmentDTO.getTeacherId() );
        assignment.setTeacherName( assignmentDTO.getTeacherName() );
        assignment.setDueDate( assignmentDTO.getDueDate() );
        assignment.setMaxMarks( assignmentDTO.getMaxMarks() );
        assignment.setIsPublished( assignmentDTO.getIsPublished() );
        assignment.setInstructions( assignmentDTO.getInstructions() );

        return assignment;
    }

    @Override
    public void updateAssignmentFromDTO(AssignmentDTO assignmentDTO, Assignment assignment) {
        if ( assignmentDTO == null ) {
            return;
        }

        assignment.setTitle( assignmentDTO.getTitle() );
        assignment.setDescription( assignmentDTO.getDescription() );
        assignment.setTeacherId( assignmentDTO.getTeacherId() );
        assignment.setTeacherName( assignmentDTO.getTeacherName() );
        assignment.setDueDate( assignmentDTO.getDueDate() );
        assignment.setMaxMarks( assignmentDTO.getMaxMarks() );
        assignment.setIsPublished( assignmentDTO.getIsPublished() );
        assignment.setInstructions( assignmentDTO.getInstructions() );
    }

    private Long assignmentLessonId(Assignment assignment) {
        Lesson lesson = assignment.getLesson();
        if ( lesson == null ) {
            return null;
        }
        return lesson.getId();
    }
}
