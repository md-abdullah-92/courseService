# Node.js to Spring Boot Migration Guide

## Complete API Migration Mapping

### 1. Course Controller Migration

#### Node.js (courseController.js)
```javascript
const prisma = require('../prismaClient');

module.exports = {
  createCourse: async (req, res) => {
    try {
      const { title, description, price, coverPhotoUrl, level, instructorId, topic } = req.body;
      const newCourse = await prisma.course.create({
        data: { title, description, price, coverPhotoUrl, level, instructorId, topic }
      });
      res.status(201).json(newCourse);
    } catch (error) {
      res.status(500).json({ message: 'Error creating course', error });
    }
  }
}
```

#### Spring Boot (CourseController.java)
```java
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    private final CourseService courseService;
    
    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto) {
        try {
            CourseDto createdCourse = courseService.createCourse(courseDto);
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e; // Handled by GlobalExceptionHandler
        }
    }
}
```

### 2. Routes Migration

#### Node.js (courseRoutes.js)
```javascript
const express = require('express');
const router = express.Router();
const courseController = require('../controllers/courseController');
const { courseValidator } = require('../middleware/validators/courseValidator');

router.post('/create', courseValidator, validateResult, courseController.createCourse);
router.get('/all', courseController.getAllCourses);
```

#### Spring Boot (Built into Controller)
```java
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto) {
        // Validation is handled by @Valid annotation
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        // Implementation
    }
}
```

### 3. Validation Migration

#### Node.js (courseValidator.js)
```javascript
const { check } = require('express-validator');

const courseValidator = [
  check('title')
    .trim()
    .notEmpty().withMessage('Title is required')
    .isLength({ min: 3, max: 100 }).withMessage('Title must be 3-100 characters'),
  
  check('description')
    .trim()
    .notEmpty().withMessage('Description is required')
    .isLength({ min: 10 }).withMessage('Description must be at least 10 characters'),
];
```

#### Spring Boot (CourseDto.java)
```java
public class CourseDto {
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;
}
```

### 4. Database Layer Migration

#### Node.js (Prisma)
```javascript
const prisma = require('../prismaClient');

// Create course
const newCourse = await prisma.course.create({
  data: { title, description, price, level, instructorId }
});

// Get course with relations
const course = await prisma.course.findUnique({
  where: { id: Number(id) },
  include: { lessons: true, outcomes: true }
});
```

#### Spring Boot (JPA Repository)
```java
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.lessons LEFT JOIN FETCH c.outcomes WHERE c.id = :id")
    Optional<Course> findByIdWithDetails(@Param("id") Long id);
    
    List<Course> findByInstructorId(String instructorId);
}

// Usage in Service
@Service
public class CourseService {
    
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = courseMapper.toEntity(courseDto);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }
}
```

### 5. Error Handling Migration

#### Node.js (Basic error handling)
```javascript
try {
  // business logic
} catch (error) {
  console.log(error);
  res.status(500).json({ message: 'Error creating course', error });
}
```

#### Spring Boot (Global Exception Handler)
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(),
            "Resource not found",
            HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
```

### 6. Authentication Migration

#### Node.js (auth.js middleware)
```javascript
const jwt = require("jsonwebtoken");

const protect = async (req, res, next) => {
  let token;
  if (req.headers.authorization && req.headers.authorization.startsWith("Bearer")) {
    token = req.headers.authorization.split(" ")[1];
    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    req.user = { id: decoded.id, role: decoded.role, name: decoded.name };
    next();
  } else {
    return res.status(401).json({ message: "Not authorized, no token" });
  }
};
```

#### Spring Boot (JwtAuthenticationFilter.java)
```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) {
        String token = getTokenFromRequest(request);
        
        if (token != null && validateToken(token)) {
            Claims claims = getClaimsFromToken(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserPrincipal(claims.getSubject(), claims.get("role", String.class)),
                null,
                authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
}
```

## Key Differences & Improvements

### 1. Type Safety
- **Node.js**: Dynamic typing, runtime errors
- **Spring Boot**: Compile-time type checking, fewer runtime errors

### 2. Dependency Injection
- **Node.js**: Manual require() statements
- **Spring Boot**: Automatic dependency injection with @Autowired

### 3. Configuration
- **Node.js**: Environment variables accessed directly
- **Spring Boot**: Type-safe configuration with @Value and @ConfigurationProperties

### 4. Database Access
- **Node.js**: Prisma ORM with generated client
- **Spring Boot**: JPA with Spring Data repositories

### 5. Validation
- **Node.js**: Middleware-based validation
- **Spring Boot**: Annotation-based validation with automatic error handling

### 6. Testing
- **Node.js**: Manual test setup
- **Spring Boot**: Comprehensive testing framework with @SpringBootTest

### 7. Monitoring & Operations
- **Node.js**: Basic logging
- **Spring Boot**: Built-in actuator endpoints for health, metrics, etc.

## Migration Benefits

### Performance
- **JVM Optimization**: Mature JVM with advanced garbage collection
- **Connection Pooling**: Built-in database connection pooling
- **Caching**: Integrated caching solutions

### Enterprise Features
- **Security**: Comprehensive security framework
- **Monitoring**: Built-in metrics and health checks
- **Configuration**: Environment-specific configurations
- **Logging**: Advanced logging with different levels and appenders

### Development Experience
- **IDE Support**: Excellent IDE support with IntelliJ/Eclipse
- **Debugging**: Superior debugging capabilities
- **Refactoring**: Safe refactoring with static typing
- **Documentation**: Auto-generated API documentation

## Deployment Comparison

### Node.js Deployment
```bash
# Install dependencies
npm install

# Start application
npm start
```

### Spring Boot Deployment
```bash
# Build application
mvn package

# Run JAR file
java -jar target/course-service-1.0.0.jar

# Or use Maven
mvn spring-boot:run
```

### Docker Deployment
Both applications can be containerized, but Spring Boot offers:
- Multi-stage builds for optimization
- Built-in health checks
- JVM tuning options
- Better memory management

## Performance Comparison

### Startup Time
- **Node.js**: Faster startup (~1-2 seconds)
- **Spring Boot**: Slower startup (~10-15 seconds) but warmer JVM performance

### Runtime Performance
- **Node.js**: Good for I/O intensive operations
- **Spring Boot**: Better for CPU intensive operations, better under load

### Memory Usage
- **Node.js**: Lower initial memory footprint
- **Spring Boot**: Higher memory usage but better garbage collection

## Conclusion

The Spring Boot migration provides:
✅ Better type safety and compile-time error detection
✅ Enterprise-grade features out of the box
✅ Superior tooling and IDE support
✅ Comprehensive testing framework
✅ Better monitoring and operational capabilities
✅ Mature ecosystem with extensive documentation

The trade-offs:
⚠️ Longer startup time
⚠️ Higher memory usage
⚠️ More complex deployment pipeline
⚠️ Steeper learning curve for Node.js developers
