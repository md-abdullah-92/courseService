package com.eduverse.courseservice.controller;

import com.eduverse.courseservice.dto.StudyNoteDTO;
import com.eduverse.courseservice.service.StudyNoteService;
import com.eduverse.courseservice.service.StudyNoteService.StudyNoteStatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for StudyNote operations
 * Provides comprehensive study note management API
 */
@RestController
@RequestMapping("/api/study-notes")
@CrossOrigin(origins = "*")
public class StudyNoteController {

    private static final Logger log = LoggerFactory.getLogger(StudyNoteController.class);
    private final StudyNoteService studyNoteService;

    public StudyNoteController(StudyNoteService studyNoteService) {
        this.studyNoteService = studyNoteService;
    }

    /**
     * Create a new study note
     * POST /api/study-notes
     */
    @PostMapping
    public ResponseEntity<StudyNoteDTO> createStudyNote(@Valid @RequestBody StudyNoteDTO studyNoteDTO) {
        log.info("Creating new study note: {}", studyNoteDTO.getTitle());
        
        StudyNoteDTO createdStudyNote = studyNoteService.createStudyNote(studyNoteDTO);
        return new ResponseEntity<>(createdStudyNote, HttpStatus.CREATED);
    }

    /**
     * Get all study notes
     * GET /api/study-notes
     */
    @GetMapping
    public ResponseEntity<List<StudyNoteDTO>> getAllStudyNotes() {
        log.info("Fetching all study notes");
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getAllStudyNotes();
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Get study note by ID
     * GET /api/study-notes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudyNoteDTO> getStudyNoteById(@PathVariable Long id) {
        log.info("Fetching study note with id: {}", id);
        
        StudyNoteDTO studyNote = studyNoteService.getStudyNoteById(id);
        return ResponseEntity.ok(studyNote);
    }

    /**
     * Update study note
     * PUT /api/study-notes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudyNoteDTO> updateStudyNote(
            @PathVariable Long id,
            @Valid @RequestBody StudyNoteDTO studyNoteDTO) {
        log.info("Updating study note with id: {}", id);
        
        StudyNoteDTO updatedStudyNote = studyNoteService.updateStudyNote(id, studyNoteDTO);
        return ResponseEntity.ok(updatedStudyNote);
    }

    /**
     * Delete study note
     * DELETE /api/study-notes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyNote(@PathVariable Long id) {
        log.info("Deleting study note with id: {}", id);
        
        studyNoteService.deleteStudyNote(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get study notes by lesson ID
     * GET /api/study-notes/lesson/{lessonId}
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<StudyNoteDTO>> getStudyNotesByLessonId(@PathVariable Long lessonId) {
        log.info("Fetching study notes for lesson id: {}", lessonId);
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getStudyNotesByLessonId(lessonId);
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Get study notes by user ID
     * GET /api/study-notes/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StudyNoteDTO>> getStudyNotesByUserId(@PathVariable Long userId) {
        log.info("Fetching study notes for user id: {}", userId);
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getStudyNotesByUserId(userId);
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Get public study notes
     * GET /api/study-notes/public
     */
    @GetMapping("/public")
    public ResponseEntity<List<StudyNoteDTO>> getPublicStudyNotes() {
        log.info("Fetching public study notes");
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getPublicStudyNotes();
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Get favorite study notes by user ID
     * GET /api/study-notes/user/{userId}/favorites
     */
    @GetMapping("/user/{userId}/favorites")
    public ResponseEntity<List<StudyNoteDTO>> getFavoriteStudyNotesByUserId(@PathVariable Long userId) {
        log.info("Fetching favorite study notes for user id: {}", userId);
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getFavoriteStudyNotesByUserId(userId);
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Search study notes by title or content
     * GET /api/study-notes/search?q={searchTerm}
     */
    @GetMapping("/search")
    public ResponseEntity<List<StudyNoteDTO>> searchStudyNotes(@RequestParam String q) {
        log.info("Searching study notes with term: {}", q);
        
        List<StudyNoteDTO> studyNotes = studyNoteService.searchStudyNotes(q);
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Get recent study notes (last 7 days)
     * GET /api/study-notes/recent
     */
    @GetMapping("/recent")
    public ResponseEntity<List<StudyNoteDTO>> getRecentStudyNotes() {
        log.info("Fetching recent study notes");
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getRecentStudyNotes();
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Get popular study notes
     * GET /api/study-notes/popular
     */
    @GetMapping("/popular")
    public ResponseEntity<List<StudyNoteDTO>> getPopularStudyNotes() {
        log.info("Fetching popular study notes");
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getPopularStudyNotes();
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Like/unlike study note
     * POST /api/study-notes/{id}/like?like={true/false}
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<StudyNoteDTO> toggleLikeStudyNote(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") boolean like) {
        log.info("Toggling like for study note id: {} to {}", id, like);
        
        StudyNoteDTO studyNote = studyNoteService.toggleLikeStudyNote(id, like);
        return ResponseEntity.ok(studyNote);
    }

    /**
     * Share study note
     * POST /api/study-notes/{id}/share
     */
    @PostMapping("/{id}/share")
    public ResponseEntity<StudyNoteDTO> shareStudyNote(@PathVariable Long id) {
        log.info("Sharing study note id: {}", id);
        
        StudyNoteDTO studyNote = studyNoteService.shareStudyNote(id);
        return ResponseEntity.ok(studyNote);
    }

    /**
     * Get study notes with filters
     * GET /api/study-notes/filter?lessonId={lessonId}&userId={userId}&category={category}&noteType={noteType}&isPublic={isPublic}
     */
    @GetMapping("/filter")
    public ResponseEntity<List<StudyNoteDTO>> getStudyNotesWithFilters(
            @RequestParam(required = false) Long lessonId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String noteType,
            @RequestParam(required = false) Boolean isPublic) {
        log.info("Fetching study notes with filters");
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getStudyNotesWithFilters(lessonId, userId, category, noteType, isPublic);
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Get paginated study notes
     * GET /api/study-notes/paginated?page={page}&size={size}&sort={sort}
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<StudyNoteDTO>> getPaginatedStudyNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction) {
        log.info("Fetching paginated study notes - page: {}, size: {}", page, size);
        
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<StudyNoteDTO> studyNotePage = studyNoteService.getPaginatedStudyNotes(pageable);
        return ResponseEntity.ok(studyNotePage);
    }

    /**
     * Get study note statistics
     * GET /api/study-notes/{id}/statistics
     */
    @GetMapping("/{id}/statistics")
    public ResponseEntity<StudyNoteStatisticsDTO> getStudyNoteStatistics(@PathVariable Long id) {
        log.info("Fetching statistics for study note id: {}", id);
        
        StudyNoteStatisticsDTO statistics = studyNoteService.getStudyNoteStatistics(id);
        return ResponseEntity.ok(statistics);
    }

    /**
     * Toggle favorite status
     * PATCH /api/study-notes/{id}/favorite
     */
    @PatchMapping("/{id}/favorite")
    public ResponseEntity<StudyNoteDTO> toggleFavoriteStatus(
            @PathVariable Long id,
            @RequestParam Boolean favorite) {
        log.info("Setting study note {} favorite status to: {}", id, favorite);
        
        StudyNoteDTO studyNote = studyNoteService.getStudyNoteById(id);
        studyNote.setIsFavorite(favorite);
        StudyNoteDTO updatedStudyNote = studyNoteService.updateStudyNote(id, studyNote);
        
        return ResponseEntity.ok(updatedStudyNote);
    }

    /**
     * Toggle public status
     * PATCH /api/study-notes/{id}/public
     */
    @PatchMapping("/{id}/public")
    public ResponseEntity<StudyNoteDTO> togglePublicStatus(
            @PathVariable Long id,
            @RequestParam Boolean isPublic) {
        log.info("Setting study note {} public status to: {}", id, isPublic);
        
        StudyNoteDTO studyNote = studyNoteService.getStudyNoteById(id);
        studyNote.setIsPublic(isPublic);
        StudyNoteDTO updatedStudyNote = studyNoteService.updateStudyNote(id, studyNote);
        
        return ResponseEntity.ok(updatedStudyNote);
    }

    /**
     * Get study notes count
     * GET /api/study-notes/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getStudyNotesCount() {
        log.info("Fetching total study notes count");
        
        long count = studyNoteService.getAllStudyNotes().size();
        return ResponseEntity.ok(count);
    }

    /**
     * Get study notes by category
     * GET /api/study-notes/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<StudyNoteDTO>> getStudyNotesByCategory(@PathVariable String category) {
        log.info("Fetching study notes for category: {}", category);
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getStudyNotesWithFilters(null, null, category, null, null);
        return ResponseEntity.ok(studyNotes);
    }

    /**
     * Get study notes by note type
     * GET /api/study-notes/type/{noteType}
     */
    @GetMapping("/type/{noteType}")
    public ResponseEntity<List<StudyNoteDTO>> getStudyNotesByType(@PathVariable String noteType) {
        log.info("Fetching study notes for type: {}", noteType);
        
        List<StudyNoteDTO> studyNotes = studyNoteService.getStudyNotesWithFilters(null, null, null, noteType, null);
        return ResponseEntity.ok(studyNotes);
    }
}
