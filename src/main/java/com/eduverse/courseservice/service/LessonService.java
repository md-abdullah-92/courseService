package com.eduverse.courseservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduverse.courseservice.dto.BulkLessonsRequest;
import com.eduverse.courseservice.dto.BulkLessonsResponse;
import com.eduverse.courseservice.dto.LessonDto;
import com.eduverse.courseservice.entity.Lesson;
import com.eduverse.courseservice.entity.LessonCompletion;
import com.eduverse.courseservice.exception.ResourceNotFoundException;
import com.eduverse.courseservice.mapper.LessonMapper;
import com.eduverse.courseservice.repository.CourseRepository;
import com.eduverse.courseservice.repository.LessonCompletionRepository;
import com.eduverse.courseservice.repository.LessonRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class LessonService {

    private static final Logger log = LoggerFactory.getLogger(LessonService.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonCompletionRepository lessonCompletionRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository, 
                        LessonCompletionRepository lessonCompletionRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.lessonCompletionRepository = lessonCompletionRepository;
        this.lessonMapper = lessonMapper;
    }

    public LessonDto getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
        return lessonMapper.toDto(lesson);
    }

    public LessonDto updateLesson(Long id, LessonDto lessonDto) {
        Lesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));

        // Update fields from DTO
        if (lessonDto.getTitle() != null) {
            existingLesson.setTitle(lessonDto.getTitle());
        }
        if (lessonDto.getDescription() != null) {
            existingLesson.setDescription(lessonDto.getDescription());
        }
        if (lessonDto.getVideoUrl() != null) {
            existingLesson.setVideoUrl(lessonDto.getVideoUrl());
        }
        if (lessonDto.getOrderIndex() != null) {
            existingLesson.setOrderIndex(lessonDto.getOrderIndex());
        }

        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return lessonMapper.toDto(updatedLesson);
    }

    public LessonDto addLesson(Long courseId, LessonDto lessonDto) {
        log.info("Adding lesson to course: {}", courseId);
        
        // Verify course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setVideoUrl(lessonDto.getVideoUrl());
        lesson.setNotes(lessonDto.getNotes());
        lesson.setOrderIndex(lessonDto.getOrderIndex());
        lesson.setCourseId(courseId);

        Lesson savedLesson = lessonRepository.save(lesson);
        log.info("Lesson saved with ID: {}", savedLesson.getId());
        
        // Flush to ensure immediate persistence
        entityManager.flush();
        
        return lessonMapper.toDto(savedLesson);
    }

    public List<LessonDto> getCourseLessons(Long courseId) {
        log.info("Fetching lessons for course: {}", courseId);
        
        // Verify course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        List<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
        log.info("Found {} lessons for course {}", lessons.size(), courseId);
        
        return lessons.stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + lessonId));
        
        lessonRepository.delete(lesson);
    }

    @Transactional
    public BulkLessonsResponse bulkUpdateLessons(Long courseId, BulkLessonsRequest request) {
        // Verify course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        if (request.getLessons() == null || request.getLessons().isEmpty()) {
            throw new IllegalArgumentException("Lessons array is required");
        }

        // Get existing lessons for this course
        List<Lesson> existingLessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
        Map<Long, Lesson> existingLessonMap = existingLessons.stream()
                .collect(Collectors.toMap(Lesson::getId, lesson -> lesson));

        Set<Long> incomingLessonIds = request.getLessons().stream()
                .filter(l -> l.getId() != null)
                .map(LessonDto::getId)
                .collect(Collectors.toSet());

        List<LessonDto> created = new ArrayList<>();
        List<LessonDto> updated = new ArrayList<>();
        List<BulkLessonsResponse.DeletedLessonInfo> deleted = new ArrayList<>();

        // Process incoming lessons
        for (LessonDto lessonDto : request.getLessons()) {
            if (lessonDto.getId() != null) {
                // Update existing lesson
                Long lessonId = lessonDto.getId();
                if (existingLessonMap.containsKey(lessonId)) {
                    Lesson existingLesson = existingLessonMap.get(lessonId);
                    
        existingLesson.setTitle(lessonDto.getTitle());
        existingLesson.setDescription(lessonDto.getDescription());
        existingLesson.setVideoUrl(lessonDto.getVideoUrl());
        existingLesson.setNotes(lessonDto.getNotes());
        existingLesson.setOrderIndex(lessonDto.getOrderIndex());                    Lesson updatedLesson = lessonRepository.save(existingLesson);
                    updated.add(lessonMapper.toDto(updatedLesson));
                }
            } else {
                // Create new lesson
                Lesson newLesson = new Lesson();
                newLesson.setTitle(lessonDto.getTitle());
                newLesson.setDescription(lessonDto.getDescription());
                newLesson.setVideoUrl(lessonDto.getVideoUrl());
                newLesson.setOrderIndex(lessonDto.getOrderIndex());
                newLesson.setCourseId(courseId);
                
                Lesson savedLesson = lessonRepository.save(newLesson);
                created.add(lessonMapper.toDto(savedLesson));
            }
        }

        // Delete lessons that are no longer present
        List<Lesson> lessonsToDelete = existingLessons.stream()
                .filter(lesson -> !incomingLessonIds.contains(lesson.getId()))
                .collect(Collectors.toList());

        for (Lesson lesson : lessonsToDelete) {
            deleted.add(new BulkLessonsResponse.DeletedLessonInfo(lesson.getId(), lesson.getTitle()));
            lessonRepository.delete(lesson);
        }

        // Build response
        BulkLessonsResponse.BulkOperationSummary summary = new BulkLessonsResponse.BulkOperationSummary(
            created.size(), updated.size(), deleted.size()
        );

        BulkLessonsResponse.BulkOperationDetails details = new BulkLessonsResponse.BulkOperationDetails(
            created, updated, deleted
        );

        return new BulkLessonsResponse(
            "Lessons updated successfully",
            summary,
            details
        );
    }

    // Mark lesson as completed
    public Map<String, Object> markLessonCompleted(Long lessonId, Long enrollmentId) {
        // Check if lesson exists
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson not found with id: " + lessonId);
        }

        // Check if already completed
        Optional<LessonCompletion> existingCompletion = lessonCompletionRepository
                .findByEnrollmentIdAndLessonId(enrollmentId, lessonId);

        LessonCompletion completion;
        boolean isNew;

        if (existingCompletion.isPresent()) {
            // Already completed
            completion = existingCompletion.get();
            isNew = false;
        } else {
            // Create new completion record
            completion = new LessonCompletion();
            completion.setLessonId(lessonId);
            completion.setEnrollmentId(enrollmentId);
            completion = lessonCompletionRepository.save(completion);
            isNew = true;
        }

        // Prepare response similar to Node.js
        Map<String, Object> response = new HashMap<>();
        response.put("id", completion.getId());
        response.put("lessonId", completion.getLessonId());
        response.put("enrollmentId", completion.getEnrollmentId());
        response.put("completedAt", completion.getCompletedAt());
        response.put("isNew", isNew);

        return response;
    }
}
