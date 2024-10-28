package org.tak.techstoreecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tak.techstoreecommerce.dto.OrderDTO;
import org.tak.techstoreecommerce.service.OrderService;
import org.tak.techstoreecommerce.util.AuthUtil;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder() {
        String email = authUtil.loggedInEmail();
        OrderDTO orderDTO = orderService.createOrder(email);
        return ResponseEntity.ok(orderDTO);
    }

}
