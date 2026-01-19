package com.ecommerce.webecommerce.service;

import com.ecommerce.webecommerce.model.CartItemResponse;

import java.util.List;


public interface CartService {

    void addItemToCart(Long userId, Long productId, int quantity);

    void updateCartItemQuantity(Long userId, Long productId, int quantity);

    void removeItemFromCart(Long userId, Long cartItemId);

    void clearCart(Long userId);

    List<CartItemResponse> getCartItems(Long userId);
}
