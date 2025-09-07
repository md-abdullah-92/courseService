package com.eduverse.courseservice.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_completion")
@EntityListeners(AuditingEntityListener.class)
public class LessonCompletion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "enrollment_id", nullable = false)
    private Long enrollmentId;
    
    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;
    
    @CreatedDate
    @Column(name = "completed_at", nullable = false, updatable = false)
    private LocalDateTime completedAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", insertable = false, updatable = false)
    private Enrollment enrollment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", insertable = false, updatable = false)
    private Lesson lesson;
    
    // Default constructor
    public LessonCompletion() {}
    
    // All args constructor  
    public LessonCompletion(Long id, Long enrollmentId, Long lessonId, LocalDateTime completedAt) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.lessonId = lessonId;
        this.completedAt = completedAt;
    }
    
    // Explicit getters for IDE compatibility
    public Long getId() { return id; }
    public Long getEnrollmentId() { return enrollmentId; }
    public Long getLessonId() { return lessonId; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public Enrollment getEnrollment() { return enrollment; }
    public Lesson getLesson() { return lesson; }
    
    // Explicit setters for IDE compatibility
    public void setId(Long id) { this.id = id; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }
}
