package com.e_commerce.project.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.project.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
