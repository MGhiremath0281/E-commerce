package com.e_commerce.project.service;

import java.util.List;
import com.e_commerce.project.model.Order;

public interface OrderService {

    // Create order for a user from their cart
    Order createOrder(Long userId);

    // Get all orders of a user
    List<Order> getOrdersByUser(Long userId);

    // Get order by ID
    Order getOrderById(Long orderId);
}
