package com.eduverse.courseservice.service;

import com.eduverse.courseservice.dto.QuizDTO;
import com.eduverse.courseservice.entity.Lesson;
import com.eduverse.courseservice.entity.QuizOption;
import com.eduverse.courseservice.entity.Question;
import com.eduverse.courseservice.entity.Quiz;
import com.eduverse.courseservice.exception.ResourceNotFoundException;
import com.eduverse.courseservice.mapper.QuizMapper;
import com.eduverse.courseservice.repository.LessonRepository;
import com.eduverse.courseservice.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Quiz operations
 * Provides comprehensive quiz management functionality
 */
@Service
@Transactional
public class QuizService {

    private static final Logger log = LoggerFactory.getLogger(QuizService.class);
    private final QuizRepository quizRepository;
    private final LessonRepository lessonRepository;
    private final QuizMapper quizMapper;
    
    public QuizService(QuizRepository quizRepository, LessonRepository lessonRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.lessonRepository = lessonRepository;
        this.quizMapper = quizMapper;
    }

    /**
     * Create a new quiz
     */
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        log.info("Creating new quiz: {}", quizDTO.getTitle());
        
        // Validate lesson exists
        Lesson lesson = lessonRepository.findById(quizDTO.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + quizDTO.getLessonId()));
        
        // Check for duplicate quiz title in the same lesson
        if (quizRepository.existsByLessonIdAndTitle(quizDTO.getLessonId(), quizDTO.getTitle())) {
            throw new IllegalArgumentException("Quiz with title '" + quizDTO.getTitle() + "' already exists in this lesson");
        }
        
        Quiz quiz = quizMapper.toEntity(quizDTO);
        quiz.setLesson(lesson);
        
        // Process questions and options if provided
        if (quizDTO.getQuestions() != null && !quizDTO.getQuestions().isEmpty()) {
            processQuizQuestions(quiz, quizDTO.getQuestions());
        }
        
        Quiz savedQuiz = quizRepository.save(quiz);
        
        QuizDTO result = quizMapper.toDTO(savedQuiz);
        result.calculateTotalQuestions();
        result.calculateTotalMarks();
        
        log.info("Quiz created successfully with id: {}", savedQuiz.getId());
        return result;
    }

    /**
     * Get all quizzes
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> getAllQuizzes() {
        log.info("Fetching all quizzes");
        
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(quiz -> {
                    QuizDTO dto = quizMapper.toDTO(quiz);
                    dto.calculateTotalQuestions();
                    dto.calculateTotalMarks();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get quiz by ID
     */
    @Transactional(readOnly = true)
    public QuizDTO getQuizById(Long quizId) {
        log.info("Fetching quiz with id: {}", quizId);
        
        Quiz quiz = quizRepository.findByIdWithQuestions(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        
        QuizDTO result = quizMapper.toDTO(quiz);
        result.calculateTotalQuestions();
        result.calculateTotalMarks();
        
        return result;
    }

    /**
     * Get quizzes by lesson ID
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> getQuizzesByLessonId(Long lessonId) {
        log.info("Fetching quizzes for lesson id: {}", lessonId);
        
        // Validate lesson exists
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson not found with id: " + lessonId);
        }
        
        List<Quiz> quizzes = quizRepository.findByLessonId(lessonId);
        return quizzes.stream()
                .map(quiz -> {
                    QuizDTO dto = quizMapper.toDTO(quiz);
                    dto.calculateTotalQuestions();
                    dto.calculateTotalMarks();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Update quiz
     */
    public QuizDTO updateQuiz(Long quizId, QuizDTO quizDTO) {
        log.info("Updating quiz with id: {}", quizId);
        
        Quiz existingQuiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        
        // Update quiz properties
        if (quizDTO.getTitle() != null) {
            existingQuiz.setTitle(quizDTO.getTitle());
        }
        if (quizDTO.getDescription() != null) {
            existingQuiz.setDescription(quizDTO.getDescription());
        }
        if (quizDTO.getTimeLimit() != null) {
            existingQuiz.setTimeLimit(quizDTO.getTimeLimit());
        }
        if (quizDTO.getPassingScore() != null) {
            existingQuiz.setPassingScore(quizDTO.getPassingScore());
        }
        if (quizDTO.getIsPublished() != null) {
            existingQuiz.setIsPublished(quizDTO.getIsPublished());
        }
        if (quizDTO.getDuration() != null) {
            existingQuiz.setDuration(quizDTO.getDuration());
        }
        if (quizDTO.getMarks() != null) {
            existingQuiz.setMarks(quizDTO.getMarks());
        }
        
        Quiz savedQuiz = quizRepository.save(existingQuiz);
        
        QuizDTO result = quizMapper.toDTO(savedQuiz);
        result.calculateTotalQuestions();
        result.calculateTotalMarks();
        
        log.info("Quiz updated successfully");
        return result;
    }

    /**
     * Delete quiz
     */
    public void deleteQuiz(Long quizId) {
        log.info("Deleting quiz with id: {}", quizId);
        
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        
        quizRepository.delete(quiz);
        log.info("Quiz deleted successfully");
    }

    /**
     * Save complete quiz with questions and options
     */
    public QuizDTO saveCompleteQuiz(Long lessonId, QuizDTO quizDTO) {
        log.info("Saving complete quiz for lesson id: {}", lessonId);
        
        // Validate lesson exists
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + lessonId));
        
        Quiz quiz = new Quiz();
        quiz.setLesson(lesson);
        quiz.setMarks(quizDTO.getMarks() != null ? quizDTO.getMarks() : 0);
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setTimeLimit(quizDTO.getTimeLimit());
        quiz.setPassingScore(quizDTO.getPassingScore());
        quiz.setIsPublished(quizDTO.getIsPublished() != null ? quizDTO.getIsPublished() : false);
        quiz.setDuration(quizDTO.getDuration());
        
        // Process questions and options
        if (quizDTO.getQuestions() != null && !quizDTO.getQuestions().isEmpty()) {
            processQuizQuestions(quiz, quizDTO.getQuestions());
        }
        
        Quiz savedQuiz = quizRepository.save(quiz);
        
        QuizDTO result = quizMapper.toDTO(savedQuiz);
        result.calculateTotalQuestions();
        result.calculateTotalMarks();
        
        log.info("Complete quiz saved successfully with id: {}", savedQuiz.getId());
        return result;
    }

    /**
     * Get published quizzes by lesson ID
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> getPublishedQuizzesByLessonId(Long lessonId) {
        log.info("Fetching published quizzes for lesson id: {}", lessonId);
        
        List<Quiz> quizzes = quizRepository.findPublishedByLessonId(lessonId);
        return quizzes.stream()
                .map(quiz -> {
                    QuizDTO dto = quizMapper.toDTO(quiz);
                    dto.calculateTotalQuestions();
                    dto.calculateTotalMarks();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Process quiz questions and options
     */
    private void processQuizQuestions(Quiz quiz, List<QuizDTO.QuestionDTO> questionDTOs) {
        for (QuizDTO.QuestionDTO questionDTO : questionDTOs) {
            Question question = new Question();
            question.setQuestion(questionDTO.getQuestion());
            question.setCorrectAnswer(questionDTO.getCorrectAnswer());
            question.setExplanation(questionDTO.getExplanation());
            question.setDifficulty(questionDTO.getDifficulty());
            question.setType(questionDTO.getType());
            
            quiz.addQuestion(question);
            
            // Process options if provided
            if (questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {
                for (QuizDTO.OptionDTO optionDTO : questionDTO.getOptions()) {
                    QuizOption option = new QuizOption();
                    option.setText(optionDTO.getText());
                    option.setIsCorrect(optionDTO.getIsCorrect() != null ? optionDTO.getIsCorrect() : false);
                    
                    question.addOption(option);
                }
            }
        }
    }

    /**
     * Get quiz statistics
     */
    @Transactional(readOnly = true)
    public QuizStatisticsDTO getQuizStatistics(Long quizId) {
        Quiz quiz = quizRepository.findByIdWithQuestions(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
        
        QuizStatisticsDTO stats = new QuizStatisticsDTO();
        stats.setQuizId(quizId);
        stats.setQuizTitle(quiz.getTitle());
        stats.setTotalQuestions(quiz.getTotalQuestions());
        stats.setTotalMarks(quiz.getMarks() != null ? quiz.getMarks() : quiz.getTotalQuestions());
        stats.setIsPublished(quiz.getIsPublished());
        stats.setCreatedAt(quiz.getCreatedAt());
        
        return stats;
    }
    
    /**
     * Inner class for quiz statistics
     */
    public static class QuizStatisticsDTO {
        private Long quizId;
        private String quizTitle;
        private Integer totalQuestions;
        private Integer totalMarks;
        private Boolean isPublished;
        private java.time.LocalDateTime createdAt;
        
        // Constructors
        public QuizStatisticsDTO() {}
        
        public QuizStatisticsDTO(Long quizId, String quizTitle, Integer totalQuestions, 
                                Integer totalMarks, Boolean isPublished, 
                                java.time.LocalDateTime createdAt) {
            this.quizId = quizId;
            this.quizTitle = quizTitle;
            this.totalQuestions = totalQuestions;
            this.totalMarks = totalMarks;
            this.isPublished = isPublished;
            this.createdAt = createdAt;
        }
        
        // Getters and Setters
        public Long getQuizId() { return quizId; }
        public void setQuizId(Long quizId) { this.quizId = quizId; }
        
        public String getQuizTitle() { return quizTitle; }
        public void setQuizTitle(String quizTitle) { this.quizTitle = quizTitle; }
        
        public Integer getTotalQuestions() { return totalQuestions; }
        public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
        
        public Integer getTotalMarks() { return totalMarks; }
        public void setTotalMarks(Integer totalMarks) { this.totalMarks = totalMarks; }
        
        public Boolean getIsPublished() { return isPublished; }
        public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }
        
        public java.time.LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
    }
}
