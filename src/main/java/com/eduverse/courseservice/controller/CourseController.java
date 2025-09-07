package com.eduverse.courseservice.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.eduverse.courseservice.dto.CourseDto;
import com.eduverse.courseservice.enums.CourseLevel;
import com.eduverse.courseservice.service.CourseService;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class CourseController {
    
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    
    private final CourseService courseService;
    
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<Object> createCourse(@RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            String description = (String) request.get("description");
            Object priceObj = request.get("price");
            String coverPhotoUrl = (String) request.get("coverPhotoUrl");
            String level = (String) request.get("level");
            String instructorId = (String) request.get("instructorId");
            String topic = (String) request.get("topic");
            
            CourseDto courseDto = new CourseDto();
            courseDto.setTitle(title);
            courseDto.setDescription(description);
            if (priceObj != null) {
                courseDto.setPrice(Double.valueOf(priceObj.toString()));
            }
            courseDto.setCoverPhotoUrl(coverPhotoUrl);
            if (level != null && !level.isEmpty()) {
                courseDto.setLevel(CourseLevel.valueOf(level.toUpperCase()));
            }
            courseDto.setInstructorId(instructorId);
            courseDto.setTopic(topic);
            
            log.info("Creating new course: {}", title);
            CourseDto createdCourse = courseService.createCourse(courseDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (Exception e) {
            log.error("Error creating course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", "Error creating course",
                "error", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCourse(
            @PathVariable Long id, 
            @RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            String description = (String) request.get("description");
            Object priceObj = request.get("price");
            String coverPhotoUrl = (String) request.get("coverPhotoUrl");
            String level = (String) request.get("level");
            String topic = (String) request.get("topic");
            
            CourseDto courseDto = new CourseDto();
            courseDto.setTitle(title);
            courseDto.setDescription(description);
            if (priceObj != null) {
                courseDto.setPrice(Double.valueOf(priceObj.toString()));
            }
            courseDto.setCoverPhotoUrl(coverPhotoUrl);
            if (level != null && !level.isEmpty()) {
                courseDto.setLevel(CourseLevel.valueOf(level.toUpperCase()));
            }
            courseDto.setTopic(topic);
            
            log.info("Updating course with ID: {}", id);
            CourseDto updatedCourse = courseService.updateCourse(id, courseDto);
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            log.error("Error updating course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", "Error updating course",
                "error", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable Long id) {
        try {
            log.info("Deleting course with ID: {}", id);
            courseService.deleteCourse(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("Error deleting course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", "Error deleting course",
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<Object> getAllCourses() {
        try {
            log.info("Fetching all courses");
            List<CourseDto> courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("Error fetching all courses: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", "Error fetching courses",
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getCourseById(@PathVariable Long id) {
        try {
            log.info("Fetching course with ID: {}", id);
            CourseDto course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "Course not found"
                ));
            }
            throw e;
        } catch (Exception e) {
            log.error("Error fetching course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", "Error fetching course",
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/getByInstructorId/{instructorId}")
    public ResponseEntity<Object> getCoursesByInstructorId(@PathVariable String instructorId) {
        try {
            log.info("Fetching courses for instructor: {}", instructorId);
            List<CourseDto> courses = courseService.getCoursesByInstructorId(instructorId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("Error fetching instructor courses: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to fetch instructor courses",
                "details", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/getByTopic/{topic}")
    public ResponseEntity<Object> getCoursesByTopic(@PathVariable String topic) {
        try {
            log.info("Fetching courses for topic: {}", topic);
            List<CourseDto> courses = courseService.getCoursesByTopic(topic);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("Error fetching courses by topic: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to fetch courses by topic",
                "details", e.getMessage()
            ));
        }
    }
}
