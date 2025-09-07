package com.eduverse.courseservice.dto;

import java.util.List;

public class BulkLessonsRequest {
    private List<LessonDto> lessons;
    
    // Default constructor
    public BulkLessonsRequest() {}
    
    // All args constructor
    public BulkLessonsRequest(List<LessonDto> lessons) {
        this.lessons = lessons;
    }
    
    // Explicit getter and setter for IDE compatibility
    public List<LessonDto> getLessons() { return lessons; }
    public void setLessons(List<LessonDto> lessons) { this.lessons = lessons; }
}
