package org.tak.techstoreecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tak.techstoreecommerce.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
