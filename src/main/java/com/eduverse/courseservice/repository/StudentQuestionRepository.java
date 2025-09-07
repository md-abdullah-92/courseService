package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.StudentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for StudentQuestion entity
 * Provides CRUD operations and custom queries for Q&A management
 */
@Repository
public interface StudentQuestionRepository extends JpaRepository<StudentQuestion, String> {
    
    /**
     * Find all questions by course ID ordered by creation date
     */
    @Query("SELECT sq FROM StudentQuestion sq WHERE sq.courseId = :courseId ORDER BY sq.createdAt DESC")
    List<StudentQuestion> findByCourseIdOrderByCreatedAtDesc(@Param("courseId") Long courseId);
    
    /**
     * Find unanswered questions by course ID
     */
    @Query("SELECT sq FROM StudentQuestion sq WHERE sq.courseId = :courseId AND sq.isAnswered = false ORDER BY sq.createdAt DESC")
    List<StudentQuestion> findUnansweredByCourseId(@Param("courseId") Long courseId);
    
    /**
     * Find answered questions by course ID
     */
    @Query("SELECT sq FROM StudentQuestion sq WHERE sq.courseId = :courseId AND sq.isAnswered = true ORDER BY sq.createdAt DESC")
    List<StudentQuestion> findAnsweredByCourseId(@Param("courseId") Long courseId);
    
    /**
     * Find questions by student ID
     */
    @Query("SELECT sq FROM StudentQuestion sq WHERE sq.studentId = :studentId ORDER BY sq.createdAt DESC")
    List<StudentQuestion> findByStudentIdOrderByCreatedAtDesc(@Param("studentId") Integer studentId);
    
    /**
     * Count unanswered questions by course ID
     */
    @Query("SELECT COUNT(sq) FROM StudentQuestion sq WHERE sq.courseId = :courseId AND sq.isAnswered = false")
    Long countUnansweredByCourseId(@Param("courseId") Long courseId);
    
    /**
     * Count total questions by course ID
     */
    @Query("SELECT COUNT(sq) FROM StudentQuestion sq WHERE sq.courseId = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);
    
    /**
     * Find questions by title containing search term
     */
    @Query("SELECT sq FROM StudentQuestion sq WHERE sq.courseId = :courseId AND LOWER(sq.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY sq.createdAt DESC")
    List<StudentQuestion> findByCourseIdAndTitleContaining(@Param("courseId") Long courseId, @Param("searchTerm") String searchTerm);
}
