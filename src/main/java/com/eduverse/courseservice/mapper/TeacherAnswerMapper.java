package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.TeacherAnswerDTO;
import com.eduverse.courseservice.entity.TeacherAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for TeacherAnswer entity and DTO conversions
 */
@Mapper(componentModel = "spring")
public interface TeacherAnswerMapper {
    
    TeacherAnswerMapper INSTANCE = Mappers.getMapper(TeacherAnswerMapper.class);
    
    /**
     * Convert TeacherAnswer entity to DTO
     */
    TeacherAnswerDTO toDto(TeacherAnswer entity);
    
    /**
     * Convert TeacherAnswerDTO to entity
     */
    @Mapping(target = "question", ignore = true)
    TeacherAnswer toEntity(TeacherAnswerDTO dto);
    
    /**
     * Convert list of entities to DTOs
     */
    List<TeacherAnswerDTO> toDtoList(List<TeacherAnswer> entities);
    
    /**
     * Convert list of DTOs to entities
     */
    List<TeacherAnswer> toEntityList(List<TeacherAnswerDTO> dtos);
    
    /**
     * Update entity from DTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "question", ignore = true)
    void updateEntityFromDto(TeacherAnswerDTO dto, @MappingTarget TeacherAnswer entity);
}
