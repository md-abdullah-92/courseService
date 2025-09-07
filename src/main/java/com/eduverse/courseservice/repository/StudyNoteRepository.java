package com.eduverse.courseservice.repository;

import com.eduverse.courseservice.entity.StudyNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for StudyNote entity
 * Provides comprehensive data access methods for study notes
 */
@Repository
public interface StudyNoteRepository extends JpaRepository<StudyNote, Long> {

    /**
     * Find study notes by lesson ID
     */
    List<StudyNote> findByLessonId(Long lessonId);

    /**
     * Find study notes by user ID
     */
    List<StudyNote> findByUserId(Long userId);

    /**
     * Find public study notes
     */
    List<StudyNote> findByIsPublicTrue();

    /**
     * Find favorite study notes by user ID
     */
    List<StudyNote> findByUserIdAndIsFavoriteTrue(Long userId);

    /**
     * Find study notes by category
     */
    List<StudyNote> findByCategory(String category);

    /**
     * Find study notes by note type
     */
    List<StudyNote> findByNoteType(String noteType);

    /**
     * Find public study notes by lesson ID
     */
    List<StudyNote> findByLessonIdAndIsPublicTrue(Long lessonId);

    /**
     * Search study notes by title containing text (case insensitive)
     */
    @Query("SELECT sn FROM StudyNote sn WHERE LOWER(sn.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<StudyNote> findByTitleContainingIgnoreCase(@Param("title") String title);

    /**
     * Search study notes by content containing text (case insensitive)
     */
    @Query("SELECT sn FROM StudyNote sn WHERE LOWER(sn.content) LIKE LOWER(CONCAT('%', :content, '%'))")
    List<StudyNote> findByContentContainingIgnoreCase(@Param("content") String content);

    /**
     * Search study notes by title or content containing text
     */
    @Query("SELECT sn FROM StudyNote sn WHERE LOWER(sn.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(sn.content) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<StudyNote> searchByTitleOrContent(@Param("searchTerm") String searchTerm);

    /**
     * Find study notes by tags containing specific tag
     */
    @Query("SELECT sn FROM StudyNote sn WHERE sn.tags LIKE CONCAT('%', :tag, '%')")
    List<StudyNote> findByTagsContaining(@Param("tag") String tag);

    /**
     * Find study notes created after specific date
     */
    List<StudyNote> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find recent study notes (last 7 days)
     */
    @Query("SELECT sn FROM StudyNote sn WHERE sn.createdAt > :weekAgo ORDER BY sn.createdAt DESC")
    List<StudyNote> findRecentNotes(@Param("weekAgo") LocalDateTime weekAgo);

    /**
     * Find popular study notes (view count > threshold)
     */
    @Query("SELECT sn FROM StudyNote sn WHERE sn.viewCount > :threshold ORDER BY sn.viewCount DESC")
    List<StudyNote> findPopularNotes(@Param("threshold") Long threshold);

    /**
     * Find most liked study notes
     */
    @Query("SELECT sn FROM StudyNote sn ORDER BY sn.likeCount DESC")
    List<StudyNote> findMostLikedNotes();

    /**
     * Find study notes with attachments
     */
    @Query("SELECT sn FROM StudyNote sn WHERE sn.fileUrl IS NOT NULL AND sn.fileUrl != ''")
    List<StudyNote> findNotesWithAttachments();

    /**
     * Find study notes by lesson ID and user ID
     */
    List<StudyNote> findByLessonIdAndUserId(Long lessonId, Long userId);

    /**
     * Count study notes by user ID
     */
    long countByUserId(Long userId);

    /**
     * Count public study notes by lesson ID
     */
    long countByLessonIdAndIsPublicTrue(Long lessonId);

    /**
     * Find study notes ordered by created date (newest first)
     */
    List<StudyNote> findAllByOrderByCreatedAtDesc();

    /**
     * Find study notes ordered by view count (most viewed first)
     */
    List<StudyNote> findAllByOrderByViewCountDesc();

    /**
     * Find study notes ordered by like count (most liked first)
     */
    List<StudyNote> findAllByOrderByLikeCountDesc();

    /**
     * Get paginated study notes by lesson ID
     */
    Page<StudyNote> findByLessonId(Long lessonId, Pageable pageable);

    /**
     * Get paginated study notes by user ID
     */
    Page<StudyNote> findByUserId(Long userId, Pageable pageable);

    /**
     * Get paginated public study notes
     */
    Page<StudyNote> findByIsPublicTrue(Pageable pageable);

    /**
     * Check if study note exists by title and lesson ID
     */
    boolean existsByTitleAndLessonId(String title, Long lessonId);

    /**
     * Check if study note exists by title and user ID
     */
    boolean existsByTitleAndUserId(String title, Long userId);

    /**
     * Find study notes by multiple filters
     */
    @Query("SELECT sn FROM StudyNote sn WHERE " +
           "(:lessonId IS NULL OR sn.lessonId = :lessonId) AND " +
           "(:userId IS NULL OR sn.userId = :userId) AND " +
           "(:category IS NULL OR sn.category = :category) AND " +
           "(:noteType IS NULL OR sn.noteType = :noteType) AND " +
           "(:isPublic IS NULL OR sn.isPublic = :isPublic)")
    List<StudyNote> findByFilters(@Param("lessonId") Long lessonId,
                                  @Param("userId") Long userId,
                                  @Param("category") String category,
                                  @Param("noteType") String noteType,
                                  @Param("isPublic") Boolean isPublic);
}
