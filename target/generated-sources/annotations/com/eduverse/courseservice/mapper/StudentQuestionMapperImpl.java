package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.StudentQuestionDTO;
import com.eduverse.courseservice.entity.StudentQuestion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T18:44:12+0600",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class StudentQuestionMapperImpl implements StudentQuestionMapper {

    @Override
    public StudentQuestionDTO toDto(StudentQuestion entity) {
        if ( entity == null ) {
            return null;
        }

        StudentQuestionDTO studentQuestionDTO = new StudentQuestionDTO();

        studentQuestionDTO.setId( entity.getId() );
        studentQuestionDTO.setStudentName( entity.getStudentName() );
        studentQuestionDTO.setStudentPhotoUrl( entity.getStudentPhotoUrl() );
        studentQuestionDTO.setTitle( entity.getTitle() );
        studentQuestionDTO.setContent( entity.getContent() );
        studentQuestionDTO.setStudentId( entity.getStudentId() );
        studentQuestionDTO.setCourseId( entity.getCourseId() );
        studentQuestionDTO.setIsAnswered( entity.getIsAnswered() );
        studentQuestionDTO.setCreatedAt( entity.getCreatedAt() );
        studentQuestionDTO.setUpdatedAt( entity.getUpdatedAt() );

        return studentQuestionDTO;
    }

    @Override
    public StudentQuestion toEntity(StudentQuestionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StudentQuestion studentQuestion = new StudentQuestion();

        studentQuestion.setId( dto.getId() );
        studentQuestion.setStudentName( dto.getStudentName() );
        studentQuestion.setStudentPhotoUrl( dto.getStudentPhotoUrl() );
        studentQuestion.setTitle( dto.getTitle() );
        studentQuestion.setContent( dto.getContent() );
        studentQuestion.setStudentId( dto.getStudentId() );
        studentQuestion.setCourseId( dto.getCourseId() );
        studentQuestion.setIsAnswered( dto.getIsAnswered() );
        studentQuestion.setCreatedAt( dto.getCreatedAt() );
        studentQuestion.setUpdatedAt( dto.getUpdatedAt() );

        return studentQuestion;
    }

    @Override
    public List<StudentQuestionDTO> toDtoList(List<StudentQuestion> entities) {
        if ( entities == null ) {
            return null;
        }

        List<StudentQuestionDTO> list = new ArrayList<StudentQuestionDTO>( entities.size() );
        for ( StudentQuestion studentQuestion : entities ) {
            list.add( toDto( studentQuestion ) );
        }

        return list;
    }

    @Override
    public List<StudentQuestion> toEntityList(List<StudentQuestionDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<StudentQuestion> list = new ArrayList<StudentQuestion>( dtos.size() );
        for ( StudentQuestionDTO studentQuestionDTO : dtos ) {
            list.add( toEntity( studentQuestionDTO ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDto(StudentQuestionDTO dto, StudentQuestion entity) {
        if ( dto == null ) {
            return;
        }

        entity.setStudentName( dto.getStudentName() );
        entity.setStudentPhotoUrl( dto.getStudentPhotoUrl() );
        entity.setTitle( dto.getTitle() );
        entity.setContent( dto.getContent() );
        entity.setStudentId( dto.getStudentId() );
        entity.setCourseId( dto.getCourseId() );
        entity.setIsAnswered( dto.getIsAnswered() );
        entity.setUpdatedAt( dto.getUpdatedAt() );
    }
}
