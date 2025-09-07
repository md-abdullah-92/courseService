package com.eduverse.courseservice.service;

import com.eduverse.courseservice.dto.StudentQuestionDTO;
import com.eduverse.courseservice.dto.TeacherAnswerDTO;
import com.eduverse.courseservice.entity.StudentQuestion;
import com.eduverse.courseservice.entity.TeacherAnswer;
import com.eduverse.courseservice.exception.ResourceNotFoundException;
import com.eduverse.courseservice.mapper.StudentQuestionMapper;
import com.eduverse.courseservice.mapper.TeacherAnswerMapper;
import com.eduverse.courseservice.repository.StudentQuestionRepository;
import com.eduverse.courseservice.repository.TeacherAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class for Q&A operations
 * Provides comprehensive student question and teacher answer management functionality
 */
@Service
@Transactional
public class QnaService {

    private static final Logger log = LoggerFactory.getLogger(QnaService.class);
    
    private final StudentQuestionRepository studentQuestionRepository;
    private final TeacherAnswerRepository teacherAnswerRepository;
    private final StudentQuestionMapper studentQuestionMapper;
    private final TeacherAnswerMapper teacherAnswerMapper;

    public QnaService(StudentQuestionRepository studentQuestionRepository,
                      TeacherAnswerRepository teacherAnswerRepository,
                      StudentQuestionMapper studentQuestionMapper,
                      TeacherAnswerMapper teacherAnswerMapper) {
        this.studentQuestionRepository = studentQuestionRepository;
        this.teacherAnswerRepository = teacherAnswerRepository;
        this.studentQuestionMapper = studentQuestionMapper;
        this.teacherAnswerMapper = teacherAnswerMapper;
    }

    /**
     * Ask a new question (Student functionality)
     */
    public StudentQuestionDTO askQuestion(StudentQuestionDTO questionDTO) {
        log.info("Student {} asking question for course {}", questionDTO.getStudentId(), questionDTO.getCourseId());
        
        StudentQuestion question = studentQuestionMapper.toEntity(questionDTO);
        question.setId(UUID.randomUUID().toString());
        question.setIsAnswered(false);
        
        StudentQuestion savedQuestion = studentQuestionRepository.save(question);
        return studentQuestionMapper.toDto(savedQuestion);
    }

    /**
     * Get all questions for a course
     */
    @Transactional(readOnly = true)
    public List<StudentQuestionDTO> getQuestionsByCourse(Long courseId) {
        log.info("Fetching questions for course {}", courseId);
        
        List<StudentQuestion> questions = studentQuestionRepository.findByCourseIdOrderByCreatedAtDesc(courseId);
        List<StudentQuestionDTO> questionDTOs = studentQuestionMapper.toDtoList(questions);
        
        // Load answers for each question
        for (StudentQuestionDTO questionDTO : questionDTOs) {
            teacherAnswerRepository.findByQuestionId(questionDTO.getId())
                    .ifPresent(answer -> questionDTO.setAnswer(teacherAnswerMapper.toDto(answer)));
        }
        
        return questionDTOs;
    }

    /**
     * Get a single question by ID
     */
    @Transactional(readOnly = true)
    public StudentQuestionDTO getQuestionById(String questionId) {
        log.info("Fetching question with id: {}", questionId);
        
        StudentQuestion question = studentQuestionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
        
        StudentQuestionDTO questionDTO = studentQuestionMapper.toDto(question);
        
        // Load answer if available
        teacherAnswerRepository.findByQuestionId(questionId)
                .ifPresent(answer -> questionDTO.setAnswer(teacherAnswerMapper.toDto(answer)));
        
        return questionDTO;
    }

    /**
     * Submit an answer (Teacher functionality)
     */
    public TeacherAnswerDTO answerQuestion(TeacherAnswerDTO answerDTO) {
        log.info("Teacher {} answering question {}", answerDTO.getTeacherId(), answerDTO.getQuestionId());
        
        // Check if question exists
        StudentQuestion question = studentQuestionRepository.findById(answerDTO.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + answerDTO.getQuestionId()));
        
        // Check if already answered
        if (teacherAnswerRepository.existsByQuestionId(answerDTO.getQuestionId())) {
            throw new IllegalStateException("This question has already been answered");
        }
        
        // Create and save answer
        TeacherAnswer answer = teacherAnswerMapper.toEntity(answerDTO);
        TeacherAnswer savedAnswer = teacherAnswerRepository.save(answer);
        
        // Mark question as answered
        question.setIsAnswered(true);
        studentQuestionRepository.save(question);
        
