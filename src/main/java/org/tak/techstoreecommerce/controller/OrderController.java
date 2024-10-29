package org.tak.techstoreecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tak.techstoreecommerce.dto.OrderDTO;
import org.tak.techstoreecommerce.service.OrderService;
import org.tak.techstoreecommerce.util.AuthUtil;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> listOrders() {
        String email = authUtil.loggedInEmail();
        List<OrderDTO> orderDTOList = orderService.getAllOrdersByUser(email);
        return ResponseEntity.ok(orderDTOList);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder() {
        String email = authUtil.loggedInEmail();
        OrderDTO orderDTO = orderService.createOrder(email);
        return ResponseEntity.ok(orderDTO);
    }

}
