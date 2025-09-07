package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.AssignmentDTO;
import com.eduverse.courseservice.entity.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for Assignment entity and DTO conversions
 */
@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    /**
     * Convert Assignment entity to DTO
     */
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "isOverdue", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "daysUntilDue", ignore = true)
    @Mapping(target = "status", ignore = true)
    AssignmentDTO toDTO(Assignment assignment);

    /**
     * Convert Assignment DTO to entity
     */
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Assignment toEntity(AssignmentDTO assignmentDTO);

    /**
     * Update Assignment entity from DTO
     */
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateAssignmentFromDTO(AssignmentDTO assignmentDTO, @MappingTarget Assignment assignment);
}
