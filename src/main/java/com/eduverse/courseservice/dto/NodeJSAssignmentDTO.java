package com.eduverse.courseservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeJSAssignmentDTO {
    private Long id;
    private String title;
    private String description;
    
    @JsonProperty("teacherId")
    private Long teacherId;
    
    @JsonProperty("lessonId")
    private Long lessonId;

    // Constructors
    public NodeJSAssignmentDTO() {}

    public NodeJSAssignmentDTO(Long id, String title, String description, Long teacherId, Long lessonId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.teacherId = teacherId;
        this.lessonId = lessonId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }
}
