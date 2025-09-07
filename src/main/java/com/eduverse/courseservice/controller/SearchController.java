package com.eduverse.courseservice.controller;

import com.eduverse.courseservice.dto.CourseDto;
import com.eduverse.courseservice.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final CourseService courseService;

    public SearchController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity<Map<String, Object>> searchCourses(@RequestParam(required = false) String query) {
        try {
            log.info("Search request received with query: {}", query);
            List<CourseDto> courses = courseService.searchCourses(query);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", courses,
                    "count", courses.size()
            ));
        } catch (Exception e) {
            log.error("Error searching courses: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Search failed",
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> filterCourses(
            @RequestParam(required = false) String level,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String instructorId,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        try {
            log.info("Filter request received with level: {}, priceRange: {}-{}, instructor: {}, sort: {} {}", 
                    level, minPrice, maxPrice, instructorId, sortBy, sortOrder);
            
            List<CourseDto> courses = courseService.filterCourses(level, minPrice, maxPrice, instructorId, sortBy, sortOrder);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", courses,
                    "count", courses.size(),
                    "filters", Map.of(
                            "level", level != null ? level : "all",
                            "minPrice", minPrice != null ? minPrice : "no_min",
                            "maxPrice", maxPrice != null ? maxPrice : "no_max",
                            "instructorId", instructorId != null ? instructorId : "all",
                            "sortBy", sortBy,
                            "sortOrder", sortOrder
                    )
            ));
        } catch (Exception e) {
            log.error("Error filtering courses: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Filtering failed",
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/combined")
    public ResponseEntity<Map<String, Object>> searchAndFilter(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String instructorId,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        try {
            log.info("Combined search and filter request with query: {}, level: {}, priceRange: {}-{}, instructor: {}, sort: {} {}", 
                    query, level, minPrice, maxPrice, instructorId, sortBy, sortOrder);
            
            List<CourseDto> courses = courseService.searchAndFilter(query, level, minPrice, maxPrice, instructorId, sortBy, sortOrder);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", courses,
                    "count", courses.size(),
                    "searchQuery", query != null ? query : "",
                    "filters", Map.of(
                            "level", level != null ? level : "all",
                            "minPrice", minPrice != null ? minPrice : "no_min",
                            "maxPrice", maxPrice != null ? maxPrice : "no_max",
                            "instructorId", instructorId != null ? instructorId : "all",
                            "sortBy", sortBy,
                            "sortOrder", sortOrder
                    )
            ));
        } catch (Exception e) {
            log.error("Error in combined search and filter: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Combined search and filter failed",
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/suggestions")
    public ResponseEntity<Map<String, Object>> getSearchSuggestions(@RequestParam String query) {
        try {
            log.info("Search suggestions requested for query: {}", query);
            // For now, we'll return courses that match the query
            // In a real implementation, you might want to return just titles or keywords
            List<CourseDto> courses = courseService.searchCourses(query);
            List<String> suggestions = courses.stream()
                    .map(CourseDto::getTitle)
                    .distinct()
                    .limit(10)
                    .toList();
            
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "suggestions", suggestions,
                    "count", suggestions.size()
            ));
        } catch (Exception e) {
            log.error("Error getting search suggestions: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to get suggestions",
                    "error", e.getMessage()
            ));
        }
    }
}
