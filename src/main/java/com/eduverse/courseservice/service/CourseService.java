package com.eduverse.courseservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduverse.courseservice.dto.CourseDto;
import com.eduverse.courseservice.entity.Course;
import com.eduverse.courseservice.exception.ResourceNotFoundException;
import com.eduverse.courseservice.mapper.CourseMapper;
import com.eduverse.courseservice.repository.CourseRepository;

@Service
@Transactional
public class CourseService {
    
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }
    
    public CourseDto createCourse(CourseDto courseDto) {
        log.info("Creating course: {}", courseDto.getTitle());
        // Ensure client-provided ID does not interfere with IDENTITY generation
        courseDto.setId(null);

        Course course = courseMapper.toEntity(courseDto);
        // Defensive defaulting in case auditing doesn't populate createdAt immediately
        if (course.getCreatedAt() == null) {
            course.setCreatedAt(LocalDateTime.now());
        }
        if (course.getAverageRating() == null) {
            course.setAverageRating(0.0);
        }
        Course savedCourse = courseRepository.save(course);
        
        log.info("Course created successfully with ID: {}", savedCourse.getId());
        return courseMapper.toDto(savedCourse);
    }
    
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        log.info("Updating course with ID: {}", id);
        
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
        
        // Update fields
        existingCourse.setTitle(courseDto.getTitle());
        existingCourse.setDescription(courseDto.getDescription());
        existingCourse.setPrice(courseDto.getPrice());
        existingCourse.setCoverPhotoUrl(courseDto.getCoverPhotoUrl());
        existingCourse.setLevel(courseDto.getLevel());
        existingCourse.setTopic(courseDto.getTopic());
        
        Course updatedCourse = courseRepository.save(existingCourse);
        
        log.info("Course updated successfully with ID: {}", updatedCourse.getId());
        return courseMapper.toDto(updatedCourse);
    }
    
    public void deleteCourse(Long id) {
        log.info("Deleting course with ID: {}", id);
        
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with ID: " + id);
        }
        
        courseRepository.deleteById(id);
        log.info("Course deleted successfully with ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        log.info("Fetching all courses");
        
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDtos = courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
        
        log.info("Found {} courses", courseDtos.size());
        return courseDtos;
    }
    
    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        log.info("Fetching course with ID: {}", id);
        
        Course course = courseRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
        
        return courseMapper.toDto(course);
    }
    
    @Transactional(readOnly = true)
    public List<CourseDto> getCoursesByInstructorId(String instructorId) {
        log.info("Fetching courses for instructor: {}", instructorId);

        // Step 1: Fetch courses without collections
        List<Course> courses = courseRepository.findAllByInstructorId(instructorId);

        // Step 2: If courses exist, fetch collections in a batch to avoid N+1 problem
        if (!courses.isEmpty()) {
            // Initialize collections within the transaction to avoid N+1 issues.
            // Accessing the size of each collection will trigger lazy loading.
            for (Course course : courses) {
                // Accessing size will trigger lazy loading for each collection
                course.getLessons().size();
                course.getOutcomes().size();
                course.getEnrollments().size();
                course.getReviews().size();
            }
        }

        List<CourseDto> courseDtos = courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());

        log.info("Found {} courses for instructor: {}", courseDtos.size(), instructorId);
        return courseDtos;
    }
    
    @Transactional(readOnly = true)
    public List<CourseDto> getCoursesByTopic(String topic) {
        log.info("Fetching courses for topic: {}", topic);
        
        List<Course> courses = courseRepository.findByTopic(topic);
        List<CourseDto> courseDtos = courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
        
        log.info("Found {} courses for topic: {}", courseDtos.size(), topic);
        return courseDtos;
    }
    
    // Search and Filter Methods
    
    @Transactional(readOnly = true)
    public List<CourseDto> searchCourses(String query) {
        log.info("Searching courses with query: {}", query);
        if (query == null || query.trim().isEmpty()) {
            return getAllCourses();
        }
        List<Course> courses = courseRepository.searchByKeyword(query.trim());
        List<CourseDto> courseDtos = courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
        
        log.info("Found {} courses for search query: {}", courseDtos.size(), query);
        return courseDtos;
    }
    
    @Transactional(readOnly = true)
    public List<CourseDto> filterCourses(String level, Double minPrice, Double maxPrice, 
                                       String instructorId, String sortBy, String sortOrder) {
        log.info("Filtering courses with level: {}, priceRange: {}-{}, instructor: {}, sort: {} {}", 
                level, minPrice, maxPrice, instructorId, sortBy, sortOrder);
        
        List<Course> courses = courseRepository.findWithFilters(level, minPrice, maxPrice, instructorId);
        List<CourseDto> courseDtos = courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
        
        // Apply sorting
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            courseDtos = applySorting(courseDtos, sortBy, sortOrder);
        }
        
        log.info("Found {} courses after filtering", courseDtos.size());
        return courseDtos;
    }
    
    @Transactional(readOnly = true)
    public List<CourseDto> searchAndFilter(String query, String level, Double minPrice, 
                                         Double maxPrice, String instructorId, String sortBy, String sortOrder) {
        log.info("Search and filter with query: {}, level: {}, priceRange: {}-{}, instructor: {}, sort: {} {}", 
                query, level, minPrice, maxPrice, instructorId, sortBy, sortOrder);
        
        if (query == null || query.trim().isEmpty()) {
            return filterCourses(level, minPrice, maxPrice, instructorId, sortBy, sortOrder);
        }
        
        List<Course> courses = courseRepository.searchAndFilter(query.trim(), level, minPrice, maxPrice, instructorId);
        List<CourseDto> courseDtos = courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
        
        // Apply sorting
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            courseDtos = applySorting(courseDtos, sortBy, sortOrder);
        }
        
        log.info("Found {} courses after search and filter", courseDtos.size());
        return courseDtos;
    }
    
    private List<CourseDto> applySorting(List<CourseDto> courses, String sortBy, String sortOrder) {
        boolean isAscending = "asc".equalsIgnoreCase(sortOrder);
        
        switch (sortBy.toLowerCase()) {
            case "title":
                courses.sort((c1, c2) -> isAscending ? 
                    c1.getTitle().compareToIgnoreCase(c2.getTitle()) : 
                    c2.getTitle().compareToIgnoreCase(c1.getTitle()));
                break;
            case "price":
                courses.sort((c1, c2) -> isAscending ? 
                    Double.compare(c1.getPrice(), c2.getPrice()) : 
                    Double.compare(c2.getPrice(), c1.getPrice()));
                break;
            case "createdat":
            case "created_at":
                courses.sort((c1, c2) -> isAscending ? 
                    c1.getCreatedAt().compareTo(c2.getCreatedAt()) : 
                    c2.getCreatedAt().compareTo(c1.getCreatedAt()));
                break;
            case "averagerating":
            case "average_rating":
                courses.sort((c1, c2) -> {
                    Double rating1 = c1.getAverageRating() != null ? c1.getAverageRating() : 0.0;
                    Double rating2 = c2.getAverageRating() != null ? c2.getAverageRating() : 0.0;
                    return isAscending ? 
                        Double.compare(rating1, rating2) : 
                        Double.compare(rating2, rating1);
                });
                break;
            case "level":
                courses.sort((c1, c2) -> isAscending ? 
                    c1.getLevel().name().compareToIgnoreCase(c2.getLevel().name()) : 
                    c2.getLevel().name().compareToIgnoreCase(c1.getLevel().name()));
                break;
            default:
                // Default sort by createdAt desc
                courses.sort((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()));
        }
        
        return courses;
    }
}
