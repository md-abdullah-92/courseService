package com.eduverse.courseservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class StudentQuestionDTO {
    
    private String id;
    
    @NotBlank(message = "Student name is required")
    private String studentName;
    
    private String studentPhotoUrl;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    @NotNull(message = "Student ID is required")
    private Integer studentId;
    
    @NotNull(message = "Course ID is required")
    private Long courseId;
    
    private Boolean isAnswered = false;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // Include the answer if available
    private TeacherAnswerDTO answer;
    
    // Constructors
    public StudentQuestionDTO() {}
    
    public StudentQuestionDTO(String id, String studentName, String studentPhotoUrl, String title, 
                             String content, Integer studentId, Long courseId, Boolean isAnswered, 
                             LocalDateTime createdAt, LocalDateTime updatedAt, TeacherAnswerDTO answer) {
        this.id = id;
        this.studentName = studentName;
        this.studentPhotoUrl = studentPhotoUrl;
        this.title = title;
        this.content = content;
        this.studentId = studentId;
        this.courseId = courseId;
        this.isAnswered = isAnswered;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.answer = answer;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getStudentPhotoUrl() { return studentPhotoUrl; }
    public void setStudentPhotoUrl(String studentPhotoUrl) { this.studentPhotoUrl = studentPhotoUrl; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    
    public Boolean getIsAnswered() { return isAnswered; }
    public void setIsAnswered(Boolean isAnswered) { this.isAnswered = isAnswered; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public TeacherAnswerDTO getAnswer() { return answer; }
    public void setAnswer(TeacherAnswerDTO answer) { this.answer = answer; }
}
