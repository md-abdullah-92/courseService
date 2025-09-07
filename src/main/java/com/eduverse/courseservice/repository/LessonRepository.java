package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    
    List<Lesson> findByCourseIdOrderByOrderIndexAsc(Long courseId);
    
    @Query("SELECT l FROM Lesson l WHERE l.courseId = :courseId")
    List<Lesson> findAllByCourseId(@Param("courseId") Long courseId);
    
    @Modifying
    @Query("DELETE FROM Lesson l WHERE l.courseId = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
