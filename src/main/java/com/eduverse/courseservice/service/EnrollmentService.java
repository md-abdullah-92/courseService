package com.eduverse.courseservice.service;

import com.eduverse.courseservice.dto.EnrollmentDTO;
import com.eduverse.courseservice.entity.Course;
import com.eduverse.courseservice.entity.Enrollment;
import com.eduverse.courseservice.mapper.EnrollmentMapper;
import com.eduverse.courseservice.repository.CourseRepository;
import com.eduverse.courseservice.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, 
                           CourseRepository courseRepository, 
                           EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Transactional
    public EnrollmentDTO enrollStudent(String studentId, Long courseId) {
        // Check if already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

        // Check if course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setProgressPercentage(0.0);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDTO(savedEnrollment, course);
    }

    public List<EnrollmentDTO> getStudentEnrollments(String studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(enrollment -> {
                    Optional<Course> course = courseRepository.findById(enrollment.getCourseId());
                    return course.map(c -> enrollmentMapper.toDTO(enrollment, c))
                            .orElse(enrollmentMapper.toDTO(enrollment));
                })
                .toList();
    }

    public List<EnrollmentDTO> getCourseEnrollments(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        Optional<Course> course = courseRepository.findById(courseId);
        
        return enrollments.stream()
                .map(enrollment -> course.map(c -> enrollmentMapper.toDTO(enrollment, c))
                        .orElse(enrollmentMapper.toDTO(enrollment)))
                .toList();
    }

    @Transactional
    public EnrollmentDTO updateProgress(String studentId, Long courseId, Double progress) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setProgressPercentage(Math.min(100.0, Math.max(0.0, progress)));

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        Optional<Course> course = courseRepository.findById(courseId);
        
        return course.map(c -> enrollmentMapper.toDTO(savedEnrollment, c))
                .orElse(enrollmentMapper.toDTO(savedEnrollment));
    }

    public Long getCourseEnrollmentCount(Long courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }

    public Long getStudentCompletedCoursesCount(String studentId) {
        return enrollmentRepository.countCompletedCoursesByStudentId(studentId);
    }

    @Transactional
    public void unenrollStudent(String studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        
        enrollmentRepository.delete(enrollment);
    }

    public boolean isStudentEnrolled(String studentId, Long courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }
}
