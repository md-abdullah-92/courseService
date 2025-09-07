package com.eduverse.courseservice.dto;

public class CourseOutcomeDto {
    
    private Long id;
    private String outcome;
    private Long courseId;
    
    // Default constructor
    public CourseOutcomeDto() {}
    
    // All args constructor
    public CourseOutcomeDto(Long id, String outcome, Long courseId) {
        this.id = id;
        this.outcome = outcome;
        this.courseId = courseId;
    }
    
    // Explicit getters for IDE compatibility
    public Long getId() { return id; }
    public String getOutcome() { return outcome; }
    public Long getCourseId() { return courseId; }
    
    // Explicit setters for IDE compatibility
    public void setId(Long id) { this.id = id; }
    public void setOutcome(String outcome) { this.outcome = outcome; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
