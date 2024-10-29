package org.tak.techstoreecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tak.techstoreecommerce.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByUserEmail(String email);
}
