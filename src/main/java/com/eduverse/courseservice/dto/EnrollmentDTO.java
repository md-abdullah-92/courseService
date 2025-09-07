package com.eduverse.courseservice.dto;

import java.time.LocalDateTime;

public class EnrollmentDTO {
    private Long id;
    private String studentId;
    private Long courseId;
    private LocalDateTime enrollmentDate;
    private Double progress;
    private LocalDateTime lastAccessed;
    
    // For response with course details
    private String courseTitle;
    private String courseDescription;
    private String instructorId;
    private Double averageRating;
    
    // Constructors
    public EnrollmentDTO() {}
    
    public EnrollmentDTO(Long id, String studentId, Long courseId, LocalDateTime enrollmentDate, 
                        Double progress, LocalDateTime lastAccessed, String courseTitle, 
                        String courseDescription, String instructorId, Double averageRating) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.progress = progress;
        this.lastAccessed = lastAccessed;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.instructorId = instructorId;
        this.averageRating = averageRating;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDateTime enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    
    public Double getProgress() { return progress; }
    public void setProgress(Double progress) { this.progress = progress; }
    
    public LocalDateTime getLastAccessed() { return lastAccessed; }
    public void setLastAccessed(LocalDateTime lastAccessed) { this.lastAccessed = lastAccessed; }
    
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
    
    public String getCourseDescription() { return courseDescription; }
    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }
    
    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
}
