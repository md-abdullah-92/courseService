package com.eduverse.courseservice.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_question")
@EntityListeners(AuditingEntityListener.class)
public class StudentQuestion {
    
    @Id
    private String id;
    
    @Column(name = "student_name", nullable = false)
    private String studentName;
    
    @Column(name = "student_photo_url")
    private String studentPhotoUrl;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "student_id", nullable = false)
    private Integer studentId;
    
    @Column(name = "course_id", nullable = false)
    private Long courseId;
    
    @Column(name = "is_answered", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isAnswered = false;
    
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
    
    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TeacherAnswer answer;
    
    // Constructors
    public StudentQuestion() {}
    
    public StudentQuestion(String id, String studentName, String studentPhotoUrl, String title, 
                          String content, Integer studentId, Long courseId, Boolean isAnswered, 
                          LocalDateTime createdAt, LocalDateTime updatedAt, Course course, 
                          TeacherAnswer answer) {
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
        this.course = course;
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
    
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    
    public TeacherAnswer getAnswer() { return answer; }
    public void setAnswer(TeacherAnswer answer) { this.answer = answer; }
}
