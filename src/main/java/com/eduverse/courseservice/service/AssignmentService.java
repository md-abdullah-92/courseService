package com.eduverse.courseservice.service;

import com.eduverse.courseservice.dto.AssignmentDTO;
import com.eduverse.courseservice.entity.Assignment;
import com.eduverse.courseservice.entity.Lesson;
import com.eduverse.courseservice.exception.ResourceNotFoundException;
import com.eduverse.courseservice.mapper.AssignmentMapper;
import com.eduverse.courseservice.repository.AssignmentRepository;
import com.eduverse.courseservice.repository.LessonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Assignment operations
 * Provides comprehensive assignment management functionality
 */
@Service
@Transactional
public class AssignmentService {

    private static final Logger log = LoggerFactory.getLogger(AssignmentService.class);
    
    private final AssignmentRepository assignmentRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentMapper assignmentMapper;

    public AssignmentService(AssignmentRepository assignmentRepository, 
                           LessonRepository lessonRepository, 
                           AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.lessonRepository = lessonRepository;
        this.assignmentMapper = assignmentMapper;
    }

    /**
     * Create a new assignment
     */
    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        log.info("Creating new assignment: {}", assignmentDTO.getTitle());
        
        // Validate lesson exists
        Lesson lesson = lessonRepository.findById(assignmentDTO.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + assignmentDTO.getLessonId()));
        
        // Check for duplicate assignment title in the same lesson
        if (assignmentRepository.existsByLessonIdAndTitle(assignmentDTO.getLessonId(), assignmentDTO.getTitle())) {
            throw new IllegalArgumentException("Assignment with title '" + assignmentDTO.getTitle() + "' already exists in this lesson");
        }
        
        Assignment assignment = assignmentMapper.toEntity(assignmentDTO);
        assignment.setLesson(lesson);
        
        // Set default values if not provided
        if (assignment.getIsPublished() == null) {
            assignment.setIsPublished(false);
        }
        
        Assignment savedAssignment = assignmentRepository.save(assignment);
        
        AssignmentDTO result = assignmentMapper.toDTO(savedAssignment);
        result.calculateComputedFields();
        
        log.info("Assignment created successfully with id: {}", savedAssignment.getId());
        return result;
    }

    /**
     * Get all assignments
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAllAssignments() {
        log.info("Fetching all assignments");
        
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream()
                .map(assignment -> {
                    AssignmentDTO dto = assignmentMapper.toDTO(assignment);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get assignment by ID
     */
    @Transactional(readOnly = true)
    public AssignmentDTO getAssignmentById(Long assignmentId) {
        log.info("Fetching assignment with id: {}", assignmentId);
        
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));
        
        AssignmentDTO result = assignmentMapper.toDTO(assignment);
        result.calculateComputedFields();
        
        return result;
    }

    /**
     * Get assignments by lesson ID
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByLessonId(Long lessonId) {
        log.info("Fetching assignments for lesson id: {}", lessonId);
        
        // Validate lesson exists
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson not found with id: " + lessonId);
        }
        
        List<Assignment> assignments = assignmentRepository.findByLessonId(lessonId);
        return assignments.stream()
                .map(assignment -> {
                    AssignmentDTO dto = assignmentMapper.toDTO(assignment);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get assignments by teacher ID
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByTeacherId(Long teacherId) {
        log.info("Fetching assignments for teacher id: {}", teacherId);
        
        List<Assignment> assignments = assignmentRepository.findByTeacherId(teacherId);
        return assignments.stream()
                .map(assignment -> {
                    AssignmentDTO dto = assignmentMapper.toDTO(assignment);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Update assignment
     */
    public AssignmentDTO updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO) {
        log.info("Updating assignment with id: {}", assignmentId);
        
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));
        
        // Update assignment properties
        if (assignmentDTO.getTitle() != null) {
            existingAssignment.setTitle(assignmentDTO.getTitle());
        }
        if (assignmentDTO.getDescription() != null) {
            existingAssignment.setDescription(assignmentDTO.getDescription());
        }
        if (assignmentDTO.getDueDate() != null) {
            existingAssignment.setDueDate(assignmentDTO.getDueDate());
        }
        if (assignmentDTO.getMaxMarks() != null) {
            existingAssignment.setMaxMarks(assignmentDTO.getMaxMarks());
        }
        if (assignmentDTO.getIsPublished() != null) {
            existingAssignment.setIsPublished(assignmentDTO.getIsPublished());
        }
        if (assignmentDTO.getInstructions() != null) {
            existingAssignment.setInstructions(assignmentDTO.getInstructions());
        }
        if (assignmentDTO.getTeacherName() != null) {
            existingAssignment.setTeacherName(assignmentDTO.getTeacherName());
        }
        
        Assignment savedAssignment = assignmentRepository.save(existingAssignment);
        
        AssignmentDTO result = assignmentMapper.toDTO(savedAssignment);
        result.calculateComputedFields();
        
        log.info("Assignment updated successfully");
        return result;
    }

    /**
     * Delete assignment
     */
    public void deleteAssignment(Long assignmentId) {
        log.info("Deleting assignment with id: {}", assignmentId);
        
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));
        
        assignmentRepository.delete(assignment);
        log.info("Assignment deleted successfully");
    }

    /**
     * Get published assignments by lesson ID
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getPublishedAssignmentsByLessonId(Long lessonId) {
        log.info("Fetching published assignments for lesson id: {}", lessonId);
        
        List<Assignment> assignments = assignmentRepository.findPublishedByLessonId(lessonId);
        return assignments.stream()
                .map(assignment -> {
                    AssignmentDTO dto = assignmentMapper.toDTO(assignment);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get active assignments (published and not overdue)
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getActiveAssignments() {
        log.info("Fetching active assignments");
        
        List<Assignment> assignments = assignmentRepository.findActiveAssignments(LocalDateTime.now());
        return assignments.stream()
                .map(assignment -> {
                    AssignmentDTO dto = assignmentMapper.toDTO(assignment);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get overdue assignments
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getOverdueAssignments() {
        log.info("Fetching overdue assignments");
        
        List<Assignment> assignments = assignmentRepository.findOverdueAssignments(LocalDateTime.now());
        return assignments.stream()
                .map(assignment -> {
                    AssignmentDTO dto = assignmentMapper.toDTO(assignment);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get assignments due within specific timeframe
     */
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsDueSoon(int days) {
        log.info("Fetching assignments due within {} days", days);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureTime = now.plusDays(days);
        
        List<Assignment> assignments = assignmentRepository.findAssignmentsDueBetween(now, futureTime);
        return assignments.stream()
                .map(assignment -> {
                    AssignmentDTO dto = assignmentMapper.toDTO(assignment);
                    dto.calculateComputedFields();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get assignment statistics
     */
    @Transactional(readOnly = true)
    public AssignmentStatisticsDTO getAssignmentStatistics(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));
        
        AssignmentStatisticsDTO stats = new AssignmentStatisticsDTO();
        stats.setAssignmentId(assignmentId);
        stats.setAssignmentTitle(assignment.getTitle());
        stats.setMaxMarks(assignment.getMaxMarks());
        stats.setIsPublished(assignment.getIsPublished());
        stats.setIsOverdue(assignment.isOverdue());
        stats.setIsActive(assignment.isActive());
        stats.setCreatedAt(assignment.getCreatedAt());
        stats.setDueDate(assignment.getDueDate());
        
        return stats;
    }
    
    /**
     * Inner class for assignment statistics
     */
    public static class AssignmentStatisticsDTO {
        private Long assignmentId;
        private String assignmentTitle;
        private Integer maxMarks;
        private Boolean isPublished;
        private Boolean isOverdue;
        private Boolean isActive;
        private LocalDateTime createdAt;
        private LocalDateTime dueDate;
        
        public AssignmentStatisticsDTO() {}
        
        public AssignmentStatisticsDTO(Long assignmentId, String assignmentTitle, Integer maxMarks, 
                                     Boolean isPublished, Boolean isOverdue, Boolean isActive, 
                                     LocalDateTime createdAt, LocalDateTime dueDate) {
            this.assignmentId = assignmentId;
            this.assignmentTitle = assignmentTitle;
            this.maxMarks = maxMarks;
            this.isPublished = isPublished;
            this.isOverdue = isOverdue;
            this.isActive = isActive;
            this.createdAt = createdAt;
            this.dueDate = dueDate;
        }
        
        // Getters and Setters
        public Long getAssignmentId() { return assignmentId; }
        public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }
        
        public String getAssignmentTitle() { return assignmentTitle; }
        public void setAssignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; }
        
        public Integer getMaxMarks() { return maxMarks; }
        public void setMaxMarks(Integer maxMarks) { this.maxMarks = maxMarks; }
        
        public Boolean getIsPublished() { return isPublished; }
        public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }
        
        public Boolean getIsOverdue() { return isOverdue; }
        public void setIsOverdue(Boolean isOverdue) { this.isOverdue = isOverdue; }
        
        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean isActive) { this.isActive = isActive; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public LocalDateTime getDueDate() { return dueDate; }
        public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    }
}
