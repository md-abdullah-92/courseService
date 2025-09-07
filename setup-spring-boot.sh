#!/bin/bash

# Spring Boot Course Service Setup Script

echo "🚀 Setting up Spring Boot Course Service..."

# Check if Java 17+ is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | sed '/^1\./s///' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17+ is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java $JAVA_VERSION detected"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven."
    exit 1
fi

echo "✅ Maven detected"

# Create environment file if it doesn't exist
if [ ! -f ".env" ]; then
    echo "📝 Creating .env file..."
    cat > .env << EOL
# Database Configuration
DB_USERNAME=root
DB_PASSWORD=password
DATABASE_URL=jdbc:mysql://localhost:3306/eduverse_course_db

# JWT Configuration
JWT_SECRET=mySecretKey

# CORS Configuration
CORS_ORIGINS=http://localhost:3000,http://localhost:3001

# MinIO Configuration (if using file storage)
MINIO_ENDPOINT=http://localhost:9000
MINIO_ACCESS_KEY=minioadmin
MINIO_SECRET_KEY=minioadmin
MINIO_BUCKET_NAME=course-files
EOL
    echo "✅ Created .env file with default values"
    echo "⚠️  Please update the values in .env file as needed"
else
    echo "✅ .env file already exists"
fi

# Install dependencies and compile
echo "📦 Installing dependencies and compiling..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Dependencies installed and compilation successful"
else
    echo "❌ Compilation failed. Please check the errors above."
    exit 1
fi

# Run tests
echo "🧪 Running tests..."
mvn test -q

if [ $? -eq 0 ]; then
    echo "✅ All tests passed"
else
    echo "⚠️  Some tests failed. Please check the test results."
fi

echo ""
echo "🎉 Setup complete!"
echo ""
echo "📚 Next steps:"
echo "1. Update database configuration in .env file"
echo "2. Ensure MySQL is running and database exists"
echo "3. Start the application:"
echo "   mvn spring-boot:run"
echo ""
echo "📖 API Documentation:"
echo "   http://localhost:5001/actuator/health (Health check)"
echo "   http://localhost:5001/api/courses/all (Get all courses)"
echo ""
echo "🔧 Useful commands:"
echo "   mvn spring-boot:run          # Start the application"
echo "   mvn test                     # Run tests"
echo "   mvn package                  # Build JAR file"
echo "   mvn clean                    # Clean build artifacts"
echo ""
echo "🐳 Docker build (optional):"
echo "   docker build -t course-service ."
echo "   docker run -p 5001:5001 course-service"
