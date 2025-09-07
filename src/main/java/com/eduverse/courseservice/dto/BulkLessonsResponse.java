package com.eduverse.courseservice.dto;

import java.util.List;

public class BulkLessonsResponse {
    private String message;
    private BulkOperationSummary summary;
    private BulkOperationDetails details;
    
    // Default constructor
    public BulkLessonsResponse() {}
    
    // All args constructor
    public BulkLessonsResponse(String message, BulkOperationSummary summary, BulkOperationDetails details) {
        this.message = message;
        this.summary = summary;
        this.details = details;
    }
    
    // Explicit getters and setters for IDE compatibility
    public String getMessage() { return message; }
    public BulkOperationSummary getSummary() { return summary; }
    public BulkOperationDetails getDetails() { return details; }
    
    public void setMessage(String message) { this.message = message; }
    public void setSummary(BulkOperationSummary summary) { this.summary = summary; }
    public void setDetails(BulkOperationDetails details) { this.details = details; }
    
    public static class BulkOperationSummary {
        private int created;
        private int updated;
        private int deleted;
        
        // Default constructor
        public BulkOperationSummary() {}
        
        // All args constructor
        public BulkOperationSummary(int created, int updated, int deleted) {
            this.created = created;
            this.updated = updated;
            this.deleted = deleted;
        }
        
        // Explicit getters and setters for IDE compatibility
        public int getCreated() { return created; }
        public int getUpdated() { return updated; }
        public int getDeleted() { return deleted; }
        
        public void setCreated(int created) { this.created = created; }
        public void setUpdated(int updated) { this.updated = updated; }
        public void setDeleted(int deleted) { this.deleted = deleted; }
    }
    
    public static class BulkOperationDetails {
        private List<LessonDto> created;
        private List<LessonDto> updated;
        private List<DeletedLessonInfo> deleted;
        
        // Default constructor
        public BulkOperationDetails() {}
        
        // All args constructor
        public BulkOperationDetails(List<LessonDto> created, List<LessonDto> updated, List<DeletedLessonInfo> deleted) {
            this.created = created;
            this.updated = updated;
            this.deleted = deleted;
        }
        
        // Explicit getters and setters for IDE compatibility
        public List<LessonDto> getCreated() { return created; }
        public List<LessonDto> getUpdated() { return updated; }
        public List<DeletedLessonInfo> getDeleted() { return deleted; }
        
        public void setCreated(List<LessonDto> created) { this.created = created; }
        public void setUpdated(List<LessonDto> updated) { this.updated = updated; }
        public void setDeleted(List<DeletedLessonInfo> deleted) { this.deleted = deleted; }
    }
    
    public static class DeletedLessonInfo {
        private Long id;
        private String title;
        
        // Default constructor
        public DeletedLessonInfo() {}
        
        // All args constructor
        public DeletedLessonInfo(Long id, String title) {
            this.id = id;
            this.title = title;
        }
        
        // Explicit getters and setters for IDE compatibility
        public Long getId() { return id; }
        public String getTitle() { return title; }
        
        public void setId(Long id) { this.id = id; }
        public void setTitle(String title) { this.title = title; }
    }
}
