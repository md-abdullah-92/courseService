package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.TeacherAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for TeacherAnswer entity
 * Provides CRUD operations and custom queries for teacher answers
 */
@Repository
public interface TeacherAnswerRepository extends JpaRepository<TeacherAnswer, Long> {
    
    /**
     * Find answer by question ID
     */
    @Query("SELECT ta FROM TeacherAnswer ta WHERE ta.questionId = :questionId")
    Optional<TeacherAnswer> findByQuestionId(@Param("questionId") String questionId);
    
    /**
     * Find all answers by teacher ID
     */
    @Query("SELECT ta FROM TeacherAnswer ta WHERE ta.teacherId = :teacherId ORDER BY ta.createdAt DESC")
    List<TeacherAnswer> findByTeacherIdOrderByCreatedAtDesc(@Param("teacherId") Long teacherId);
    
    /**
     * Check if question is already answered
     */
    @Query("SELECT COUNT(ta) > 0 FROM TeacherAnswer ta WHERE ta.questionId = :questionId")
    boolean existsByQuestionId(@Param("questionId") String questionId);
    
    /**
     * Count answers by teacher ID
     */
    @Query("SELECT COUNT(ta) FROM TeacherAnswer ta WHERE ta.teacherId = :teacherId")
    Long countByTeacherId(@Param("teacherId") Long teacherId);
    
    /**
     * Delete answer by question ID
     */
    void deleteByQuestionId(String questionId);
}
