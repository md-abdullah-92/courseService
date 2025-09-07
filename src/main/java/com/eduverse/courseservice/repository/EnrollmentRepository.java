package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    List<Enrollment> findByStudentId(String studentId);
    
    List<Enrollment> findByCourseId(Long courseId);
    
    Optional<Enrollment> findByStudentIdAndCourseId(String studentId, Long courseId);
    
    boolean existsByStudentIdAndCourseId(String studentId, Long courseId);
    
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.courseId = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT e FROM Enrollment e WHERE e.studentId = :studentId AND e.progressPercentage >= :minProgress")
    List<Enrollment> findByStudentIdAndProgressGreaterThanEqual(@Param("studentId") String studentId, @Param("minProgress") Double minProgress);
    
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.studentId = :studentId AND e.progressPercentage = 100.0")
    Long countCompletedCoursesByStudentId(@Param("studentId") String studentId);
}
