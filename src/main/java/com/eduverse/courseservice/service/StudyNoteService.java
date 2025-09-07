package com.eduverse.courseservice.service;

import com.eduverse.courseservice.dto.StudyNoteDTO;
import com.eduverse.courseservice.entity.StudyNote;
import com.eduverse.courseservice.entity.Lesson;
import com.eduverse.courseservice.exception.ResourceNotFoundException;
import com.eduverse.courseservice.mapper.StudyNoteMapper;
import com.eduverse.courseservice.repository.StudyNoteRepository;
import com.eduverse.courseservice.repository.LessonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for StudyNote operations
 * Provides comprehensive study note management functionality
 */
@Service
@Transactional
public class StudyNoteService {

    private static final Logger log = LoggerFactory.getLogger(StudyNoteService.class);
    private final StudyNoteRepository studyNoteRepository;
    private final LessonRepository lessonRepository;
    private final StudyNoteMapper studyNoteMapper;

    public StudyNoteService(StudyNoteRepository studyNoteRepository, LessonRepository lessonRepository, 
                           StudyNoteMapper studyNoteMapper) {
        this.studyNoteRepository = studyNoteRepository;
        this.lessonRepository = lessonRepository;
        this.studyNoteMapper = studyNoteMapper;
    }

    /**
     * Create a new study note
     */
    public StudyNoteDTO createStudyNote(StudyNoteDTO studyNoteDTO) {
        log.info("Creating new study note: {}", studyNoteDTO.getTitle());
        
        // Validate lesson exists
        Lesson lesson = lessonRepository.findById(studyNoteDTO.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + studyNoteDTO.getLessonId()));
        
        // Check for duplicate note title for the same user and lesson
        if (studyNoteRepository.existsByTitleAndUserId(studyNoteDTO.getTitle(), studyNoteDTO.getUserId())) {
            throw new IllegalArgumentException("Study note with title '" + studyNoteDTO.getTitle() + "' already exists for this user");
        }
        
        StudyNote studyNote = studyNoteMapper.toEntity(studyNoteDTO);
        
        // Set default values if not provided
        if (studyNote.getIsPublic() == null) {
            studyNote.setIsPublic(false);
        }
        if (studyNote.getIsFavorite() == null) {
            studyNote.setIsFavorite(false);
        }
        if (studyNote.getNoteType() == null) {
            studyNote.setNoteType("TEXT");
        }
        
        StudyNote savedStudyNote = studyNoteRepository.save(studyNote);
        
        StudyNoteDTO result = studyNoteMapper.toDTO(savedStudyNote);
        result.calculateComputedFields();
        
