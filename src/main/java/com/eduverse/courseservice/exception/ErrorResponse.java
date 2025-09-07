package com.eduverse.courseservice.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private int status;
    
    // Default constructor
    public ErrorResponse() {}
    
    // All args constructor
    public ErrorResponse(String message, String details, LocalDateTime timestamp, int status) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
        this.status = status;
    }
    
    // Custom constructor
    public ErrorResponse(String message, String details, int status) {
        this.message = message;
        this.details = details;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and setters
    public String getMessage() { return message; }
    public String getDetails() { return details; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    
    public void setMessage(String message) { this.message = message; }
    public void setDetails(String details) { this.details = details; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setStatus(int status) { this.status = status; }
}
