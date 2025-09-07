package com.eduverse.courseservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for StudyNote entity
 * Contains comprehensive study note information and computed fields
 */
public class StudyNoteDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 500, message = "Title must not exceed 500 characters")
    private String title;

    @Size(max = 10000, message = "Content must not exceed 10000 characters")
    private String content;

    @NotNull(message = "Lesson ID is required")
    private Long lessonId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Size(max = 255, message = "User name must not exceed 255 characters")
    private String userName;

    private Boolean isPublic = false;

    private Boolean isFavorite = false;

    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;

    @Size(max = 1000, message = "Tags must not exceed 1000 characters")
    private String tags;

    @Pattern(regexp = "TEXT|IMAGE|VIDEO|LINK|DOCUMENT", message = "Note type must be TEXT, IMAGE, VIDEO, LINK, or DOCUMENT")
    private String noteType = "TEXT";

    private String fileUrl;

    @Size(max = 100, message = "File type must not exceed 100 characters")
    private String fileType;

    @Min(value = 0, message = "File size must be non-negative")
    private Long fileSize;

    @Min(value = 0, message = "View count must be non-negative")
    private Long viewCount = 0L;

    @Min(value = 0, message = "Like count must be non-negative")
    private Long likeCount = 0L;

    @Min(value = 0, message = "Share count must be non-negative")
    private Long shareCount = 0L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastAccessed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Computed fields
    private Boolean isRecent;
    private Boolean isPopular;
    private Boolean hasAttachment;
    private Long daysOld;
    private String shortContent;
    private String status;

    /**
     * Calculate computed fields based on current data
     */
    public void calculateComputedFields() {
        this.isRecent = calculateIsRecent();
        this.isPopular = calculateIsPopular();
        this.hasAttachment = calculateHasAttachment();
        this.daysOld = calculateDaysOld();
        this.shortContent = calculateShortContent();
        this.status = calculateStatus();
    }

    private Boolean calculateIsRecent() {
        if (createdAt == null) return false;
        return createdAt.isAfter(LocalDateTime.now().minusDays(7));
    }

    private Boolean calculateIsPopular() {
        return viewCount != null && viewCount > 100;
    }

    private Boolean calculateHasAttachment() {
        return fileUrl != null && !fileUrl.trim().isEmpty();
    }

    private Long calculateDaysOld() {
        if (createdAt == null) return 0L;
        return java.time.temporal.ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
    }

    private String calculateShortContent() {
        if (content == null) return "";
        return content.length() > 200 ? content.substring(0, 200) + "..." : content;
    }

    private String calculateStatus() {
        if (isRecent != null && isRecent) return "RECENT";
        if (isPopular != null && isPopular) return "POPULAR";
        if (Boolean.TRUE.equals(isFavorite)) return "FAVORITE";
        if (Boolean.TRUE.equals(isPublic)) return "PUBLIC";
        return "PRIVATE";
    }

    // Helper methods
    public String[] getTagsArray() {
        if (tags == null || tags.trim().isEmpty()) {
            return new String[0];
        }
        return tags.split(",");
    }

    public void setTagsFromArray(String[] tagsArray) {
        if (tagsArray == null || tagsArray.length == 0) {
            this.tags = "";
        } else {
            this.tags = String.join(",", tagsArray);
        }
    }

    public String getFileSizeFormatted() {
        if (fileSize == null || fileSize == 0) return "0 B";
        
        String[] units = {"B", "KB", "MB", "GB"};
        double size = fileSize.doubleValue();
        int unitIndex = 0;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return String.format("%.1f %s", size, units[unitIndex]);
    }

    public String getEngagementLevel() {
        long totalEngagement = (viewCount != null ? viewCount : 0) + 
                             (likeCount != null ? likeCount : 0) * 5 + 
                             (shareCount != null ? shareCount : 0) * 10;
        
        if (totalEngagement >= 1000) return "HIGH";
        if (totalEngagement >= 100) return "MEDIUM";
        return "LOW";
    }

    public Double getEngagementScore() {
        long views = viewCount != null ? viewCount : 0;
        long likes = likeCount != null ? likeCount : 0;
        long shares = shareCount != null ? shareCount : 0;
        
        if (views == 0) return 0.0;
        
        return (likes * 5.0 + shares * 10.0) / views;
    }

    public boolean isContentRich() {
        return (content != null && content.length() > 500) || 
               (hasAttachment != null && hasAttachment);
    }

    // Constructors
    public StudyNoteDTO() {}

    public StudyNoteDTO(Long id, String title, String content, Long lessonId, Long userId, String userName,
                        Boolean isPublic, Boolean isFavorite, String category, String tags, String noteType,
                        String fileUrl, String fileType, Long fileSize, Long viewCount, Long likeCount,
                        Long shareCount, LocalDateTime lastAccessed, LocalDateTime createdAt, LocalDateTime updatedAt,
                        Boolean isRecent, Boolean isPopular, Boolean hasAttachment, Long daysOld,
                        String shortContent, String status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.lessonId = lessonId;
        this.userId = userId;
        this.userName = userName;
        this.isPublic = isPublic;
        this.isFavorite = isFavorite;
        this.category = category;
        this.tags = tags;
        this.noteType = noteType;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.lastAccessed = lastAccessed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isRecent = isRecent;
        this.isPopular = isPopular;
        this.hasAttachment = hasAttachment;
        this.daysOld = daysOld;
        this.shortContent = shortContent;
        this.status = status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsRecent() {
        return isRecent;
    }

    public void setIsRecent(Boolean isRecent) {
        this.isRecent = isRecent;
    }

    public Boolean getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(Boolean isPopular) {
        this.isPopular = isPopular;
    }

    public Boolean getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(Boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public Long getDaysOld() {
        return daysOld;
    }

    public void setDaysOld(Long daysOld) {
        this.daysOld = daysOld;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
