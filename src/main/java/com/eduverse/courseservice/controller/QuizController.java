package com.eduverse.courseservice.controller;

import com.eduverse.courseservice.dto.QuizDTO;
import com.eduverse.courseservice.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for Quiz management
 * Provides comprehensive quiz functionality with questions and options
 */
@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "*")
public class QuizController {

    private static final Logger log = LoggerFactory.getLogger(QuizController.class);
    private final QuizService quizService;
    
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Create a new quiz
     * POST /api/quiz
     */
    @PostMapping
    public ResponseEntity<ApiResponse<QuizDTO>> createQuiz(@Valid @RequestBody QuizDTO quizDTO) {
        log.info("Creating new quiz: {}", quizDTO.getTitle());
        
        try {
            QuizDTO createdQuiz = quizService.createQuiz(quizDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Quiz created successfully", createdQuiz));
        } catch (Exception e) {
            log.error("Error creating quiz: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get all quizzes
     * GET /api/quiz
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuizDTO>>> getAllQuizzes() {
        log.info("Fetching all quizzes");
        
        try {
            List<QuizDTO> quizzes = quizService.getAllQuizzes();
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Quizzes retrieved successfully. Count: " + quizzes.size(), quizzes));
        } catch (Exception e) {
            log.error("Error fetching quizzes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get quiz by ID
     * GET /api/quiz/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizDTO>> getQuizById(@PathVariable Long id) {
        log.info("Fetching quiz with id: {}", id);
        
        try {
            QuizDTO quiz = quizService.getQuizById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Quiz retrieved successfully", quiz));
        } catch (Exception e) {
            log.error("Error fetching quiz: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Update quiz
     * PUT /api/quiz/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizDTO>> updateQuiz(@PathVariable Long id, 
                                                          @Valid @RequestBody QuizDTO quizDTO) {
        log.info("Updating quiz with id: {}", id);
        
        try {
            QuizDTO updatedQuiz = quizService.updateQuiz(id, quizDTO);
            return ResponseEntity.ok(new ApiResponse<>(true, "Quiz updated successfully", updatedQuiz));
        } catch (Exception e) {
            log.error("Error updating quiz: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Delete quiz
     * DELETE /api/quiz/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQuiz(@PathVariable Long id) {
        log.info("Deleting quiz with id: {}", id);
        
        try {
            quizService.deleteQuiz(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Quiz deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting quiz: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get quizzes by lesson ID
     * GET /api/quiz/lesson/{lessonId}
     */
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<ApiResponse<List<QuizDTO>>> getQuizzesByLessonId(@PathVariable Long lessonId) {
        log.info("Fetching quizzes for lesson id: {}", lessonId);
        
        try {
            List<QuizDTO> quizzes = quizService.getQuizzesByLessonId(lessonId);
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Quizzes retrieved successfully for lesson. Count: " + quizzes.size(), quizzes));
        } catch (Exception e) {
            log.error("Error fetching quizzes for lesson: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Save complete quiz with questions and options
     * POST /api/quiz/save/{lessonId}
     */
    @PostMapping("/save/{lessonId}")
    public ResponseEntity<ApiResponse<QuizDTO>> saveCompleteQuiz(@PathVariable Long lessonId,
                                                                @Valid @RequestBody QuizDTO quizDTO) {
        log.info("Saving complete quiz for lesson id: {}", lessonId);
        
        try {
            QuizDTO savedQuiz = quizService.saveCompleteQuiz(lessonId, quizDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Complete quiz saved successfully", savedQuiz));
        } catch (Exception e) {
            log.error("Error saving complete quiz: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get published quizzes by lesson ID
     * GET /api/quiz/lesson/{lessonId}/published
     */
    @GetMapping("/lesson/{lessonId}/published")
    public ResponseEntity<ApiResponse<List<QuizDTO>>> getPublishedQuizzesByLessonId(@PathVariable Long lessonId) {
        log.info("Fetching published quizzes for lesson id: {}", lessonId);
        
        try {
            List<QuizDTO> quizzes = quizService.getPublishedQuizzesByLessonId(lessonId);
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Published quizzes retrieved successfully. Count: " + quizzes.size(), quizzes));
        } catch (Exception e) {
            log.error("Error fetching published quizzes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get quiz statistics
     * GET /api/quiz/{id}/statistics
     */
    @GetMapping("/{id}/statistics")
    public ResponseEntity<ApiResponse<QuizService.QuizStatisticsDTO>> getQuizStatistics(@PathVariable Long id) {
        log.info("Fetching statistics for quiz id: {}", id);
        
        try {
            QuizService.QuizStatisticsDTO stats = quizService.getQuizStatistics(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Quiz statistics retrieved successfully", stats));
        } catch (Exception e) {
            log.error("Error fetching quiz statistics: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Generic API Response wrapper
     */
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        
        // Constructors
        public ApiResponse() {}
        
        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
        
        // Getters and Setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
    }
}
