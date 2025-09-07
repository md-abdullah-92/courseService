package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.LessonDto;
import com.eduverse.courseservice.entity.Lesson;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T18:44:12+0600",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class LessonMapperImpl implements LessonMapper {

    @Override
    public LessonDto toDto(Lesson lesson) {
        if ( lesson == null ) {
            return null;
        }

        LessonDto lessonDto = new LessonDto();

        lessonDto.setId( lesson.getId() );
        lessonDto.setTitle( lesson.getTitle() );
        lessonDto.setDescription( lesson.getDescription() );
        lessonDto.setVideoUrl( lesson.getVideoUrl() );
        lessonDto.setNotes( lesson.getNotes() );
        lessonDto.setOrderIndex( lesson.getOrderIndex() );
        lessonDto.setCourseId( lesson.getCourseId() );
        lessonDto.setCreatedAt( lesson.getCreatedAt() );
        lessonDto.setUpdatedAt( lesson.getUpdatedAt() );

        return lessonDto;
    }

    @Override
    public Lesson toEntity(LessonDto lessonDto) {
        if ( lessonDto == null ) {
            return null;
        }

        Lesson lesson = new Lesson();

        lesson.setId( lessonDto.getId() );
        lesson.setTitle( lessonDto.getTitle() );
        lesson.setDescription( lessonDto.getDescription() );
        lesson.setVideoUrl( lessonDto.getVideoUrl() );
        lesson.setNotes( lessonDto.getNotes() );
        lesson.setOrderIndex( lessonDto.getOrderIndex() );
        lesson.setCourseId( lessonDto.getCourseId() );

        return lesson;
    }

    @Override
    public void updateLessonFromDto(LessonDto lessonDto, Lesson lesson) {
        if ( lessonDto == null ) {
            return;
        }

        lesson.setTitle( lessonDto.getTitle() );
        lesson.setDescription( lessonDto.getDescription() );
        lesson.setVideoUrl( lessonDto.getVideoUrl() );
        lesson.setNotes( lessonDto.getNotes() );
        lesson.setOrderIndex( lessonDto.getOrderIndex() );
        lesson.setCourseId( lessonDto.getCourseId() );
    }
}
