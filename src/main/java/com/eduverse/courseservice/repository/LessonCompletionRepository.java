package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.LessonCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonCompletionRepository extends JpaRepository<LessonCompletion, Long> {
    
    Optional<LessonCompletion> findByEnrollmentIdAndLessonId(Long enrollmentId, Long lessonId);
    
    boolean existsByEnrollmentIdAndLessonId(Long enrollmentId, Long lessonId);
}
