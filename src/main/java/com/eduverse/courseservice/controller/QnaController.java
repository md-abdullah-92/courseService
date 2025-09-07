package com.eduverse.courseservice.controller;

import com.eduverse.courseservice.dto.StudentQuestionDTO;
import com.eduverse.courseservice.dto.TeacherAnswerDTO;
import com.eduverse.courseservice.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for Q&A management
 * Provides comprehensive question and answer functionality for student-teacher interaction
 */
@RestController
@RequestMapping("/api/qna")
@CrossOrigin(origins = "*")
public class QnaController {

    private static final Logger log = LoggerFactory.getLogger(QnaController.class);
    private final QnaService qnaService;

    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    /**
     * Ask a new question (Student)
     * POST /api/qna/questions
     */
    @PostMapping("/questions")
    public ResponseEntity<ApiResponse<StudentQuestionDTO>> askQuestion(@Valid @RequestBody StudentQuestionDTO questionDTO) {
        log.info("Student {} asking question for course {}", questionDTO.getStudentId(), questionDTO.getCourseId());
        
        try {
            StudentQuestionDTO savedQuestion = qnaService.askQuestion(questionDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Question submitted successfully", savedQuestion));
        } catch (Exception e) {
            log.error("Error asking question: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get all questions for a course
     * GET /api/qna/courses/{courseId}/questions
     */
    @GetMapping("/courses/{courseId}/questions")
    public ResponseEntity<ApiResponse<List<StudentQuestionDTO>>> getQuestionsByCourse(@PathVariable Long courseId) {
        log.info("Fetching questions for course {}", courseId);
        
        try {
            List<StudentQuestionDTO> questions = qnaService.getQuestionsByCourse(courseId);
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Questions retrieved successfully. Count: " + questions.size(), questions));
        } catch (Exception e) {
            log.error("Error fetching questions: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get a single question by ID
     * GET /api/qna/questions/{id}
     */
    @GetMapping("/questions/{id}")
    public ResponseEntity<ApiResponse<StudentQuestionDTO>> getQuestionById(@PathVariable String id) {
        log.info("Fetching question with id: {}", id);
        
        try {
            StudentQuestionDTO question = qnaService.getQuestionById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Question retrieved successfully", question));
        } catch (Exception e) {
            log.error("Error fetching question: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Submit an answer (Teacher)
     * POST /api/qna/answers
     */
    @PostMapping("/answers")
    public ResponseEntity<ApiResponse<TeacherAnswerDTO>> answerQuestion(@Valid @RequestBody TeacherAnswerDTO answerDTO) {
        log.info("Teacher {} answering question {}", answerDTO.getTeacherId(), answerDTO.getQuestionId());
        
        try {
            TeacherAnswerDTO savedAnswer = qnaService.answerQuestion(answerDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Answer submitted successfully", savedAnswer));
        } catch (IllegalStateException e) {
            log.warn("Question already answered: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error submitting answer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get unanswered questions for a course
     * GET /api/qna/courses/{courseId}/questions/unanswered
     */
    @GetMapping("/courses/{courseId}/questions/unanswered")
    public ResponseEntity<ApiResponse<List<StudentQuestionDTO>>> getUnansweredQuestions(@PathVariable Long courseId) {
        log.info("Fetching unanswered questions for course {}", courseId);
        
        try {
            List<StudentQuestionDTO> questions = qnaService.getUnansweredQuestions(courseId);
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Unanswered questions retrieved successfully. Count: " + questions.size(), questions));
        } catch (Exception e) {
            log.error("Error fetching unanswered questions: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get answered questions for a course
     * GET /api/qna/courses/{courseId}/questions/answered
     */
    @GetMapping("/courses/{courseId}/questions/answered")
    public ResponseEntity<ApiResponse<List<StudentQuestionDTO>>> getAnsweredQuestions(@PathVariable Long courseId) {
        log.info("Fetching answered questions for course {}", courseId);
        
        try {
            List<StudentQuestionDTO> questions = qnaService.getAnsweredQuestions(courseId);
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Answered questions retrieved successfully. Count: " + questions.size(), questions));
        } catch (Exception e) {
            log.error("Error fetching answered questions: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get questions by student ID
     * GET /api/qna/students/{studentId}/questions
     */
    @GetMapping("/students/{studentId}/questions")
    public ResponseEntity<ApiResponse<List<StudentQuestionDTO>>> getQuestionsByStudent(@PathVariable Integer studentId) {
        log.info("Fetching questions for student {}", studentId);
        
        try {
            List<StudentQuestionDTO> questions = qnaService.getQuestionsByStudent(studentId);
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Student questions retrieved successfully. Count: " + questions.size(), questions));
        } catch (Exception e) {
            log.error("Error fetching student questions: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get answers by teacher ID
     * GET /api/qna/teachers/{teacherId}/answers
     */
    @GetMapping("/teachers/{teacherId}/answers")
    public ResponseEntity<ApiResponse<List<TeacherAnswerDTO>>> getAnswersByTeacher(@PathVariable Long teacherId) {
        log.info("Fetching answers for teacher {}", teacherId);
        
        try {
            List<TeacherAnswerDTO> answers = qnaService.getAnswersByTeacher(teacherId);
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Teacher answers retrieved successfully. Count: " + answers.size(), answers));
        } catch (Exception e) {
            log.error("Error fetching teacher answers: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Search questions by title
     * GET /api/qna/courses/{courseId}/questions/search
     */
    @GetMapping("/courses/{courseId}/questions/search")
    public ResponseEntity<ApiResponse<List<StudentQuestionDTO>>> searchQuestions(
            @PathVariable Long courseId, 
            @RequestParam String q) {
        log.info("Searching questions in course {} with term: {}", courseId, q);
        
        try {
            List<StudentQuestionDTO> questions = qnaService.searchQuestions(courseId, q);
            return ResponseEntity.ok(new ApiResponse<>(true, 
                    "Search completed successfully. Found: " + questions.size() + " questions", questions));
        } catch (Exception e) {
            log.error("Error searching questions: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Get Q&A statistics for a course
     * GET /api/qna/courses/{courseId}/statistics
     */
    @GetMapping("/courses/{courseId}/statistics")
    public ResponseEntity<ApiResponse<QnaService.QnaStatisticsDTO>> getQnaStatistics(@PathVariable Long courseId) {
        log.info("Fetching Q&A statistics for course {}", courseId);
        
        try {
            QnaService.QnaStatisticsDTO stats = qnaService.getQnaStatistics(courseId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Q&A statistics retrieved successfully", stats));
        } catch (Exception e) {
            log.error("Error fetching Q&A statistics: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Update question
     * PUT /api/qna/questions/{id}
     */
    @PutMapping("/questions/{id}")
    public ResponseEntity<ApiResponse<StudentQuestionDTO>> updateQuestion(
            @PathVariable String id, 
            @Valid @RequestBody StudentQuestionDTO questionDTO) {
        log.info("Updating question with id: {}", id);
        
        try {
            StudentQuestionDTO updatedQuestion = qnaService.updateQuestion(id, questionDTO);
            return ResponseEntity.ok(new ApiResponse<>(true, "Question updated successfully", updatedQuestion));
        } catch (Exception e) {
            log.error("Error updating question: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    /**
     * Delete question
     * DELETE /api/qna/questions/{id}
     */
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQuestion(@PathVariable String id) {
        log.info("Deleting question with id: {}", id);
        
        try {
            qnaService.deleteQuestion(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Question deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting question: {}", e.getMessage());
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
