package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.TeacherAnswerDTO;
import com.eduverse.courseservice.entity.TeacherAnswer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T18:44:11+0600",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class TeacherAnswerMapperImpl implements TeacherAnswerMapper {

    @Override
    public TeacherAnswerDTO toDto(TeacherAnswer entity) {
        if ( entity == null ) {
            return null;
        }

        TeacherAnswerDTO teacherAnswerDTO = new TeacherAnswerDTO();

        if ( entity.getId() != null ) {
            teacherAnswerDTO.setId( String.valueOf( entity.getId() ) );
        }
        teacherAnswerDTO.setContent( entity.getContent() );
        if ( entity.getTeacherId() != null ) {
            teacherAnswerDTO.setTeacherId( entity.getTeacherId().intValue() );
        }
        teacherAnswerDTO.setTeacherName( entity.getTeacherName() );
        teacherAnswerDTO.setTeacherPhotoUrl( entity.getTeacherPhotoUrl() );
        teacherAnswerDTO.setQuestionId( entity.getQuestionId() );
        teacherAnswerDTO.setCreatedAt( entity.getCreatedAt() );
        teacherAnswerDTO.setUpdatedAt( entity.getUpdatedAt() );

        return teacherAnswerDTO;
    }

    @Override
    public TeacherAnswer toEntity(TeacherAnswerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TeacherAnswer teacherAnswer = new TeacherAnswer();

        if ( dto.getId() != null ) {
            teacherAnswer.setId( Long.parseLong( dto.getId() ) );
        }
        teacherAnswer.setContent( dto.getContent() );
        if ( dto.getTeacherId() != null ) {
            teacherAnswer.setTeacherId( dto.getTeacherId().longValue() );
        }
        teacherAnswer.setTeacherName( dto.getTeacherName() );
        teacherAnswer.setTeacherPhotoUrl( dto.getTeacherPhotoUrl() );
        teacherAnswer.setQuestionId( dto.getQuestionId() );
        teacherAnswer.setCreatedAt( dto.getCreatedAt() );
        teacherAnswer.setUpdatedAt( dto.getUpdatedAt() );

        return teacherAnswer;
    }

    @Override
    public List<TeacherAnswerDTO> toDtoList(List<TeacherAnswer> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TeacherAnswerDTO> list = new ArrayList<TeacherAnswerDTO>( entities.size() );
        for ( TeacherAnswer teacherAnswer : entities ) {
            list.add( toDto( teacherAnswer ) );
        }

        return list;
    }

    @Override
    public List<TeacherAnswer> toEntityList(List<TeacherAnswerDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<TeacherAnswer> list = new ArrayList<TeacherAnswer>( dtos.size() );
        for ( TeacherAnswerDTO teacherAnswerDTO : dtos ) {
            list.add( toEntity( teacherAnswerDTO ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDto(TeacherAnswerDTO dto, TeacherAnswer entity) {
        if ( dto == null ) {
            return;
        }

        entity.setContent( dto.getContent() );
        if ( dto.getTeacherId() != null ) {
            entity.setTeacherId( dto.getTeacherId().longValue() );
        }
        else {
            entity.setTeacherId( null );
        }
        entity.setTeacherName( dto.getTeacherName() );
        entity.setTeacherPhotoUrl( dto.getTeacherPhotoUrl() );
        entity.setQuestionId( dto.getQuestionId() );
        entity.setUpdatedAt( dto.getUpdatedAt() );
    }
}
