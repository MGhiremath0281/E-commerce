package com.e_commerce.project.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e_commerce.project.model.Cart;
import com.e_commerce.project.model.CartItem;
import com.e_commerce.project.model.Order;
import com.e_commerce.project.model.OrderItem;
import com.e_commerce.project.repositry.OrderItemRepository;
import com.e_commerce.project.repositry.OrderRepository;
import com.e_commerce.project.service.CartService;
import com.e_commerce.project.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(Long userId) {
        // 1. Get user's cart
        Cart cart = cartService.getCart(userId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot create order.");
        }

        // 2. Create new order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        // 3. Convert cart items to order items
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSubTotal(cartItem.getSubTotal());
            orderItemRepository.save(orderItem);

            order.getItems().add(orderItem); // ensure order.items is populated
        }

        cartService.clearCart(userId);

        return order;
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }
}
