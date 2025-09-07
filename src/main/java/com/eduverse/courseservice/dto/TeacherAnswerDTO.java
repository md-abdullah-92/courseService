package com.eduverse.courseservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TeacherAnswerDTO {
    
    private String id;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    @NotNull(message = "Teacher ID is required")
    private Integer teacherId;
    
    private String teacherName;
    
    private String teacherPhotoUrl;
    
    @NotNull(message = "Question ID is required")
    private String questionId;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // Constructors
    public TeacherAnswerDTO() {}
    
    public TeacherAnswerDTO(String id, String content, Integer teacherId, String teacherName, 
                           String teacherPhotoUrl, String questionId, LocalDateTime createdAt, 
                           LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherPhotoUrl = teacherPhotoUrl;
        this.questionId = questionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public Integer getTeacherId() { return teacherId; }
    public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }
    
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    
    public String getTeacherPhotoUrl() { return teacherPhotoUrl; }
    public void setTeacherPhotoUrl(String teacherPhotoUrl) { this.teacherPhotoUrl = teacherPhotoUrl; }
    
    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
