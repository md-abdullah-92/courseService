package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Quiz entity
 * Provides CRUD operations and custom queries for Quiz management
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
    /**
     * Find all quizzes by lesson ID
     */
    @Query("SELECT q FROM Quiz q WHERE q.lesson.id = :lessonId")
    List<Quiz> findByLessonId(@Param("lessonId") Long lessonId);
    
    /**
     * Find quiz by ID with questions and options loaded
     */
    @Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.questions questions LEFT JOIN FETCH questions.options WHERE q.id = :quizId")
    Optional<Quiz> findByIdWithQuestions(@Param("quizId") Long quizId);
    
    /**
     * Find all published quizzes by lesson ID
     */
    @Query("SELECT q FROM Quiz q WHERE q.lesson.id = :lessonId AND q.isPublished = true")
    List<Quiz> findPublishedByLessonId(@Param("lessonId") Long lessonId);
    
    /**
     * Count total quizzes by lesson ID
     */
    @Query("SELECT COUNT(q) FROM Quiz q WHERE q.lesson.id = :lessonId")
    Long countByLessonId(@Param("lessonId") Long lessonId);
    
    /**
     * Find quizzes by lesson ID with pagination
     */
    @Query("SELECT q FROM Quiz q WHERE q.lesson.id = :lessonId ORDER BY q.createdAt DESC")
    List<Quiz> findByLessonIdOrderByCreatedAtDesc(@Param("lessonId") Long lessonId);
    
    /**
     * Find all quizzes with their questions count
     */
    @Query("SELECT q, SIZE(q.questions) as questionCount FROM Quiz q")
    List<Object[]> findAllWithQuestionCount();
    
    /**
     * Delete all quizzes by lesson ID
     */
    @Modifying
    @Query("DELETE FROM Quiz q WHERE q.lesson.id = :lessonId")
    void deleteByLessonId(@Param("lessonId") Long lessonId);
    
    /**
     * Check if quiz exists by lesson ID and title
     */
    @Query("SELECT COUNT(q) > 0 FROM Quiz q WHERE q.lesson.id = :lessonId AND q.title = :title")
    boolean existsByLessonIdAndTitle(@Param("lessonId") Long lessonId, @Param("title") String title);
}
