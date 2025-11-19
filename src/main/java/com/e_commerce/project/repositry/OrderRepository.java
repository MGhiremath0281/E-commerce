package com.e_commerce.project.repositry;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.e_commerce.project.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