        log.info("Study note created successfully with id: {}", savedStudyNote.getId());
        return result;
    }

    /**
     * Get all study notes
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> getAllStudyNotes() {
        log.info("Fetching all study notes");
        
        List<StudyNote> studyNotes = studyNoteRepository.findAll();
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get study note by ID with view count increment
     */
    public StudyNoteDTO getStudyNoteById(Long studyNoteId) {
        log.info("Fetching study note with id: {}", studyNoteId);
        
        StudyNote studyNote = studyNoteRepository.findById(studyNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Study note not found with id: " + studyNoteId));
        
        // Increment view count
        studyNote.incrementViewCount();
        studyNoteRepository.save(studyNote);
        
        StudyNoteDTO result = studyNoteMapper.toDTO(studyNote);
        result.calculateComputedFields();
        
        return result;
    }

    /**
     * Get study notes by lesson ID
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> getStudyNotesByLessonId(Long lessonId) {
        log.info("Fetching study notes for lesson id: {}", lessonId);
        
        // Validate lesson exists
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson not found with id: " + lessonId);
        }
        
        List<StudyNote> studyNotes = studyNoteRepository.findByLessonId(lessonId);
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get study notes by user ID
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> getStudyNotesByUserId(Long userId) {
        log.info("Fetching study notes for user id: {}", userId);
        
        List<StudyNote> studyNotes = studyNoteRepository.findByUserId(userId);
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Update study note
     */
    public StudyNoteDTO updateStudyNote(Long studyNoteId, StudyNoteDTO studyNoteDTO) {
        log.info("Updating study note with id: {}", studyNoteId);
        
        StudyNote existingStudyNote = studyNoteRepository.findById(studyNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Study note not found with id: " + studyNoteId));
        
        // Update study note properties
        if (studyNoteDTO.getTitle() != null) {
            existingStudyNote.setTitle(studyNoteDTO.getTitle());
        }
        if (studyNoteDTO.getContent() != null) {
            existingStudyNote.setContent(studyNoteDTO.getContent());
        }
        if (studyNoteDTO.getIsPublic() != null) {
            existingStudyNote.setIsPublic(studyNoteDTO.getIsPublic());
        }
        if (studyNoteDTO.getIsFavorite() != null) {
            existingStudyNote.setIsFavorite(studyNoteDTO.getIsFavorite());
        }
        if (studyNoteDTO.getCategory() != null) {
            existingStudyNote.setCategory(studyNoteDTO.getCategory());
        }
        if (studyNoteDTO.getTags() != null) {
            existingStudyNote.setTags(studyNoteDTO.getTags());
        }
        if (studyNoteDTO.getNoteType() != null) {
            existingStudyNote.setNoteType(studyNoteDTO.getNoteType());
        }
        if (studyNoteDTO.getFileUrl() != null) {
            existingStudyNote.setFileUrl(studyNoteDTO.getFileUrl());
        }
        if (studyNoteDTO.getFileType() != null) {
            existingStudyNote.setFileType(studyNoteDTO.getFileType());
        }
        if (studyNoteDTO.getFileSize() != null) {
            existingStudyNote.setFileSize(studyNoteDTO.getFileSize());
        }
        if (studyNoteDTO.getUserName() != null) {
            existingStudyNote.setUserName(studyNoteDTO.getUserName());
        }
        
        StudyNote savedStudyNote = studyNoteRepository.save(existingStudyNote);
        
        StudyNoteDTO result = studyNoteMapper.toDTO(savedStudyNote);
        result.calculateComputedFields();
        
        log.info("Study note updated successfully");
        return result;
    }

    /**
     * Delete study note
     */
    public void deleteStudyNote(Long studyNoteId) {
        log.info("Deleting study note with id: {}", studyNoteId);
        
        StudyNote studyNote = studyNoteRepository.findById(studyNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Study note not found with id: " + studyNoteId));
        
        studyNoteRepository.delete(studyNote);
        log.info("Study note deleted successfully");
    }

    /**
     * Get public study notes
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> getPublicStudyNotes() {
        log.info("Fetching public study notes");
        
        List<StudyNote> studyNotes = studyNoteRepository.findByIsPublicTrue();
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get favorite study notes by user ID
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> getFavoriteStudyNotesByUserId(Long userId) {
        log.info("Fetching favorite study notes for user id: {}", userId);
        
        List<StudyNote> studyNotes = studyNoteRepository.findByUserIdAndIsFavoriteTrue(userId);
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Search study notes by title or content
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> searchStudyNotes(String searchTerm) {
        log.info("Searching study notes with term: {}", searchTerm);
        
        List<StudyNote> studyNotes = studyNoteRepository.searchByTitleOrContent(searchTerm);
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get recent study notes (last 7 days)
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> getRecentStudyNotes() {
        log.info("Fetching recent study notes");
        
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        List<StudyNote> studyNotes = studyNoteRepository.findRecentNotes(weekAgo);
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get popular study notes
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> getPopularStudyNotes() {
        log.info("Fetching popular study notes");
        
        List<StudyNote> studyNotes = studyNoteRepository.findPopularNotes(100L);
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Like/unlike study note
     */
    public StudyNoteDTO toggleLikeStudyNote(Long studyNoteId, boolean like) {
        log.info("Toggling like for study note id: {} to {}", studyNoteId, like);
        
        StudyNote studyNote = studyNoteRepository.findById(studyNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Study note not found with id: " + studyNoteId));
        
        if (like) {
            studyNote.incrementLikeCount();
        } else {
            studyNote.decrementLikeCount();
        }
        
        StudyNote savedStudyNote = studyNoteRepository.save(studyNote);
        
        StudyNoteDTO result = studyNoteMapper.toDTO(savedStudyNote);
        result.calculateComputedFields();
        
        return result;
    }

    /**
     * Share study note
     */
    public StudyNoteDTO shareStudyNote(Long studyNoteId) {
        log.info("Sharing study note id: {}", studyNoteId);
        
        StudyNote studyNote = studyNoteRepository.findById(studyNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Study note not found with id: " + studyNoteId));
        
        studyNote.incrementShareCount();
        StudyNote savedStudyNote = studyNoteRepository.save(studyNote);
        
        StudyNoteDTO result = studyNoteMapper.toDTO(savedStudyNote);
        result.calculateComputedFields();
        
        return result;
    }

    /**
     * Get study notes with filters
     */
    @Transactional(readOnly = true)
    public List<StudyNoteDTO> getStudyNotesWithFilters(Long lessonId, Long userId, String category, String noteType, Boolean isPublic) {
        log.info("Fetching study notes with filters");
        
        List<StudyNote> studyNotes = studyNoteRepository.findByFilters(lessonId, userId, category, noteType, isPublic);
        return studyNotes.stream()
                .map(studyNote -> {
                    StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get paginated study notes
     */
    @Transactional(readOnly = true)
    public Page<StudyNoteDTO> getPaginatedStudyNotes(Pageable pageable) {
        log.info("Fetching paginated study notes");
        
        Page<StudyNote> studyNotePage = studyNoteRepository.findAll(pageable);
        return studyNotePage.map(studyNote -> {
            StudyNoteDTO dto = studyNoteMapper.toDTO(studyNote);
            dto.calculateComputedFields();
            return dto;
        });
    }

    /**
     * Get study note statistics
     */
    @Transactional(readOnly = true)
    public StudyNoteStatisticsDTO getStudyNoteStatistics(Long studyNoteId) {
        StudyNote studyNote = studyNoteRepository.findById(studyNoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Study note not found with id: " + studyNoteId));
        
        StudyNoteStatisticsDTO stats = new StudyNoteStatisticsDTO();
        stats.setStudyNoteId(studyNoteId);
        stats.setTitle(studyNote.getTitle());
        stats.setViewCount(studyNote.getViewCount());
        stats.setLikeCount(studyNote.getLikeCount());
        stats.setShareCount(studyNote.getShareCount());
        stats.setIsPublic(studyNote.getIsPublic());
        stats.setIsRecent(studyNote.isRecent());
        stats.setIsPopular(studyNote.isPopular());
        stats.setCreatedAt(studyNote.getCreatedAt());
        stats.setLastAccessed(studyNote.getLastAccessed());
        
        return stats;
    }
    
    /**
     * Inner class for study note statistics
     */
    public static class StudyNoteStatisticsDTO {
        private Long studyNoteId;
        private String title;
        private Long viewCount;
        private Long likeCount;
        private Long shareCount;
        private Boolean isPublic;
        private Boolean isRecent;
        private Boolean isPopular;
        private LocalDateTime createdAt;
        private LocalDateTime lastAccessed;

        // Constructors
        public StudyNoteStatisticsDTO() {}

        public StudyNoteStatisticsDTO(Long studyNoteId, String title, Long viewCount, Long likeCount, 
                                     Long shareCount, Boolean isPublic, Boolean isRecent, Boolean isPopular, 
                                     LocalDateTime createdAt, LocalDateTime lastAccessed) {
            this.studyNoteId = studyNoteId;
            this.title = title;
            this.viewCount = viewCount;
            this.likeCount = likeCount;
            this.shareCount = shareCount;
            this.isPublic = isPublic;
            this.isRecent = isRecent;
            this.isPopular = isPopular;
            this.createdAt = createdAt;
            this.lastAccessed = lastAccessed;
        }

        // Getters and Setters
        public Long getStudyNoteId() {
            return studyNoteId;
        }

        public void setStudyNoteId(Long studyNoteId) {
            this.studyNoteId = studyNoteId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public Boolean getIsPublic() {
            return isPublic;
        }

        public void setIsPublic(Boolean isPublic) {
            this.isPublic = isPublic;
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

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getLastAccessed() {
            return lastAccessed;
        }

        public void setLastAccessed(LocalDateTime lastAccessed) {
            this.lastAccessed = lastAccessed;
        }
    }
}
