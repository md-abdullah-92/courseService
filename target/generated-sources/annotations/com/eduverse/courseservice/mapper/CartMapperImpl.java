package com.eduverse.courseservice.mapper;

import com.eduverse.courseservice.dto.CartDTO;
import com.eduverse.courseservice.dto.CartItemDTO;
import com.eduverse.courseservice.entity.Cart;
import com.eduverse.courseservice.entity.CartItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T18:15:54+0600",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public CartDTO toDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();

        cartDTO.setStudentId( cart.getStudentId() );
        cartDTO.setCreatedAt( cart.getCreatedAt() );
        cartDTO.setUpdatedAt( cart.getUpdatedAt() );
        cartDTO.setItems( cartItemListToCartItemDTOList( cart.getItems() ) );

        return cartDTO;
    }

    @Override
    public CartItemDTO toItemDTO(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setCourse( courseMapper.toDto( cartItem.getCourse() ) );
        cartItemDTO.setId( cartItem.getId() );
        cartItemDTO.setStudentId( cartItem.getStudentId() );
        cartItemDTO.setCourseId( cartItem.getCourseId() );
        cartItemDTO.setAddedAt( cartItem.getAddedAt() );

        return cartItemDTO;
    }

    @Override
    public Cart toEntity(CartDTO cartDTO) {
        if ( cartDTO == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setStudentId( cartDTO.getStudentId() );
        cart.setCreatedAt( cartDTO.getCreatedAt() );
        cart.setUpdatedAt( cartDTO.getUpdatedAt() );
        cart.setItems( cartItemDTOListToCartItemList( cartDTO.getItems() ) );

        return cart;
    }

    @Override
    public CartItem toItemEntity(CartItemDTO cartItemDTO) {
        if ( cartItemDTO == null ) {
            return null;
        }

        CartItem cartItem = new CartItem();

        cartItem.setId( cartItemDTO.getId() );
        cartItem.setStudentId( cartItemDTO.getStudentId() );
        cartItem.setCourseId( cartItemDTO.getCourseId() );
        cartItem.setAddedAt( cartItemDTO.getAddedAt() );
        cartItem.setCourse( courseMapper.toEntity( cartItemDTO.getCourse() ) );

        return cartItem;
    }

    protected List<CartItemDTO> cartItemListToCartItemDTOList(List<CartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItemDTO> list1 = new ArrayList<CartItemDTO>( list.size() );
        for ( CartItem cartItem : list ) {
            list1.add( toItemDTO( cartItem ) );
        }

        return list1;
    }

    protected List<CartItem> cartItemDTOListToCartItemList(List<CartItemDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItem> list1 = new ArrayList<CartItem>( list.size() );
        for ( CartItemDTO cartItemDTO : list ) {
            list1.add( toItemEntity( cartItemDTO ) );
        }

        return list1;
    }
}
