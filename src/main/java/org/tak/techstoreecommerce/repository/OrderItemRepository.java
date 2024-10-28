package org.tak.techstoreecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tak.techstoreecommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
