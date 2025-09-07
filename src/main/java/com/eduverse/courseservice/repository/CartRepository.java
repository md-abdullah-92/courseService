package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items ci LEFT JOIN FETCH ci.course WHERE c.studentId = :studentId")
    Optional<Cart> findByStudentIdWithItems(@Param("studentId") String studentId);
    
    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.studentId = :studentId")
    Long countItemsByStudentId(@Param("studentId") String studentId);
}
