package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.QuizDTO;
import com.eduverse.courseservice.entity.Lesson;
import com.eduverse.courseservice.entity.Question;
import com.eduverse.courseservice.entity.Quiz;
import com.eduverse.courseservice.entity.QuizOption;
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
public class QuizMapperImpl implements QuizMapper {

    @Override
    public QuizDTO toDTO(Quiz quiz) {
        if ( quiz == null ) {
            return null;
        }

        QuizDTO quizDTO = new QuizDTO();

        quizDTO.setLessonId( quizLessonId( quiz ) );
        quizDTO.setLessonTitle( quizLessonTitle( quiz ) );
        quizDTO.setQuestions( questionsToDTOList( quiz.getQuestions() ) );
        quizDTO.setId( quiz.getId() );
        quizDTO.setTitle( quiz.getTitle() );
        quizDTO.setDescription( quiz.getDescription() );
        quizDTO.setTimeLimit( quiz.getTimeLimit() );
        quizDTO.setPassingScore( quiz.getPassingScore() );
        quizDTO.setIsPublished( quiz.getIsPublished() );
        quizDTO.setDuration( quiz.getDuration() );
        quizDTO.setMarks( quiz.getMarks() );
        quizDTO.setCreatedAt( quiz.getCreatedAt() );
        quizDTO.setUpdatedAt( quiz.getUpdatedAt() );
        quizDTO.setTotalQuestions( quiz.getTotalQuestions() );

        return quizDTO;
    }

    @Override
    public Quiz toEntity(QuizDTO quizDTO) {
        if ( quizDTO == null ) {
            return null;
        }

        Quiz quiz = new Quiz();

        quiz.setId( quizDTO.getId() );
        quiz.setTitle( quizDTO.getTitle() );
        quiz.setDescription( quizDTO.getDescription() );
        quiz.setTimeLimit( quizDTO.getTimeLimit() );
        quiz.setPassingScore( quizDTO.getPassingScore() );
        quiz.setIsPublished( quizDTO.getIsPublished() );
        quiz.setDuration( quizDTO.getDuration() );
        quiz.setMarks( quizDTO.getMarks() );

        return quiz;
    }

    @Override
    public QuizDTO.QuestionDTO questionToDTO(Question question) {
        if ( question == null ) {
            return null;
        }

        QuizDTO.QuestionDTO questionDTO = new QuizDTO.QuestionDTO();

        questionDTO.setQuizId( questionQuizId( question ) );
        questionDTO.setOptions( optionsToDTOList( question.getOptions() ) );
        questionDTO.setId( question.getId() );
        questionDTO.setQuestion( question.getQuestion() );
        questionDTO.setCorrectAnswer( question.getCorrectAnswer() );
        questionDTO.setExplanation( question.getExplanation() );
        questionDTO.setDifficulty( question.getDifficulty() );
        questionDTO.setType( question.getType() );
        questionDTO.setCreatedAt( question.getCreatedAt() );
        questionDTO.setUpdatedAt( question.getUpdatedAt() );
        questionDTO.setTotalOptions( question.getTotalOptions() );

        return questionDTO;
    }

    @Override
    public Question questionToEntity(QuizDTO.QuestionDTO questionDTO) {
        if ( questionDTO == null ) {
            return null;
        }

        Question question = new Question();

        question.setId( questionDTO.getId() );
        question.setQuestion( questionDTO.getQuestion() );
        question.setCorrectAnswer( questionDTO.getCorrectAnswer() );
        question.setExplanation( questionDTO.getExplanation() );
        question.setDifficulty( questionDTO.getDifficulty() );
        question.setType( questionDTO.getType() );

        return question;
    }

    @Override
    public QuizDTO.OptionDTO optionToDTO(QuizOption option) {
        if ( option == null ) {
            return null;
        }

        QuizDTO.OptionDTO optionDTO = new QuizDTO.OptionDTO();

        optionDTO.setId( option.getId() );
        optionDTO.setText( option.getText() );
        optionDTO.setIsCorrect( option.getIsCorrect() );
        optionDTO.setCreatedAt( option.getCreatedAt() );
        optionDTO.setUpdatedAt( option.getUpdatedAt() );

        return optionDTO;
    }

