package com.eduverse.courseservice.controller;

import com.eduverse.courseservice.dto.AssignmentDTO;
import com.eduverse.courseservice.service.AssignmentService;
import com.eduverse.courseservice.service.AssignmentService.AssignmentStatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for Assignment operations
 * Provides comprehensive assignment management API
 */
@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {

    private static final Logger log = LoggerFactory.getLogger(AssignmentController.class);
    private final AssignmentService assignmentService;
    
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /**
     * Create a new assignment
     * POST /api/assignments
     */
    @PostMapping
    public ResponseEntity<AssignmentDTO> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
        log.info("Creating new assignment: {}", assignmentDTO.getTitle());
        
        AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
        return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
    }

    /**
     * Get all assignments
     * GET /api/assignments
     */
    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments() {
        log.info("Fetching all assignments");
        
        List<AssignmentDTO> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get assignment by ID
     * GET /api/assignments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable Long id) {
        log.info("Fetching assignment with id: {}", id);
        
        AssignmentDTO assignment = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    /**
     * Update assignment
     * PUT /api/assignments/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentDTO assignmentDTO) {
        log.info("Updating assignment with id: {}", id);
        
        AssignmentDTO updatedAssignment = assignmentService.updateAssignment(id, assignmentDTO);
        return ResponseEntity.ok(updatedAssignment);
    }

    /**
     * Delete assignment
     * DELETE /api/assignments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.info("Deleting assignment with id: {}", id);
        
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get assignments by lesson ID
     * GET /api/assignments/lesson/{lessonId}
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByLessonId(@PathVariable Long lessonId) {
        log.info("Fetching assignments for lesson id: {}", lessonId);
        
        List<AssignmentDTO> assignments = assignmentService.getAssignmentsByLessonId(lessonId);
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get assignments by teacher ID
     * GET /api/assignments/teacher/{teacherId}
     */
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByTeacherId(@PathVariable Long teacherId) {
        log.info("Fetching assignments for teacher id: {}", teacherId);
        
        List<AssignmentDTO> assignments = assignmentService.getAssignmentsByTeacherId(teacherId);
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get published assignments by lesson ID
     * GET /api/assignments/lesson/{lessonId}/published
     */
    @GetMapping("/lesson/{lessonId}/published")
    public ResponseEntity<List<AssignmentDTO>> getPublishedAssignmentsByLessonId(@PathVariable Long lessonId) {
        log.info("Fetching published assignments for lesson id: {}", lessonId);
        
        List<AssignmentDTO> assignments = assignmentService.getPublishedAssignmentsByLessonId(lessonId);
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get active assignments (published and not overdue)
     * GET /api/assignments/active
     */
    @GetMapping("/active")
    public ResponseEntity<List<AssignmentDTO>> getActiveAssignments() {
        log.info("Fetching active assignments");
        
        List<AssignmentDTO> assignments = assignmentService.getActiveAssignments();
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get overdue assignments
     * GET /api/assignments/overdue
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<AssignmentDTO>> getOverdueAssignments() {
        log.info("Fetching overdue assignments");
        
        List<AssignmentDTO> assignments = assignmentService.getOverdueAssignments();
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get assignments due within specific timeframe
     * GET /api/assignments/due-soon?days={days}
     */
    @GetMapping("/due-soon")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsDueSoon(
            @RequestParam(defaultValue = "7") int days) {
        log.info("Fetching assignments due within {} days", days);
        
        List<AssignmentDTO> assignments = assignmentService.getAssignmentsDueSoon(days);
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get assignment statistics
     * GET /api/assignments/{id}/statistics
     */
    @GetMapping("/{id}/statistics")
    public ResponseEntity<AssignmentStatisticsDTO> getAssignmentStatistics(@PathVariable Long id) {
        log.info("Fetching statistics for assignment id: {}", id);
        
        AssignmentStatisticsDTO statistics = assignmentService.getAssignmentStatistics(id);
        return ResponseEntity.ok(statistics);
    }

    /**
     * Search assignments by title
     * GET /api/assignments/search?title={title}
     */
    @GetMapping("/search")
    public ResponseEntity<List<AssignmentDTO>> searchAssignmentsByTitle(
            @RequestParam String title) {
        log.info("Searching assignments with title containing: {}", title);
        
        List<AssignmentDTO> assignments = assignmentService.getAllAssignments().stream()
                .filter(assignment -> assignment.getTitle() != null && 
                       assignment.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
        
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get assignment count
     * GET /api/assignments/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getAssignmentCount() {
        log.info("Fetching total assignment count");
        
        long count = assignmentService.getAllAssignments().size();
        return ResponseEntity.ok(count);
    }

    /**
     * Get assignments by lesson and status
     * GET /api/assignments/lesson/{lessonId}/status?published={published}
     */
    @GetMapping("/lesson/{lessonId}/status")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByLessonAndStatus(
            @PathVariable Long lessonId,
            @RequestParam Boolean published) {
        log.info("Fetching assignments for lesson {} with published status: {}", lessonId, published);
        
        List<AssignmentDTO> assignments;
        if (published) {
            assignments = assignmentService.getPublishedAssignmentsByLessonId(lessonId);
        } else {
            assignments = assignmentService.getAssignmentsByLessonId(lessonId).stream()
                    .filter(assignment -> !assignment.getIsPublished())
                    .toList();
        }
        
        return ResponseEntity.ok(assignments);
    }

    /**
     * Publish/unpublish assignment
     * PATCH /api/assignments/{id}/publish
     */
    @PatchMapping("/{id}/publish")
    public ResponseEntity<AssignmentDTO> toggleAssignmentPublishStatus(
            @PathVariable Long id,
            @RequestParam Boolean published) {
        log.info("Setting assignment {} published status to: {}", id, published);
        
        AssignmentDTO assignment = assignmentService.getAssignmentById(id);
        assignment.setIsPublished(published);
        AssignmentDTO updatedAssignment = assignmentService.updateAssignment(id, assignment);
        
        return ResponseEntity.ok(updatedAssignment);
    }
}
