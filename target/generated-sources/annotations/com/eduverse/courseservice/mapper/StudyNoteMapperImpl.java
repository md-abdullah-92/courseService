package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.StudyNoteDTO;
import com.eduverse.courseservice.entity.StudyNote;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T18:15:54+0600",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class StudyNoteMapperImpl implements StudyNoteMapper {

    @Override
    public StudyNoteDTO toDTO(StudyNote studyNote) {
        if ( studyNote == null ) {
            return null;
        }

        StudyNoteDTO studyNoteDTO = new StudyNoteDTO();

        studyNoteDTO.setId( studyNote.getId() );
        studyNoteDTO.setTitle( studyNote.getTitle() );
        studyNoteDTO.setContent( studyNote.getContent() );
        studyNoteDTO.setLessonId( studyNote.getLessonId() );
        studyNoteDTO.setUserId( studyNote.getUserId() );
        studyNoteDTO.setUserName( studyNote.getUserName() );
        studyNoteDTO.setIsPublic( studyNote.getIsPublic() );
        studyNoteDTO.setIsFavorite( studyNote.getIsFavorite() );
        studyNoteDTO.setCategory( studyNote.getCategory() );
        studyNoteDTO.setTags( studyNote.getTags() );
        studyNoteDTO.setNoteType( studyNote.getNoteType() );
        studyNoteDTO.setFileUrl( studyNote.getFileUrl() );
        studyNoteDTO.setFileType( studyNote.getFileType() );
        studyNoteDTO.setFileSize( studyNote.getFileSize() );
        studyNoteDTO.setViewCount( studyNote.getViewCount() );
        studyNoteDTO.setLikeCount( studyNote.getLikeCount() );
        studyNoteDTO.setShareCount( studyNote.getShareCount() );
        studyNoteDTO.setLastAccessed( studyNote.getLastAccessed() );
        studyNoteDTO.setCreatedAt( studyNote.getCreatedAt() );
        studyNoteDTO.setUpdatedAt( studyNote.getUpdatedAt() );

        return studyNoteDTO;
    }

    @Override
    public StudyNote toEntity(StudyNoteDTO studyNoteDTO) {
        if ( studyNoteDTO == null ) {
            return null;
        }

        StudyNote studyNote = new StudyNote();

        studyNote.setTitle( studyNoteDTO.getTitle() );
        studyNote.setContent( studyNoteDTO.getContent() );
        studyNote.setLessonId( studyNoteDTO.getLessonId() );
        studyNote.setUserId( studyNoteDTO.getUserId() );
        studyNote.setUserName( studyNoteDTO.getUserName() );
        studyNote.setIsPublic( studyNoteDTO.getIsPublic() );
        studyNote.setIsFavorite( studyNoteDTO.getIsFavorite() );
        studyNote.setCategory( studyNoteDTO.getCategory() );
        studyNote.setTags( studyNoteDTO.getTags() );
        studyNote.setNoteType( studyNoteDTO.getNoteType() );
        studyNote.setFileUrl( studyNoteDTO.getFileUrl() );
        studyNote.setFileType( studyNoteDTO.getFileType() );
        studyNote.setFileSize( studyNoteDTO.getFileSize() );
        studyNote.setViewCount( studyNoteDTO.getViewCount() );
        studyNote.setLikeCount( studyNoteDTO.getLikeCount() );
        studyNote.setShareCount( studyNoteDTO.getShareCount() );

        return studyNote;
    }

    @Override
    public void updateStudyNoteFromDTO(StudyNoteDTO studyNoteDTO, StudyNote studyNote) {
        if ( studyNoteDTO == null ) {
            return;
        }

        studyNote.setTitle( studyNoteDTO.getTitle() );
        studyNote.setContent( studyNoteDTO.getContent() );
        studyNote.setLessonId( studyNoteDTO.getLessonId() );
        studyNote.setUserId( studyNoteDTO.getUserId() );
        studyNote.setUserName( studyNoteDTO.getUserName() );
        studyNote.setIsPublic( studyNoteDTO.getIsPublic() );
        studyNote.setIsFavorite( studyNoteDTO.getIsFavorite() );
        studyNote.setCategory( studyNoteDTO.getCategory() );
        studyNote.setTags( studyNoteDTO.getTags() );
        studyNote.setNoteType( studyNoteDTO.getNoteType() );
        studyNote.setFileUrl( studyNoteDTO.getFileUrl() );
        studyNote.setFileType( studyNoteDTO.getFileType() );
        studyNote.setFileSize( studyNoteDTO.getFileSize() );
        studyNote.setViewCount( studyNoteDTO.getViewCount() );
        studyNote.setLikeCount( studyNoteDTO.getLikeCount() );
        studyNote.setShareCount( studyNoteDTO.getShareCount() );
    }
}
