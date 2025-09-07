package com.eduverse.courseservice.dto;

import java.time.LocalDateTime;

/**
 * DTO for Assignment entities
 * Handles assignment data transfer with lesson information
 */
public class AssignmentDTO {
    
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private String teacherName;
    private LocalDateTime dueDate;
    private Integer maxMarks;
    private Boolean isPublished;
    private String instructions;
    private Long lessonId;
    private String lessonTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Computed fields
    private Boolean isOverdue;
    private Boolean isActive;
    private Long daysUntilDue;
    private String status;
    
    public AssignmentDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    
    public Integer getMaxMarks() { return maxMarks; }
    public void setMaxMarks(Integer maxMarks) { this.maxMarks = maxMarks; }
    
    public Boolean getIsPublished() { return isPublished; }
    public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }
    
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    
    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }
    
    public String getLessonTitle() { return lessonTitle; }
    public void setLessonTitle(String lessonTitle) { this.lessonTitle = lessonTitle; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Boolean getIsOverdue() { return isOverdue; }
    public void setIsOverdue(Boolean isOverdue) { this.isOverdue = isOverdue; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public Long getDaysUntilDue() { return daysUntilDue; }
    public void setDaysUntilDue(Long daysUntilDue) { this.daysUntilDue = daysUntilDue; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    /**
     * Calculate computed fields based on assignment data
     */
    public void calculateComputedFields() {
        LocalDateTime now = LocalDateTime.now();
        
        // Calculate isOverdue
        this.isOverdue = (dueDate != null && dueDate.isBefore(now));
        
        // Calculate isActive (published and not overdue)
        this.isActive = (isPublished != null && isPublished && (dueDate == null || dueDate.isAfter(now)));
        
        // Calculate days until due
        if (dueDate != null) {
            long daysUntil = java.time.temporal.ChronoUnit.DAYS.between(now.toLocalDate(), dueDate.toLocalDate());
            this.daysUntilDue = daysUntil;
        } else {
            this.daysUntilDue = null;
        }
        
        // Calculate status
        if (isPublished == null || !isPublished) {
            this.status = "Draft";
        } else if (this.isOverdue) {
            this.status = "Overdue";
        } else if (dueDate != null && daysUntilDue != null && daysUntilDue <= 7) {
            this.status = "Due Soon";
        } else {
            this.status = "Active";
        }
    }
}
