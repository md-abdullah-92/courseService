package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.CartDTO;
import com.eduverse.courseservice.dto.CartItemDTO;
import com.eduverse.courseservice.entity.Cart;
import com.eduverse.courseservice.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CourseMapper.class})
public interface CartMapper {
    
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "itemCount", ignore = true)
    CartDTO toDTO(Cart cart);
    
    @Mapping(target = "course", source = "course")
    CartItemDTO toItemDTO(CartItem cartItem);
    
    Cart toEntity(CartDTO cartDTO);
    
    @Mapping(target = "cart", ignore = true)
    CartItem toItemEntity(CartItemDTO cartItemDTO);
}
