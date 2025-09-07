package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.QuizDTO;
import com.eduverse.courseservice.entity.QuizOption;
import com.eduverse.courseservice.entity.Question;
import com.eduverse.courseservice.entity.Quiz;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper for Quiz entity and DTO conversions
 * Handles complex quiz mapping with questions and options
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizMapper {

    /**
     * Convert Quiz entity to QuizDTO
     */
    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "lesson.title", target = "lessonTitle")
    @Mapping(source = "questions", target = "questions")
    QuizDTO toDTO(Quiz quiz);

    /**
     * Convert QuizDTO to Quiz entity
     */
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Quiz toEntity(QuizDTO quizDTO);

    /**
     * Convert Question entity to QuestionDTO
     */
    @Mapping(source = "quiz.id", target = "quizId")
    @Mapping(source = "options", target = "options")
    QuizDTO.QuestionDTO questionToDTO(Question question);

    /**
     * Convert QuestionDTO to Question entity
     */
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "options", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Question questionToEntity(QuizDTO.QuestionDTO questionDTO);

    /**
     * Convert QuizOption entity to OptionDTO
     */
    @Mapping(target = "questionId", ignore = true)
    QuizDTO.OptionDTO optionToDTO(QuizOption option);
    
    /**
     * Convert OptionDTO to QuizOption entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    QuizOption optionToEntity(QuizDTO.OptionDTO optionDTO);    /**
     * Convert list of Quiz entities to list of QuizDTOs
     */
    List<QuizDTO> toDTOList(List<Quiz> quizzes);

    /**
     * Convert list of QuizDTOs to list of Quiz entities
     */
    List<Quiz> toEntityList(List<QuizDTO> quizDTOs);

    /**
     * Convert list of Question entities to list of QuestionDTOs
     */
    List<QuizDTO.QuestionDTO> questionsToDTOList(List<Question> questions);

    /**
     * Convert list of QuestionDTOs to list of Question entities
     */
    List<Question> questionsToEntityList(List<QuizDTO.QuestionDTO> questionDTOs);

    /**
     * Convert list of QuizOption entities to list of OptionDTOs
     */
    List<QuizDTO.OptionDTO> optionsToDTOList(List<QuizOption> options);
    
    /**
     * Convert list of OptionDTOs to list of QuizOption entities
     */
    List<QuizOption> optionsToEntityList(List<QuizDTO.OptionDTO> optionDTOs);    /**
     * Update Quiz entity from QuizDTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(QuizDTO quizDTO, @MappingTarget Quiz quiz);
}
