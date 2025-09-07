package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Assignment entity
 * Provides CRUD operations and custom queries for Assignment management
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    /**
     * Find all assignments by lesson ID
     */
    @Query("SELECT a FROM Assignment a WHERE a.lesson.id = :lessonId")
    List<Assignment> findByLessonId(@Param("lessonId") Long lessonId);
    
    /**
     * Find all published assignments by lesson ID
     */
    @Query("SELECT a FROM Assignment a WHERE a.lesson.id = :lessonId AND a.isPublished = true")
    List<Assignment> findPublishedByLessonId(@Param("lessonId") Long lessonId);
    
    /**
     * Find assignments by teacher ID
     */
    @Query("SELECT a FROM Assignment a WHERE a.teacherId = :teacherId ORDER BY a.createdAt DESC")
    List<Assignment> findByTeacherId(@Param("teacherId") Long teacherId);
    
    /**
     * Find active assignments (published and not overdue)
     */
    @Query("SELECT a FROM Assignment a WHERE a.isPublished = true AND (a.dueDate IS NULL OR a.dueDate > :currentTime)")
    List<Assignment> findActiveAssignments(@Param("currentTime") LocalDateTime currentTime);
    
    /**
     * Find overdue assignments
     */
    @Query("SELECT a FROM Assignment a WHERE a.isPublished = true AND a.dueDate IS NOT NULL AND a.dueDate < :currentTime")
    List<Assignment> findOverdueAssignments(@Param("currentTime") LocalDateTime currentTime);
    
    /**
     * Count assignments by lesson ID
     */
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.lesson.id = :lessonId")
    Long countByLessonId(@Param("lessonId") Long lessonId);
    
    /**
     * Find assignments due within a specific timeframe
     */
    @Query("SELECT a FROM Assignment a WHERE a.isPublished = true AND a.dueDate BETWEEN :startTime AND :endTime")
    List<Assignment> findAssignmentsDueBetween(@Param("startTime") LocalDateTime startTime, 
                                              @Param("endTime") LocalDateTime endTime);
    
    /**
     * Check if assignment exists by title and lesson
     */
    @Query("SELECT COUNT(a) > 0 FROM Assignment a WHERE a.lesson.id = :lessonId AND a.title = :title")
    boolean existsByLessonIdAndTitle(@Param("lessonId") Long lessonId, @Param("title") String title);
    
    /**
     * Delete all assignments by lesson ID
     */
    @Modifying
    @Query("DELETE FROM Assignment a WHERE a.lesson.id = :lessonId")
    void deleteByLessonId(@Param("lessonId") Long lessonId);
}
