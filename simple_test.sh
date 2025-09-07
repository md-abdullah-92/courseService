#!/bin/bash

echo "=== Simple API Test ==="

# Start the application in background
cd "/home/apurbo/Videos/Therap Frontend /courseService"
java -jar target/course-service-1.0.0.jar &
APP_PID=$!

# Wait for application to start  
echo "Waiting for application to start..."
sleep 15

echo "Testing basic endpoint access..."

# Test basic health endpoint first
echo "Testing actuator health..."
curl -s -X GET "http://localhost:5001/actuator/health" || echo "Health endpoint failed"

echo ""

# Test specific course endpoint with different paths
echo "Testing course endpoints..."
curl -s -X GET "http://localhost:5001/api/courses/all" || echo "GET /api/courses/all failed"

echo ""

# Try a simple course creation with the full path
echo "Testing course creation..."
curl -s -X POST "http://localhost:5001/api/courses/create" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Simple Test Course", 
    "description": "A test course",
    "price": 10.0,
    "level": "BEGINNER",
    "instructorId": "test123",
    "topic": "Testing"
  }' || echo "Course creation failed"

echo ""

# Clean up
echo "Stopping application..."
kill $APP_PID

echo "=== Test completed ==="
