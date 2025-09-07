package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByStudentId(String studentId);
    
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.course WHERE ci.studentId = :studentId")
    List<CartItem> findByStudentIdWithCourse(@Param("studentId") String studentId);
    
    Optional<CartItem> findByStudentIdAndCourseId(String studentId, Long courseId);
    
    boolean existsByStudentIdAndCourseId(String studentId, Long courseId);
    
    void deleteByStudentIdAndCourseId(String studentId, Long courseId);
    
    void deleteByStudentId(String studentId);
    
    Long countByStudentId(String studentId);
}
