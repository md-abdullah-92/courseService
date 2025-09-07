package com.eduverse.courseservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eduverse.courseservice.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    List<Course> findByInstructorId(String instructorId);

    @Query("SELECT c FROM Course c WHERE c.instructorId = :instructorId")
    List<Course> findAllByInstructorId(@Param("instructorId") String instructorId);
    
    List<Course> findByTopic(String topic);
    
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.lessons LEFT JOIN FETCH c.outcomes WHERE c.id = :id")
    Optional<Course> findByIdWithDetails(@Param("id") Long id);
    
    @Query("SELECT c FROM Course c WHERE c.averageRating >= :minRating ORDER BY c.averageRating DESC")
    List<Course> findByAverageRatingGreaterThanEqualOrderByAverageRatingDesc(@Param("minRating") Double minRating);
    
    @Query("SELECT c FROM Course c WHERE c.price BETWEEN :minPrice AND :maxPrice")
    List<Course> findByPriceBetween(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
    @Query("SELECT c FROM Course c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Course> searchByKeyword(@Param("keyword") String keyword);
    
    // Advanced search and filter methods
    @Query("SELECT c FROM Course c WHERE " +
           "(:level IS NULL OR c.level = :level) AND " +
           "(:minPrice IS NULL OR c.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
           "(:instructorId IS NULL OR c.instructorId = :instructorId)")
    List<Course> findWithFilters(
        @Param("level") String level,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("instructorId") String instructorId
    );
    
    @Query("SELECT c FROM Course c WHERE " +
           "(LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:level IS NULL OR c.level = :level) AND " +
           "(:minPrice IS NULL OR c.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
           "(:instructorId IS NULL OR c.instructorId = :instructorId)")
    List<Course> searchAndFilter(
        @Param("query") String query,
        @Param("level") String level,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("instructorId") String instructorId
    );
}