    @Override
    public QuizOption optionToEntity(QuizDTO.OptionDTO optionDTO) {
        if ( optionDTO == null ) {
            return null;
        }

        QuizOption quizOption = new QuizOption();

        quizOption.setText( optionDTO.getText() );
        quizOption.setIsCorrect( optionDTO.getIsCorrect() );

        return quizOption;
    }

    @Override
    public List<QuizDTO> toDTOList(List<Quiz> quizzes) {
        if ( quizzes == null ) {
            return null;
        }

        List<QuizDTO> list = new ArrayList<QuizDTO>( quizzes.size() );
        for ( Quiz quiz : quizzes ) {
            list.add( toDTO( quiz ) );
        }

        return list;
    }

    @Override
    public List<Quiz> toEntityList(List<QuizDTO> quizDTOs) {
        if ( quizDTOs == null ) {
            return null;
        }

        List<Quiz> list = new ArrayList<Quiz>( quizDTOs.size() );
        for ( QuizDTO quizDTO : quizDTOs ) {
            list.add( toEntity( quizDTO ) );
        }

        return list;
    }

    @Override
    public List<QuizDTO.QuestionDTO> questionsToDTOList(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuizDTO.QuestionDTO> list = new ArrayList<QuizDTO.QuestionDTO>( questions.size() );
        for ( Question question : questions ) {
            list.add( questionToDTO( question ) );
        }

        return list;
    }

    @Override
    public List<Question> questionsToEntityList(List<QuizDTO.QuestionDTO> questionDTOs) {
        if ( questionDTOs == null ) {
            return null;
        }

        List<Question> list = new ArrayList<Question>( questionDTOs.size() );
        for ( QuizDTO.QuestionDTO questionDTO : questionDTOs ) {
            list.add( questionToEntity( questionDTO ) );
        }

        return list;
    }

    @Override
    public List<QuizDTO.OptionDTO> optionsToDTOList(List<QuizOption> options) {
        if ( options == null ) {
            return null;
        }

        List<QuizDTO.OptionDTO> list = new ArrayList<QuizDTO.OptionDTO>( options.size() );
        for ( QuizOption quizOption : options ) {
            list.add( optionToDTO( quizOption ) );
        }

        return list;
    }

    @Override
    public List<QuizOption> optionsToEntityList(List<QuizDTO.OptionDTO> optionDTOs) {
        if ( optionDTOs == null ) {
            return null;
        }

        List<QuizOption> list = new ArrayList<QuizOption>( optionDTOs.size() );
        for ( QuizDTO.OptionDTO optionDTO : optionDTOs ) {
            list.add( optionToEntity( optionDTO ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDTO(QuizDTO quizDTO, Quiz quiz) {
        if ( quizDTO == null ) {
            return;
        }

        quiz.setTitle( quizDTO.getTitle() );
        quiz.setDescription( quizDTO.getDescription() );
        quiz.setTimeLimit( quizDTO.getTimeLimit() );
        quiz.setPassingScore( quizDTO.getPassingScore() );
        quiz.setIsPublished( quizDTO.getIsPublished() );
        quiz.setDuration( quizDTO.getDuration() );
        quiz.setMarks( quizDTO.getMarks() );
    }

    private Long quizLessonId(Quiz quiz) {
        Lesson lesson = quiz.getLesson();
        if ( lesson == null ) {
            return null;
        }
        return lesson.getId();
    }

    private String quizLessonTitle(Quiz quiz) {
        Lesson lesson = quiz.getLesson();
        if ( lesson == null ) {
            return null;
        }
        return lesson.getTitle();
    }

    private Long questionQuizId(Question question) {
        Quiz quiz = question.getQuiz();
        if ( quiz == null ) {
            return null;
        }
        return quiz.getId();
    }
}
