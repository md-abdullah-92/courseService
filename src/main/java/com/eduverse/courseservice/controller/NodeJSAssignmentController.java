package com.eduverse.courseservice.controller;

import com.eduverse.courseservice.dto.NodeJSAssignmentDTO;
import com.eduverse.courseservice.service.NodeJSAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
@CrossOrigin(origins = "*")
public class NodeJSAssignmentController {

    @Autowired
    private NodeJSAssignmentService assignmentService;

    @PostMapping("/")
    public ResponseEntity<NodeJSAssignmentDTO> createAssignment(@RequestBody NodeJSAssignmentDTO assignmentDTO) {
        try {
            NodeJSAssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
            return ResponseEntity.ok(createdAssignment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<NodeJSAssignmentDTO>> getAssignmentsByLesson(@PathVariable Long lessonId) {
        try {
            List<NodeJSAssignmentDTO> assignments = assignmentService.getAssignmentsByLesson(lessonId);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        try {
            assignmentService.deleteAssignment(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
