const express = require('express');
const router = express.Router();
const enrollmentController = require('../controllers/enrollmentController');
const { protect } = require('../middleware/auth');

// Enrollment Routes
router.post('/enroll', protect, enrollmentController.enrollStudent);
router.delete('/unenroll/:id', protect, enrollmentController.unenrollStudent);
router.get('/student/:studentId', protect, enrollmentController.getStudentEnrollments);
router.get('/course/:courseId', enrollmentController.getCourseEnrollments);
router.get('/:id', protect, enrollmentController.getEnrollment);
router.get('/stats/:studentId', protect, enrollmentController.getStudentStats);
router.get('/stats/teacher/:instructorId', protect, enrollmentController.getTeacherStats);

module.exports = router;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                