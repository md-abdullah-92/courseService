package com.eduverse.courseservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eduverse.courseservice.dto.ReviewDTO;
import com.eduverse.courseservice.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReview(@RequestBody Map<String, Object> request) {
        try {
            String studentId = (String) request.get("studentId");
            Long courseId = Long.valueOf(request.get("courseId").toString());
            Integer rating = Integer.valueOf(request.get("rating").toString());
            String reviewText = (String) request.get("comment");
            
            ReviewDTO review = reviewService.createReview(studentId, courseId, rating, reviewText);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Review created successfully",
                    "data", review
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Map<String, Object>> updateReview(
            @PathVariable Long reviewId,
            @RequestBody Map<String, Object> request) {
        try {
            String studentId = (String) request.get("studentId");
            Integer rating = Integer.valueOf(request.get("rating").toString());
            String reviewText = (String) request.get("comment");
            
            ReviewDTO review = reviewService.updateReview(reviewId, studentId, rating, reviewText);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Review updated successfully",
                    "data", review
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Map<String, Object>> getCourseReviews(@PathVariable Long courseId) {
        try {
            List<ReviewDTO> reviews = reviewService.getCourseReviews(courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", reviews
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentReviews(@PathVariable String studentId) {
        try {
            List<ReviewDTO> reviews = reviewService.getStudentReviews(studentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", reviews
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @GetMapping("/course/{courseId}/average")
    public ResponseEntity<Map<String, Object>> getCourseAverageRating(@PathVariable Long courseId) {
        try {
            Double averageRating = reviewService.getCourseAverageRating(courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of("averageRating", averageRating != null ? averageRating : 0.0)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<Map<String, Object>> getCourseReviewCount(@PathVariable Long courseId) {
        try {
            Long reviewCount = reviewService.getCourseReviewCount(courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of("reviewCount", reviewCount)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @GetMapping("/course/{courseId}/stats")
    public ResponseEntity<Map<String, Object>> getCourseReviewStats(@PathVariable Long courseId) {
        try {
            Map<String, Object> stats = reviewService.getCourseReviewStats(courseId);
            
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", stats
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to fetch rating statistics"
            ));
        }
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Map<String, Object>> getStudentReviewForCourse(
            @PathVariable String studentId, 
            @PathVariable Long courseId) {
        try {
            Optional<ReviewDTO> review = reviewService.getStudentReviewForCourse(studentId, courseId);
            if (review.isPresent()) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "data", review.get()
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "data", null,
                        "message", "No review found"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            ));
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable Long reviewId, @RequestParam String studentId) {
        try {
            reviewService.deleteReview(reviewId, studentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Review deleted successfully"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
}
