package org.tak.techstoreecommerce.service;

import org.tak.techstoreecommerce.dto.OrderDTO;

public interface OrderService {
    OrderDTO createOrder(String email);
}