        log.info("Question {} marked as answered", answerDTO.getQuestionId());
        return teacherAnswerMapper.toDto(savedAnswer);
    }

    /**
     * Get all unanswered questions for a course
     */
    @Transactional(readOnly = true)
    public List<StudentQuestionDTO> getUnansweredQuestions(Long courseId) {
        log.info("Fetching unanswered questions for course {}", courseId);
        
        List<StudentQuestion> questions = studentQuestionRepository.findUnansweredByCourseId(courseId);
        return studentQuestionMapper.toDtoList(questions);
    }

    /**
     * Get all answered questions for a course
     */
    @Transactional(readOnly = true)
    public List<StudentQuestionDTO> getAnsweredQuestions(Long courseId) {
        log.info("Fetching answered questions for course {}", courseId);
        
        List<StudentQuestion> questions = studentQuestionRepository.findAnsweredByCourseId(courseId);
        List<StudentQuestionDTO> questionDTOs = studentQuestionMapper.toDtoList(questions);
        
        // Load answers for each question
        for (StudentQuestionDTO questionDTO : questionDTOs) {
            teacherAnswerRepository.findByQuestionId(questionDTO.getId())
                    .ifPresent(answer -> questionDTO.setAnswer(teacherAnswerMapper.toDto(answer)));
        }
        
        return questionDTOs;
    }

    /**
     * Get questions by student ID
     */
    @Transactional(readOnly = true)
    public List<StudentQuestionDTO> getQuestionsByStudent(Integer studentId) {
        log.info("Fetching questions for student {}", studentId);
        
        List<StudentQuestion> questions = studentQuestionRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
        List<StudentQuestionDTO> questionDTOs = studentQuestionMapper.toDtoList(questions);
        
        // Load answers for each question
        for (StudentQuestionDTO questionDTO : questionDTOs) {
            teacherAnswerRepository.findByQuestionId(questionDTO.getId())
                    .ifPresent(answer -> questionDTO.setAnswer(teacherAnswerMapper.toDto(answer)));
        }
        
        return questionDTOs;
    }

    /**
     * Get answers by teacher ID
     */
    @Transactional(readOnly = true)
    public List<TeacherAnswerDTO> getAnswersByTeacher(Long teacherId) {
        log.info("Fetching answers for teacher {}", teacherId);
        
        List<TeacherAnswer> answers = teacherAnswerRepository.findByTeacherIdOrderByCreatedAtDesc(teacherId);
        return teacherAnswerMapper.toDtoList(answers);
    }

    /**
     * Search questions by title
     */
    @Transactional(readOnly = true)
    public List<StudentQuestionDTO> searchQuestions(Long courseId, String searchTerm) {
        log.info("Searching questions in course {} with term: {}", courseId, searchTerm);
        
        List<StudentQuestion> questions = studentQuestionRepository.findByCourseIdAndTitleContaining(courseId, searchTerm);
        List<StudentQuestionDTO> questionDTOs = studentQuestionMapper.toDtoList(questions);
        
        // Load answers for each question
        for (StudentQuestionDTO questionDTO : questionDTOs) {
            teacherAnswerRepository.findByQuestionId(questionDTO.getId())
                    .ifPresent(answer -> questionDTO.setAnswer(teacherAnswerMapper.toDto(answer)));
        }
        
        return questionDTOs;
    }

    /**
     * Get Q&A statistics for a course
     */
    @Transactional(readOnly = true)
    public QnaStatisticsDTO getQnaStatistics(Long courseId) {
        log.info("Fetching Q&A statistics for course {}", courseId);
        
        Long totalQuestions = studentQuestionRepository.countByCourseId(courseId);
        Long unansweredQuestions = studentQuestionRepository.countUnansweredByCourseId(courseId);
        Long answeredQuestions = totalQuestions - unansweredQuestions;
        
        double answerRate = totalQuestions > 0 ? (double) answeredQuestions / totalQuestions * 100 : 0.0;
        
        return new QnaStatisticsDTO(totalQuestions, answeredQuestions, unansweredQuestions, answerRate);
    }

    /**
     * Update question
     */
    public StudentQuestionDTO updateQuestion(String questionId, StudentQuestionDTO questionDTO) {
        log.info("Updating question with id: {}", questionId);
        
        StudentQuestion existingQuestion = studentQuestionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
        
        // Update fields
        if (questionDTO.getTitle() != null) {
            existingQuestion.setTitle(questionDTO.getTitle());
        }
        if (questionDTO.getContent() != null) {
            existingQuestion.setContent(questionDTO.getContent());
        }
        
        StudentQuestion updatedQuestion = studentQuestionRepository.save(existingQuestion);
        return studentQuestionMapper.toDto(updatedQuestion);
    }

    /**
     * Delete question and its answer
     */
    public void deleteQuestion(String questionId) {
        log.info("Deleting question with id: {}", questionId);
        
        // Delete answer first if exists
        teacherAnswerRepository.deleteByQuestionId(questionId);
        
        // Delete question
        studentQuestionRepository.deleteById(questionId);
        
        log.info("Question {} deleted successfully", questionId);
    }

    /**
     * Statistics DTO for Q&A
     */
    public static class QnaStatisticsDTO {
        private final Long totalQuestions;
        private final Long answeredQuestions;
        private final Long unansweredQuestions;
        private final Double answerRate;

        public QnaStatisticsDTO(Long totalQuestions, Long answeredQuestions, Long unansweredQuestions, Double answerRate) {
            this.totalQuestions = totalQuestions;
            this.answeredQuestions = answeredQuestions;
            this.unansweredQuestions = unansweredQuestions;
            this.answerRate = answerRate;
        }

        // Getters
        public Long getTotalQuestions() { return totalQuestions; }
        public Long getAnsweredQuestions() { return answeredQuestions; }
        public Long getUnansweredQuestions() { return unansweredQuestions; }
        public Double getAnswerRate() { return answerRate; }
    }
}
