package com.eduverse.courseservice.dto;

import com.eduverse.courseservice.enums.DifficultyLevel;
import com.eduverse.courseservice.enums.QuestionType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for Quiz entities
 * Handles quiz data transfer with questions and options
 */
public class QuizDTO {
    
    private Long id;
    private String title;
    private String description;
    private Integer timeLimit;
    private Integer passingScore;
    private Boolean isPublished;
    private Integer duration;
    private Integer marks;
    private Long lessonId;
    private String lessonTitle;
    private List<QuestionDTO> questions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Computed fields
    private Integer totalQuestions;
    private Integer totalMarks;
    
    // Constructors
    public QuizDTO() {}
    
    public QuizDTO(Long id, String title, String description, Integer timeLimit, Integer passingScore, 
                   Boolean isPublished, Integer duration, Integer marks, Long lessonId, String lessonTitle, 
                   List<QuestionDTO> questions, LocalDateTime createdAt, LocalDateTime updatedAt, 
                   Integer totalQuestions, Integer totalMarks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timeLimit = timeLimit;
        this.passingScore = passingScore;
        this.isPublished = isPublished;
        this.duration = duration;
        this.marks = marks;
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.questions = questions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalQuestions = totalQuestions;
        this.totalMarks = totalMarks;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getTimeLimit() { return timeLimit; }
    public void setTimeLimit(Integer timeLimit) { this.timeLimit = timeLimit; }
    
    public Integer getPassingScore() { return passingScore; }
    public void setPassingScore(Integer passingScore) { this.passingScore = passingScore; }
    
    public Boolean getIsPublished() { return isPublished; }
    public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Integer getMarks() { return marks; }
    public void setMarks(Integer marks) { this.marks = marks; }
    
    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }
    
    public String getLessonTitle() { return lessonTitle; }
    public void setLessonTitle(String lessonTitle) { this.lessonTitle = lessonTitle; }
    
    public List<QuestionDTO> getQuestions() { return questions; }
    public void setQuestions(List<QuestionDTO> questions) { this.questions = questions; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
    
    public Integer getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Integer totalMarks) { this.totalMarks = totalMarks; }
    
    public static class QuestionDTO {
        private Long id;
        private String question;
        private String correctAnswer;
        private String explanation;
        private DifficultyLevel difficulty;
        private QuestionType type;
        private Long quizId;
        private List<OptionDTO> options;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        // Computed field
        private Integer totalOptions;
        
        // Constructors
        public QuestionDTO() {}
        
        public QuestionDTO(Long id, String question, String correctAnswer, String explanation, 
                          DifficultyLevel difficulty, QuestionType type, Long quizId, 
                          List<OptionDTO> options, LocalDateTime createdAt, LocalDateTime updatedAt, 
                          Integer totalOptions) {
            this.id = id;
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.explanation = explanation;
            this.difficulty = difficulty;
            this.type = type;
            this.quizId = quizId;
            this.options = options;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.totalOptions = totalOptions;
        }
        
        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        
        public String getCorrectAnswer() { return correctAnswer; }
        public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
        
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
        
        public DifficultyLevel getDifficulty() { return difficulty; }
        public void setDifficulty(DifficultyLevel difficulty) { this.difficulty = difficulty; }
        
        public QuestionType getType() { return type; }
        public void setType(QuestionType type) { this.type = type; }
        
        public Long getQuizId() { return quizId; }
        public void setQuizId(Long quizId) { this.quizId = quizId; }
        
        public List<OptionDTO> getOptions() { return options; }
        public void setOptions(List<OptionDTO> options) { this.options = options; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
        
        public Integer getTotalOptions() { return totalOptions; }
        public void setTotalOptions(Integer totalOptions) { this.totalOptions = totalOptions; }
    }
    
    public static class OptionDTO {
        private Long id;
        private String text;
        private Boolean isCorrect;
        private Long questionId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        // Constructors
        public OptionDTO() {}
        
        public OptionDTO(Long id, String text, Boolean isCorrect, Long questionId, 
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.text = text;
            this.isCorrect = isCorrect;
            this.questionId = questionId;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
        
        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        
        public Boolean getIsCorrect() { return isCorrect; }
        public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
        
        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
    
    // Helper methods for computed fields
    public void calculateTotalQuestions() {
        this.totalQuestions = questions != null ? questions.size() : 0;
    }
    
    public void calculateTotalMarks() {
        this.totalMarks = marks != null ? marks : 
                         (questions != null ? questions.size() : 0);
    }
}
