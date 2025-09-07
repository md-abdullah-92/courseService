package com.eduverse.courseservice.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lesson")
@EntityListeners(AuditingEntityListener.class)
public class Lesson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "video_url", columnDefinition = "TEXT")
    private String videoUrl;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;
    
    @Column(name = "course_id", nullable = false)
    private Long courseId;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;
    
    // Default constructor
    public Lesson() {}
    
    // All args constructor
    public Lesson(Long id, String title, String description, String videoUrl, String notes, 
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
    public Course getCourse() { return course; }
    
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
    public void setCourse(Course course) { this.course = course; }
    
    // @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Quiz> quizzes;
    
    // @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Assignment> assignments;
    
    // @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<StudyNote> studyNotes;
    
    // @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<LessonCompletion> lessonCompletions;
}
