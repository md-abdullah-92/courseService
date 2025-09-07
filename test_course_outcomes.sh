#!/bin/bash

echo "=== Starting Course Outcome Test ==="

# Start the application in background
echo "Starting application..."
cd "/home/apurbo/Videos/Therap Frontend /courseService"
java -jar target/course-service-1.0.0.jar &
APP_PID=$!

# Wait for application to start
echo "Waiting for application to start..."
sleep 15

# Test 1: Create a test course
echo "Creating test course..."
COURSE_RESPONSE=$(curl -s -X POST "http://localhost:5001/api/courses" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Course for Outcomes",
    "description": "Testing course outcomes functionality",
    "price": 25.0,
    "level": "BEGINNER",
    "instructorId": "instructor123",
    "topic": "Testing"
  }')

echo "Course created: $COURSE_RESPONSE"

# Extract course ID from response
COURSE_ID=$(echo $COURSE_RESPONSE | grep -o '"id":[0-9]*' | cut -d: -f2)
echo "Course ID: $COURSE_ID"

if [ -n "$COURSE_ID" ]; then
    # Test 2: Add course outcomes
    echo "Adding course outcomes..."
    
    OUTCOME1=$(curl -s -X POST "http://localhost:5001/api/outcomes" \
      -H "Content-Type: application/json" \
      -d '{
        "courseId": '$COURSE_ID',
        "description": "Students will understand basic concepts"
      }')
    echo "Outcome 1: $OUTCOME1"
    
    OUTCOME2=$(curl -s -X POST "http://localhost:5001/api/outcomes" \
      -H "Content-Type: application/json" \
      -d '{
        "courseId": '$COURSE_ID',
        "description": "Students will be able to apply knowledge"
      }')
    echo "Outcome 2: $OUTCOME2"
    
    # Test 3: Fetch course with outcomes
    echo "Fetching course with outcomes..."
    COURSE_WITH_OUTCOMES=$(curl -s -X GET "http://localhost:5001/api/courses/$COURSE_ID")
    echo "Course with outcomes: $COURSE_WITH_OUTCOMES"
    
    # Check if outcomes are included
    if echo "$COURSE_WITH_OUTCOMES" | grep -q "outcomes"; then
        echo "✅ SUCCESS: Course outcomes are properly included!"
    else
        echo "❌ FAIL: Course outcomes are NOT included!"
    fi
    
    # Test 4: Test by instructor ID
    echo "Testing courses by instructor..."
    INSTRUCTOR_COURSES=$(curl -s -X GET "http://localhost:5001/api/courses/instructor/instructor123")
    echo "Instructor courses: $INSTRUCTOR_COURSES"
    
    if echo "$INSTRUCTOR_COURSES" | grep -q "outcomes"; then
        echo "✅ SUCCESS: Instructor courses include outcomes!"
    else
        echo "❌ FAIL: Instructor courses do NOT include outcomes!"
    fi
else
    echo "Failed to create course, skipping tests"
fi

# Clean up
echo "Stopping application..."
kill $APP_PID

echo "=== Test completed ==="
