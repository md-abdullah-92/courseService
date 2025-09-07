package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.StudentQuestionDTO;
import com.eduverse.courseservice.entity.StudentQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for StudentQuestion entity and DTO conversions
 */
@Mapper(componentModel = "spring")
public interface StudentQuestionMapper {
    
    StudentQuestionMapper INSTANCE = Mappers.getMapper(StudentQuestionMapper.class);
    
    /**
     * Convert StudentQuestion entity to DTO
     */
    @Mapping(target = "answer", ignore = true) // Handle separately if needed
    StudentQuestionDTO toDto(StudentQuestion entity);
    
    /**
     * Convert StudentQuestionDTO to entity
     */
    @Mapping(target = "answer", ignore = true) // Handle separately
    StudentQuestion toEntity(StudentQuestionDTO dto);
    
    /**
     * Convert list of entities to DTOs
     */
    List<StudentQuestionDTO> toDtoList(List<StudentQuestion> entities);
    
    /**
     * Convert list of DTOs to entities
     */
    List<StudentQuestion> toEntityList(List<StudentQuestionDTO> dtos);
    
    /**
     * Update entity from DTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "answer", ignore = true)
    void updateEntityFromDto(StudentQuestionDTO dto, @MappingTarget StudentQuestion entity);
}
