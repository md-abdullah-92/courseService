package com.eduverse.courseservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduverse.courseservice.dto.BulkLessonsRequest;
import com.eduverse.courseservice.dto.BulkLessonsResponse;
import com.eduverse.courseservice.dto.LessonDto;
import com.eduverse.courseservice.service.LessonService;

@RestController
@RequestMapping("/api/lessons")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // Add a lesson to a course
    @PostMapping("/add/{courseId}")
    public ResponseEntity<Object> addLesson(@PathVariable Long courseId, @RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            String description = (String) request.get("description");
            String videoUrl = (String) request.get("videoUrl");
            String notes = (String) request.get("notes");
            Integer orderIndex = (Integer) request.get("orderIndex");
            
            LessonDto lessonDto = new LessonDto();
            lessonDto.setTitle(title);
            lessonDto.setDescription(description);
            lessonDto.setVideoUrl(videoUrl);
            lessonDto.setNotes(notes);
            lessonDto.setOrderIndex(orderIndex);
            
            LessonDto createdLesson = lessonService.addLesson(courseId, lessonDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLesson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to add lesson",
                "details", e.getMessage()
            ));
        }
    }

    // Get all lessons of a course
    @GetMapping("/get/{courseId}")
    public ResponseEntity<Object> getCourseLessons(@PathVariable Long courseId) {
        try {
            List<LessonDto> lessons = lessonService.getCourseLessons(courseId);
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to fetch lessons",
                "details", e.getMessage()
            ));
        }
    }

    // Get lesson by ID
    @GetMapping("/get/lesson/{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long id) {
        LessonDto lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(lesson);
    }

    // Update a lesson by ID
    @PutMapping("/update/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable Long lessonId, @RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            String description = (String) request.get("description");
            String videoUrl = (String) request.get("videoUrl");
            String notes = (String) request.get("notes");
            Integer orderIndex = (Integer) request.get("orderIndex");
            
            LessonDto lessonDto = new LessonDto();
            lessonDto.setTitle(title);
            lessonDto.setDescription(description);
            lessonDto.setVideoUrl(videoUrl);
            lessonDto.setNotes(notes);
            lessonDto.setOrderIndex(orderIndex);
            
            LessonDto updatedLesson = lessonService.updateLesson(lessonId, lessonDto);
            return ResponseEntity.ok(updatedLesson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to update lesson",
                "details", e.getMessage()
            ));
        }
    }

    // Delete a lesson by ID
    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable Long lessonId) {
        try {
            lessonService.deleteLesson(lessonId);
            return ResponseEntity.ok(Map.of("message", "Lesson deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to delete lesson",
                "details", e.getMessage()
            ));
        }
    }

    // Bulk update lessons for a course
    @PutMapping("/bulk/{courseId}")
    public ResponseEntity<Object> bulkUpdateLessons(
            @PathVariable Long courseId, 
            @RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> lessons = (List<Map<String, Object>>) request.get("lessons");
            
            if (lessons == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Lessons array is required"));
            }
            
            BulkLessonsRequest bulkRequest = new BulkLessonsRequest();
            bulkRequest.setLessons(lessons.stream().map(lessonMap -> {
                LessonDto lessonDto = new LessonDto();
                if (lessonMap.get("id") != null) {
                    lessonDto.setId(Long.valueOf(lessonMap.get("id").toString()));
                }
                lessonDto.setTitle((String) lessonMap.get("title"));
                lessonDto.setDescription((String) lessonMap.get("description"));
                lessonDto.setVideoUrl((String) lessonMap.get("videoUrl"));
                lessonDto.setNotes((String) lessonMap.get("notes"));
                if (lessonMap.get("orderIndex") != null) {
                    lessonDto.setOrderIndex(Integer.valueOf(lessonMap.get("orderIndex").toString()));
                }
                return lessonDto;
            }).collect(java.util.stream.Collectors.toList()));
            
            BulkLessonsResponse response = lessonService.bulkUpdateLessons(courseId, bulkRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to bulk update lessons",
                "details", e.getMessage()
            ));
        }
    }

    // Mark lesson as completed
    @PostMapping("/mark-completed/{lessonId}/{enrollmentId}")
    public ResponseEntity<Object> markLessonCompleted(
            @PathVariable Long lessonId, 
            @PathVariable Long enrollmentId) {
        try {
            Map<String, Object> response = lessonService.markLessonCompleted(lessonId, enrollmentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to mark lesson as completed",
                "details", e.getMessage()
            ));
        }
    }
}
