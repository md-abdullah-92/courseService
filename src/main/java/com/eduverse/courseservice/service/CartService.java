package com.eduverse.courseservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduverse.courseservice.dto.CartDTO;
import com.eduverse.courseservice.dto.CartItemDTO;
import com.eduverse.courseservice.entity.Cart;
import com.eduverse.courseservice.entity.CartItem;
import com.eduverse.courseservice.mapper.CartMapper;
import com.eduverse.courseservice.repository.CartItemRepository;
import com.eduverse.courseservice.repository.CartRepository;
import com.eduverse.courseservice.repository.CourseRepository;
import com.eduverse.courseservice.repository.EnrollmentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class CartService {
    
    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                      CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
                      CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.cartMapper = cartMapper;
    }
    
    @Transactional(readOnly = true)
    public CartDTO getCart(String studentId) {
        log.info("Fetching cart for student: {}", studentId);
        
        // Ensure cart exists (similar to Node.js upsert)
        Cart cart = cartRepository.findById(studentId).orElse(null);
        if (cart == null) {
            log.info("Cart not found for student {}, creating new cart", studentId);
            cart = createNewCart(studentId);
        }
        
        // Fetch cart items separately (similar to Node.js approach)
        List<CartItem> items = cartItemRepository.findByStudentIdWithCourse(studentId);
        log.info("Found {} cart items for student {}", items.size(), studentId);
        
        // Set items to the cart
        cart.setItems(items);
        
        CartDTO cartDTO = cartMapper.toDTO(cart);
        cartDTO.setTotal(cartDTO.getCalculatedTotal());
        cartDTO.setItemCount(cartDTO.getCalculatedItemCount());
        
        log.info("Cart response: studentId={}, itemCount={}, total=${}", 
                 cartDTO.getStudentId(), cartDTO.getItemCount(), cartDTO.getTotal());
        return cartDTO;
    }
    
    @Transactional
    public CartItemDTO addToCart(String studentId, Long courseId) {
        log.info("Adding course {} to cart for student {}", courseId, studentId);
        
        // Check if course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        // Check if student is already enrolled
        boolean isEnrolled = enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (isEnrolled) {
            throw new RuntimeException("You are already enrolled in this course");
        }
        
        // Check if item already in cart
        boolean existsInCart = cartItemRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (existsInCart) {
            throw new RuntimeException("Course is already in your cart");
        }
        
        // Ensure cart exists (similar to Node.js upsert)
        Cart cart = cartRepository.findById(studentId)
                .orElseGet(() -> createNewCart(studentId));
        
        // Create and save cart item
        CartItem cartItem = new CartItem();
        cartItem.setStudentId(studentId);
        cartItem.setCourseId(courseId);
        
        CartItem savedItem = cartItemRepository.save(cartItem);
        log.info("CartItem saved with ID: {}", savedItem.getId());
        
        // Flush to ensure immediate persistence
        entityManager.flush();
        
        // Verify the item was saved
        boolean verifyExists = cartItemRepository.existsByStudentIdAndCourseId(studentId, courseId);
        log.info("Verification - Item exists in cart: {}", verifyExists);
        
        // Fetch with course details for response (similar to Node.js include)
        CartItem itemWithCourse = cartItemRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElse(savedItem);
        
        log.info("Course {} added to cart successfully", courseId);
        return cartMapper.toItemDTO(itemWithCourse);
    }
    
    public void removeFromCart(String studentId, Long courseId) {
        log.info("Removing course {} from cart for student {}", courseId, studentId);
        
        boolean exists = cartItemRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (!exists) {
            throw new RuntimeException("Course not found in cart");
        }
        
        cartItemRepository.deleteByStudentIdAndCourseId(studentId, courseId);
        log.info("Course {} removed from cart successfully", courseId);
    }
    
    public void clearCart(String studentId) {
        log.info("Clearing cart for student {}", studentId);
        
        cartItemRepository.deleteByStudentId(studentId);
        log.info("Cart cleared successfully for student {}", studentId);
    }
    
    @Transactional(readOnly = true)
    public Long getCartCount(String studentId) {
        log.info("Getting cart count for student {}", studentId);
        
        Long count = cartItemRepository.countByStudentId(studentId);
        log.info("Cart count for student {}: {}", studentId, count);
        return count;
    }
    
    @Transactional(readOnly = true)
    public List<CartItemDTO> getCartItems(String studentId) {
        log.info("Fetching cart items for student {}", studentId);
        
        List<CartItem> items = cartItemRepository.findByStudentIdWithCourse(studentId);
        List<CartItemDTO> itemDTOs = items.stream()
                .map(cartMapper::toItemDTO)
                .collect(Collectors.toList());
        
        log.info("Found {} cart items for student {}", itemDTOs.size(), studentId);
        return itemDTOs;
    }
    
    @Transactional
    private Cart createNewCart(String studentId) {
        log.info("Creating new cart for student {}", studentId);
        
        Cart cart = new Cart();
        cart.setStudentId(studentId);
        Cart savedCart = cartRepository.save(cart);
        
        // Flush to ensure immediate persistence
        entityManager.flush();
        
        log.info("New cart created for student {} with ID: {}", studentId, savedCart.getStudentId());
        return savedCart;
    }
}
