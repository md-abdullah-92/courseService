package com.eduverse.courseservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_outcome")
public class CourseOutcome {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String outcome;
    
    @Column(name = "course_id", nullable = false)
    private Long courseId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;
    
    // Default constructor
    public CourseOutcome() {}
    
    // All args constructor
    public CourseOutcome(Long id, String outcome, Long courseId) {
        this.id = id;
        this.outcome = outcome;
        this.courseId = courseId;
    }
    
    // Explicit getters for IDE compatibility
    public Long getId() { return id; }
    public String getOutcome() { return outcome; }
    public Long getCourseId() { return courseId; }
    public Course getCourse() { return course; }
    
    // Explicit setters for IDE compatibility
    public void setId(Long id) { this.id = id; }
    public void setOutcome(String outcome) { this.outcome = outcome; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public void setCourse(Course course) { this.course = course; }
}
