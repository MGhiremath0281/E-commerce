package com.e_commerce.project.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import com.e_commerce.project.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
