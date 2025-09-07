package com.eduverse.courseservice.service;

import com.eduverse.courseservice.dto.NodeJSAssignmentDTO;
import com.eduverse.courseservice.entity.Assignment;
import com.eduverse.courseservice.entity.Lesson;
import com.eduverse.courseservice.repository.AssignmentRepository;
import com.eduverse.courseservice.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeJSAssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public NodeJSAssignmentDTO createAssignment(NodeJSAssignmentDTO assignmentDTO) {
        // Verify lesson exists
        Lesson lesson = lessonRepository.findById(assignmentDTO.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        Assignment assignment = new Assignment();
        assignment.setTitle(assignmentDTO.getTitle());
        assignment.setDescription(assignmentDTO.getDescription());
        assignment.setLesson(lesson);

        Assignment savedAssignment = assignmentRepository.save(assignment);

        return convertToDTO(savedAssignment, assignmentDTO.getTeacherId());
    }

    public List<NodeJSAssignmentDTO> getAssignmentsByLesson(Long lessonId) {
        List<Assignment> assignments = assignmentRepository.findByLessonId(lessonId);
        return assignments.stream()
                .map(assignment -> convertToDTO(assignment, null))
                .collect(Collectors.toList());
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    private NodeJSAssignmentDTO convertToDTO(Assignment assignment, Long teacherId) {
        return new NodeJSAssignmentDTO(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                teacherId,
                assignment.getLesson().getId()
        );
    }
}
