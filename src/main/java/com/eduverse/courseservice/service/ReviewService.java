package com.eduverse.courseservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduverse.courseservice.dto.ReviewDTO;
import com.eduverse.courseservice.entity.Course;
import com.eduverse.courseservice.entity.Review;
import com.eduverse.courseservice.mapper.ReviewMapper;
import com.eduverse.courseservice.repository.CourseRepository;
import com.eduverse.courseservice.repository.ReviewRepository;

@Service
public class ReviewService {

    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, CourseRepository courseRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.courseRepository = courseRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public ReviewDTO createReview(String studentId, Long courseId, Integer rating, String reviewText) {
        log.info("Creating review for student {} on course {} with rating {}", studentId, courseId, rating);
        
        // Validate rating
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        
        // Check if course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        // Check if student already reviewed this course
        boolean alreadyReviewed = reviewRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (alreadyReviewed) {
            throw new RuntimeException("You have already reviewed this course");
        }
        
        // Create review
        Review review = new Review();
        review.setStudentId(studentId);
        review.setCourseId(courseId);
        review.setRating(rating);
        review.setComment(reviewText);
        
        Review savedReview = reviewRepository.save(review);
        
        // Update course average rating
        updateCourseAverageRating(courseId);
        
        return reviewMapper.toDTO(savedReview, course);
    }

    @Transactional
    public ReviewDTO updateReview(Long reviewId, String studentId, Integer rating, String reviewText) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Check if the student owns this review
        if (!review.getStudentId().equals(studentId)) {
            throw new RuntimeException("You can only update your own reviews");
        }

        // Validate rating
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        review.setRating(rating);
        review.setComment(reviewText);

        Review savedReview = reviewRepository.save(review);
        
        // Update course average rating
        updateCourseAverageRating(review.getCourseId());
        
        Optional<Course> course = courseRepository.findById(review.getCourseId());
        return course.map(c -> reviewMapper.toDTO(savedReview, c))
                .orElse(reviewMapper.toDTO(savedReview));
    }

    public List<ReviewDTO> getCourseReviews(Long courseId) {
        List<Review> reviews = reviewRepository.findByCourseIdOrderByCreatedAtDesc(courseId);
        Optional<Course> course = courseRepository.findById(courseId);
        
        return reviews.stream()
                .map(review -> course.map(c -> reviewMapper.toDTO(review, c))
                        .orElse(reviewMapper.toDTO(review)))
                .toList();
    }

    public List<ReviewDTO> getStudentReviews(String studentId) {
        List<Review> reviews = reviewRepository.findByStudentId(studentId);
        return reviews.stream()
                .map(review -> {
                    Optional<Course> course = courseRepository.findById(review.getCourseId());
                    return course.map(c -> reviewMapper.toDTO(review, c))
                            .orElse(reviewMapper.toDTO(review));
                })
                .toList();
    }

    public Double getCourseAverageRating(Long courseId) {
        return reviewRepository.getAverageRatingByCourseId(courseId);
    }

    public Long getCourseReviewCount(Long courseId) {
        return reviewRepository.countByCourseId(courseId);
    }

    public Map<String, Object> getCourseReviewStats(Long courseId) {
        Double averageRating = reviewRepository.getAverageRatingByCourseId(courseId);
        Long totalReviews = reviewRepository.countByCourseId(courseId);
        
        // Get rating distribution
        List<Object[]> distributionData = reviewRepository.getRatingDistributionByCourseId(courseId);
        Map<String, Integer> distribution = new HashMap<>();
        
        // Initialize all ratings to 0
        for (int i = 1; i <= 5; i++) {
            distribution.put(String.valueOf(i), 0);
        }
        
        // Fill in actual counts
        for (Object[] row : distributionData) {
            Integer rating = (Integer) row[0];
            Long count = (Long) row[1];
            distribution.put(rating.toString(), count.intValue());
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("averageRating", averageRating != null ? averageRating : 0.0);
        stats.put("totalReviews", totalReviews);
        stats.put("distribution", distribution);
        
        return stats;
    }

    @Transactional
    public void deleteReview(Long reviewId, String studentId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Check if the student owns this review
        if (!review.getStudentId().equals(studentId)) {
            throw new RuntimeException("You can only delete your own reviews");
        }

        Long courseId = review.getCourseId();
        reviewRepository.delete(review);
        
        // Update course average rating
        updateCourseAverageRating(courseId);
    }

    public Optional<ReviewDTO> getStudentReviewForCourse(String studentId, Long courseId) {
        Optional<Review> review = reviewRepository.findByStudentIdAndCourseId(studentId, courseId);
        if (review.isPresent()) {
            Optional<Course> course = courseRepository.findById(courseId);
            return Optional.of(course.map(c -> reviewMapper.toDTO(review.get(), c))
                    .orElse(reviewMapper.toDTO(review.get())));
        }
        return Optional.empty();
    }

    @Transactional
    private void updateCourseAverageRating(Long courseId) {
        Double averageRating = reviewRepository.getAverageRatingByCourseId(courseId);
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            course.setAverageRating(averageRating);
            courseRepository.save(course);
        }
    }
}
