package com.eduverse.courseservice.dto;

import java.time.LocalDateTime;

public class CartItemDTO {
    
    private Long id;
    private String studentId;
    private Long courseId;
    private LocalDateTime addedAt;
    private CourseDto course;
    
    // Constructors
    public CartItemDTO() {}
    
    public CartItemDTO(Long id, String studentId, Long courseId, LocalDateTime addedAt, CourseDto course) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.addedAt = addedAt;
        this.course = course;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    
    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
    
    public CourseDto getCourse() { return course; }
    public void setCourse(CourseDto course) { this.course = course; }
}
