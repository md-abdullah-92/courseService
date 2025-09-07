# Course Service - Java Spring Boot Migration

This is a complete migration of the Node.js + Express.js Course Service to Java Spring Boot, maintaining all functionality and API endpoints.

## Project Structure

```
src/main/java/com/eduverse/courseservice/
├── CourseServiceApplication.java     # Main Application Class
├── config/                          # Configuration Classes
│   ├── SecurityConfig.java          # Security & CORS Configuration
│   └── JwtAuthenticationFilter.java # JWT Authentication Filter
├── controller/                      # REST Controllers
│   └── CourseController.java        # Course API endpoints
├── service/                         # Business Logic Layer
│   └── CourseService.java           # Course business logic
├── repository/                      # Data Access Layer
│   └── CourseRepository.java        # JPA Repository interfaces
├── entity/                          # JPA Entities
│   ├── Course.java                  # Course entity
│   ├── CourseOutcome.java          # Course outcome entity
│   ├── Enrollment.java             # Enrollment entity
│   └── Lesson.java                 # Lesson entity
├── dto/                            # Data Transfer Objects
│   └── CourseDto.java              # Course DTOs for API
├── mapper/                         # Entity-DTO Mappers
│   └── CourseMapper.java           # Course mapping logic
├── enums/                          # Enum Classes
│   ├── CourseLevel.java            # Course difficulty levels
│   ├── QuestionType.java           # Question types
│   └── DifficultyLevel.java        # Quiz difficulty levels
└── exception/                      # Exception Handling
    ├── ResourceNotFoundException.java
    ├── ErrorResponse.java
    └── GlobalExceptionHandler.java
```

## API Endpoints (Migrated from Node.js)

### Course Management
- `POST /api/courses/create` - Create a new course
- `PUT /api/courses/update/{id}` - Update a course
- `DELETE /api/courses/delete/{id}` - Delete a course
- `GET /api/courses/all` - Get all courses
- `GET /api/courses/get/{id}` - Get course by ID
- `GET /api/courses/getByInstructorId/{instructorId}` - Get courses by instructor
- `GET /api/courses/getByTopic/{topic}` - Get courses by topic

## Key Features Migrated

### 1. Database Layer
- **Prisma → Spring Data JPA**: Complete migration from Prisma ORM to JPA entities
- **MySQL Support**: Same database structure maintained
- **Relationships**: All foreign key relationships preserved
- **Indexes**: Database indexes maintained for performance

### 2. Validation
- **express-validator → Bean Validation**: Validation rules moved to annotations
- **Custom validators**: Complex validation logic in service layer
- **Error handling**: Centralized error handling with GlobalExceptionHandler

### 3. Authentication & Security
- **JWT middleware → Spring Security**: JWT authentication filter
- **CORS**: Configurable CORS support
- **Role-based access**: Authorization preserved

### 4. Business Logic
- **Controllers**: RESTful endpoints with proper HTTP status codes
- **Services**: Business logic separated into service layer
- **DTOs**: Request/response objects for clean API contracts
- **Mappers**: Entity-DTO conversion logic

### 5. Error Handling
- **Global exception handler**: Centralized error processing
- **Custom exceptions**: ResourceNotFoundException and others
- **Validation errors**: Proper validation error responses

## Environment Configuration

Create `application.properties` or `application.yml`:

```properties
# Server Configuration
server.port=5001

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/eduverse_course_db
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:password}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=${JWT_SECRET:mySecretKey}

# CORS Configuration
cors.allowed-origins=${CORS_ORIGINS:http://localhost:3000}
```

## Dependencies Added

### Core Spring Boot
- `spring-boot-starter-web` - Web layer
- `spring-boot-starter-data-jpa` - Data access
- `spring-boot-starter-validation` - Validation
- `spring-boot-starter-security` - Security

### Database
- `mysql-connector-java` - MySQL driver

### JWT & Security
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` - JWT processing

### Utilities
- `lombok` - Reduces boilerplate code
- `spring-boot-starter-actuator` - Monitoring

## Build and Run

### Using Maven
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package the application
mvn package

# Run the application
mvn spring-boot:run

# Or run the JAR
java -jar target/course-service-1.0.0.jar
```

### Using Gradle (if converted)
```bash
# Clean and build
./gradlew clean build

# Run the application
./gradlew bootRun
```

## Migration Highlights

### From Node.js Express to Spring Boot

1. **Routing**: Express routes → Spring `@RestController` with mapping annotations
2. **Middleware**: Express middleware → Spring filters and interceptors
3. **Database**: Prisma → Spring Data JPA repositories
4. **Validation**: express-validator → Bean Validation annotations
5. **Error Handling**: Express error middleware → `@ControllerAdvice`
6. **Dependency Injection**: Manual requires → Spring's `@Autowired`
7. **Configuration**: Environment variables → Spring profiles

### Preserved Functionality
- ✅ All API endpoints maintained
- ✅ Request/response formats identical
- ✅ Validation rules preserved
- ✅ Database schema unchanged
- ✅ JWT authentication flow
- ✅ CORS configuration
- ✅ Error response formats

## Database Setup

The application expects the same MySQL database structure as the Node.js version. Run your existing database migrations or use the JPA auto-DDL feature:

```sql
-- The same database schema from Prisma will work
-- JPA will auto-create tables based on entities
```

## Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn test -Dtest=*IntegrationTest
```

### API Testing
Use the same Postman collection or API tests from the Node.js version - all endpoints should work identically.

## Monitoring

Access Spring Boot Actuator endpoints:
- Health: `http://localhost:5001/actuator/health`
- Metrics: `http://localhost:5001/actuator/metrics`
- Info: `http://localhost:5001/actuator/info`

## Production Deployment

### Docker
```dockerfile
FROM openjdk:17-jre-slim
COPY target/course-service-1.0.0.jar app.jar
EXPOSE 5001
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Environment Variables
Set these in production:
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `CORS_ORIGINS`

## Performance Considerations

### JPA Optimizations
- Lazy loading for relationships
- Query optimization with `@Query`
- Connection pooling configured
- Database indexes preserved

### Security
- JWT stateless authentication
- CORS properly configured
- Input validation on all endpoints
- SQL injection prevention via JPA

This Spring Boot application provides the same functionality as the original Node.js service with improved type safety, better tooling, and enterprise-grade features.
