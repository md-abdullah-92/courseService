package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.CourseOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOutcomeRepository extends JpaRepository<CourseOutcome, Long> {
    
    List<CourseOutcome> findByCourseId(Long courseId);
    
    @Modifying
    @Query("DELETE FROM CourseOutcome co WHERE co.course.id = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
