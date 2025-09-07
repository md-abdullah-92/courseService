package com.eduverse.courseservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eduverse.courseservice.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByCourseId(Long courseId);
    
    List<Review> findByStudentId(String studentId);
    
    Optional<Review> findByStudentIdAndCourseId(String studentId, Long courseId);
    
    boolean existsByStudentIdAndCourseId(String studentId, Long courseId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.courseId = :courseId")
    Double getAverageRatingByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.courseId = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.courseId = :courseId GROUP BY r.rating")
    List<Object[]> getRatingDistributionByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT r FROM Review r WHERE r.courseId = :courseId ORDER BY r.createdAt DESC")
    List<Review> findByCourseIdOrderByCreatedAtDesc(@Param("courseId") Long courseId);
}
