package com.eduverse.courseservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CartDTO {
    
    private String studentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartItemDTO> items;
    private Double total;
    private Integer itemCount;
    
    // Constructors
    public CartDTO() {}
    
    public CartDTO(String studentId, LocalDateTime createdAt, LocalDateTime updatedAt, 
                   List<CartItemDTO> items, Double total, Integer itemCount) {
        this.studentId = studentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
        this.total = total;
        this.itemCount = itemCount;
    }
    
    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    
    public Integer getItemCount() { return itemCount; }
    public void setItemCount(Integer itemCount) { this.itemCount = itemCount; }

    // Helper methods to calculate totals
    public Double getCalculatedTotal() {
        if (items == null) return 0.0;
        return items.stream()
                .mapToDouble(item -> item.getCourse() != null ? item.getCourse().getPrice() : 0.0)
                .sum();
    }
    
    public Integer getCalculatedItemCount() {
        return items != null ? items.size() : 0;
    }
}
