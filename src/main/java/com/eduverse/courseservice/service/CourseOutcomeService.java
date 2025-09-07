package com.eduverse.courseservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduverse.courseservice.dto.CourseOutcomeDto;
import com.eduverse.courseservice.entity.CourseOutcome;
import com.eduverse.courseservice.exception.ResourceNotFoundException;
import com.eduverse.courseservice.repository.CourseOutcomeRepository;
import com.eduverse.courseservice.repository.CourseRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class CourseOutcomeService {

    private static final Logger log = LoggerFactory.getLogger(CourseOutcomeService.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    private final CourseOutcomeRepository outcomeRepository;
    private final CourseRepository courseRepository;

    public CourseOutcomeService(CourseOutcomeRepository outcomeRepository, CourseRepository courseRepository) {
        this.outcomeRepository = outcomeRepository;
        this.courseRepository = courseRepository;
    }

    public CourseOutcomeDto addOutcome(Long courseId, CourseOutcomeDto outcomeDto) {
        log.info("Adding outcome to course: {}", courseId);
        
        // Verify course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        CourseOutcome outcome = new CourseOutcome();
        outcome.setCourseId(courseId);
        outcome.setOutcome(outcomeDto.getOutcome());

        CourseOutcome savedOutcome = outcomeRepository.save(outcome);
        log.info("Outcome saved with ID: {}", savedOutcome.getId());
        
        // Flush to ensure immediate persistence
        entityManager.flush();
        
        return toDto(savedOutcome);
    }

    public List<CourseOutcomeDto> getCourseOutcomes(Long courseId) {
        log.info("Fetching outcomes for course: {}", courseId);
        
        // Verify course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        List<CourseOutcome> outcomes = outcomeRepository.findByCourseId(courseId);
        log.info("Found {} outcomes for course {}", outcomes.size(), courseId);
        
        return outcomes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteOutcome(Long outcomeId) {
        CourseOutcome outcome = outcomeRepository.findById(outcomeId)
                .orElseThrow(() -> new ResourceNotFoundException("Outcome not found with id: " + outcomeId));
        
        outcomeRepository.delete(outcome);
    }

    private CourseOutcomeDto toDto(CourseOutcome outcome) {
        CourseOutcomeDto dto = new CourseOutcomeDto();
        dto.setId(outcome.getId());
        dto.setOutcome(outcome.getOutcome());
        dto.setCourseId(outcome.getCourseId());
        return dto;
    }
}
