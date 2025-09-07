package com.eduverse.courseservice.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduverse.courseservice.dto.CartDTO;
import com.eduverse.courseservice.dto.CartItemDTO;
import com.eduverse.courseservice.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Map<String, Object>> getCart(@PathVariable String studentId) {
        try {
            log.info("Getting cart for student: {}", studentId);
            CartDTO cart = cartService.getCart(studentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of(
                            "studentId", cart.getStudentId(),
                            "items", cart.getItems(),
                            "total", cart.getTotal(),
                            "itemCount", cart.getItemCount()
                    )
            ));
        } catch (Exception e) {
            log.error("Error getting cart for student {}: {}", studentId, e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to fetch cart"
            ));
        }
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<Map<String, Object>> addToCart(
            @PathVariable String studentId,
            @RequestBody Map<String, Object> request) {
        try {
            Object courseIdObj = request.get("courseId");
            if (courseIdObj == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Course ID is required"
                ));
            }
            
            Long courseId;
            try {
                courseId = Long.valueOf(courseIdObj.toString());
                if (courseId <= 0) {
                    throw new NumberFormatException("Course ID must be positive");
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Valid course ID is required"
                ));
            }
            
            log.info("Adding course {} to cart for student {}", courseId, studentId);
            CartItemDTO cartItem = cartService.addToCart(studentId, courseId);
            return ResponseEntity.status(201).body(Map.of(
                    "success", true,
                    "data", cartItem,
                    "message", "Course added to cart successfully"
            ));
        } catch (RuntimeException e) {
            log.error("Error adding to cart: {}", e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(Map.of(
                        "success", false,
                        "message", e.getMessage()
                ));
            } else if (e.getMessage().contains("already")) {
                return ResponseEntity.status(409).body(Map.of(
                        "success", false,
                        "message", e.getMessage()
                ));
            }
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            log.error("Unexpected error adding to cart: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to add course to cart"
            ));
        }
    }

    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<Map<String, Object>> removeFromCart(
            @PathVariable String studentId,
            @PathVariable Long courseId) {
        try {
            if (courseId <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Valid course ID is required"
                ));
            }
            
            log.info("Removing course {} from cart for student {}", courseId, studentId);
            cartService.removeFromCart(studentId, courseId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Course removed from cart successfully"
            ));
        } catch (RuntimeException e) {
            log.error("Error removing from cart: {}", e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(Map.of(
                        "success", false,
                        "message", e.getMessage()
                ));
            }
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            log.error("Unexpected error removing from cart: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to remove course from cart"
            ));
        }
    }

    @DeleteMapping("/{studentId}/clear")
    public ResponseEntity<Map<String, Object>> clearCart(@PathVariable String studentId) {
        try {
            log.info("Clearing cart for student {}", studentId);
            cartService.clearCart(studentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cart cleared successfully"
            ));
        } catch (Exception e) {
            log.error("Error clearing cart for student {}: {}", studentId, e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to clear cart"
            ));
        }
    }

    @GetMapping("/{studentId}/count")
    public ResponseEntity<Map<String, Object>> getCartCount(@PathVariable String studentId) {
        try {
            log.info("Getting cart count for student {}", studentId);
            Long count = cartService.getCartCount(studentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of("count", count)
            ));
        } catch (Exception e) {
            log.error("Error getting cart count for student {}: {}", studentId, e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to get cart count"
            ));
        }
    }

    @GetMapping("/{studentId}/items")
    public ResponseEntity<Map<String, Object>> getCartItems(@PathVariable String studentId) {
        try {
            log.info("Getting cart items for student {}", studentId);
            List<CartItemDTO> items = cartService.getCartItems(studentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", items,
                    "count", items.size()
            ));
        } catch (Exception e) {
            log.error("Error getting cart items for student {}: {}", studentId, e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to get cart items"
            ));
        }
    }

    @PostMapping("/{studentId}/promo")
    public ResponseEntity<Map<String, Object>> applyPromoCode(
            @PathVariable String studentId,
            @RequestBody Map<String, Object> request) {
        try {
            // Promo code functionality not implemented yet
            return ResponseEntity.status(501).body(Map.of(
                    "success", false,
                    "message", "Promo code functionality not implemented yet"
            ));
        } catch (Exception e) {
            log.error("Error applying promo code for student {}: {}", studentId, e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Failed to apply promo code"
            ));
        }
    }
}
