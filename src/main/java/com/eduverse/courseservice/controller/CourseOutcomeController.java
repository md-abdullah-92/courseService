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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduverse.courseservice.dto.CourseOutcomeDto;
import com.eduverse.courseservice.service.CourseOutcomeService;

@RestController
@RequestMapping("/api/outcomes")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class CourseOutcomeController {

    private final CourseOutcomeService outcomeService;

    public CourseOutcomeController(CourseOutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    // Add an outcome to a course
    @PostMapping("/add/{courseId}")
    public ResponseEntity<Object> addOutcome(@PathVariable Long courseId, @RequestBody Map<String, Object> request) {
        try {
            String outcome = (String) request.get("outcome");
            CourseOutcomeDto outcomeDto = new CourseOutcomeDto();
            outcomeDto.setOutcome(outcome);
            
            CourseOutcomeDto createdOutcome = outcomeService.addOutcome(courseId, outcomeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOutcome);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to add outcome",
                "details", e.getMessage()
            ));
        }
    }

    // Get all outcomes for a course
    @GetMapping("/get/{courseId}")
    public ResponseEntity<Object> getCourseOutcomes(@PathVariable Long courseId) {
        try {
            List<CourseOutcomeDto> outcomes = outcomeService.getCourseOutcomes(courseId);
            return ResponseEntity.ok(outcomes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to fetch outcomes",
                "details", e.getMessage()
            ));
        }
    }

    // Delete an outcome by ID
    @DeleteMapping("/delete/{outcomeId}")
    public ResponseEntity<Object> deleteOutcome(@PathVariable Long outcomeId) {
        try {
            outcomeService.deleteOutcome(outcomeId);
            return ResponseEntity.ok(Map.of("message", "Outcome deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Failed to delete outcome",
                "details", e.getMessage()
            ));
        }
    }
}
