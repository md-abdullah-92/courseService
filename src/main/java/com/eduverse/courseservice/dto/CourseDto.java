package com.eduverse.courseservice.dto;

import com.eduverse.courseservice.enums.CourseLevel;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;


public class CourseDto {
    
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive")
    private Double price;
    
    private String coverPhotoUrl;
    
    @NotNull(message = "Level is required")
    private CourseLevel level;
    
    private String topic;
    
    @NotBlank(message = "Instructor ID is required")
    private String instructorId;
    
    private Double averageRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Nested DTOs for relationships will be defined separately
    // private List<CourseOutcomeDto> outcomes;
    // private List<LessonDto> lessons;
    private Integer enrollmentCount;
    private Integer reviewCount;
    
    // Constructors
    public CourseDto() {}
    
    public CourseDto(Long id, String title, String description, Double price, String coverPhotoUrl, 
                    CourseLevel level, String topic, String instructorId, Double averageRating, 
                    LocalDateTime createdAt, LocalDateTime updatedAt, Integer enrollmentCount, Integer reviewCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.coverPhotoUrl = coverPhotoUrl;
        this.level = level;
        this.topic = topic;
        this.instructorId = instructorId;
        this.averageRating = averageRating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.enrollmentCount = enrollmentCount;
        this.reviewCount = reviewCount;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getCoverPhotoUrl() { return coverPhotoUrl; }
    public void setCoverPhotoUrl(String coverPhotoUrl) { this.coverPhotoUrl = coverPhotoUrl; }
    
    public CourseLevel getLevel() { return level; }
    public void setLevel(CourseLevel level) { this.level = level; }
    
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    
    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Integer getEnrollmentCount() { return enrollmentCount; }
    public void setEnrollmentCount(Integer enrollmentCount) { this.enrollmentCount = enrollmentCount; }
    
    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
}
