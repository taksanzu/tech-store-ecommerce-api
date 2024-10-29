package org.tak.techstoreecommerce.service;

import org.tak.techstoreecommerce.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(String email);

    List<OrderDTO> getAllOrdersByUser(String email);

    OrderDTO getOrderById(Long orderId);
}
