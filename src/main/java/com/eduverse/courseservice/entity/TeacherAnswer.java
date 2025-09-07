package com.eduverse.courseservice.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_answer")
@EntityListeners(AuditingEntityListener.class)
public class TeacherAnswer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;
    
    @Column(name = "teacher_name", nullable = false)
    private String teacherName;
    
    @Column(name = "teacher_photo_url")
    private String teacherPhotoUrl;
    
    @Column(name = "question_id", nullable = false)
    private String questionId;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private StudentQuestion question;
    
    // Constructors
    public TeacherAnswer() {}
    
    public TeacherAnswer(Long id, String content, Long teacherId, String teacherName, 
                        String teacherPhotoUrl, String questionId, LocalDateTime createdAt, 
                        LocalDateTime updatedAt, StudentQuestion question) {
        this.id = id;
        this.content = content;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherPhotoUrl = teacherPhotoUrl;
        this.questionId = questionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.question = question;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    
    public String getTeacherPhotoUrl() { return teacherPhotoUrl; }
    public void setTeacherPhotoUrl(String teacherPhotoUrl) { this.teacherPhotoUrl = teacherPhotoUrl; }
    
    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public StudentQuestion getQuestion() { return question; }
    public void setQuestion(StudentQuestion question) { this.question = question; }
}
