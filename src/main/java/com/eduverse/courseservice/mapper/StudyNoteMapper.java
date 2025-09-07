package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.StudyNoteDTO;
import com.eduverse.courseservice.entity.StudyNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for StudyNote entity and DTO conversions
 */
@Mapper(componentModel = "spring")
public interface StudyNoteMapper {

    StudyNoteMapper INSTANCE = Mappers.getMapper(StudyNoteMapper.class);

    /**
     * Convert StudyNote entity to DTO
     */
    @Mapping(target = "isRecent", ignore = true)
    @Mapping(target = "isPopular", ignore = true)
    @Mapping(target = "hasAttachment", ignore = true)
    @Mapping(target = "daysOld", ignore = true)
    @Mapping(target = "shortContent", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "tagsFromArray", ignore = true)
    StudyNoteDTO toDTO(StudyNote studyNote);

    /**
     * Convert StudyNote DTO to entity
     */
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastAccessed", ignore = true)
    StudyNote toEntity(StudyNoteDTO studyNoteDTO);

    /**
     * Update StudyNote entity from DTO
     */
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastAccessed", ignore = true)
    void updateStudyNoteFromDTO(StudyNoteDTO studyNoteDTO, @MappingTarget StudyNote studyNote);
}
