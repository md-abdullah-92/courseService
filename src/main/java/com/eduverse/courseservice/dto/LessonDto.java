package com.eduverse.courseservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class LessonDto {
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    private String videoUrl;
    
    @Size(max = 5000, message = "Notes too long")
    private String notes;
    
    private Integer orderIndex;
    private Long courseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public LessonDto() {}

    public LessonDto(Long id, String title, String description, String videoUrl, String notes, 
                     Integer orderIndex, Long courseId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.notes = notes;
        this.orderIndex = orderIndex;
        this.courseId = courseId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Explicit getters for IDE compatibility
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getVideoUrl() { return videoUrl; }
    public String getNotes() { return notes; }
    public Integer getOrderIndex() { return orderIndex; }
    public Long getCourseId() { return courseId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    // Explicit setters for IDE compatibility
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
