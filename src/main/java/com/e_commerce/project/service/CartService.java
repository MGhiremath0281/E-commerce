package com.e_commerce.project.service;

import com.e_commerce.project.model.Cart;

public interface CartService {

    public Cart getOrCreate(Long userId);

    public Cart addItem(Long userId, long productId, int quantity);

    public Cart updateQuantity(Long userId, Long productId, int quantity);

    public Cart removeItem(Long userId, Long productId);

    public void clearCart(Long userId);

    public Cart getCart(Long userId);

}
