package com.eduverse.courseservice.controller;

import com.eduverse.courseservice.dto.EnrollmentDTO;
import com.eduverse.courseservice.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> enrollStudent(
            @RequestParam String studentId,
            @RequestParam Long courseId) {
        try {
            EnrollmentDTO enrollment = enrollmentService.enrollStudent(studentId, courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Student enrolled successfully",
                    "data", enrollment
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentEnrollments(@PathVariable String studentId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.getStudentEnrollments(studentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", enrollments
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Map<String, Object>> getCourseEnrollments(@PathVariable Long courseId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.getCourseEnrollments(courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", enrollments
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @PutMapping("/progress")
    public ResponseEntity<Map<String, Object>> updateProgress(
            @RequestParam String studentId,
            @RequestParam Long courseId,
            @RequestParam Double progress) {
        try {
            EnrollmentDTO enrollment = enrollmentService.updateProgress(studentId, courseId, progress);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Progress updated successfully",
                    "data", enrollment
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<Map<String, Object>> getCourseEnrollmentCount(@PathVariable Long courseId) {
        try {
            Long count = enrollmentService.getCourseEnrollmentCount(courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of("enrollmentCount", count)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @GetMapping("/student/{studentId}/completed-count")
    public ResponseEntity<Map<String, Object>> getStudentCompletedCoursesCount(@PathVariable String studentId) {
        try {
            Long count = enrollmentService.getStudentCompletedCoursesCount(studentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of("completedCourses", count)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> unenrollStudent(
            @RequestParam String studentId,
            @RequestParam Long courseId) {
        try {
            enrollmentService.unenrollStudent(studentId, courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Student unenrolled successfully"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkEnrollment(
            @RequestParam String studentId,
            @RequestParam Long courseId) {
        try {
            boolean isEnrolled = enrollmentService.isStudentEnrolled(studentId, courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of("isEnrolled", isEnrolled)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }
}
