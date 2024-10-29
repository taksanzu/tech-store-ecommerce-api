package org.tak.techstoreecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder() {
        String email = authUtil.loggedInEmail();
        OrderDTO orderDTO = orderService.createOrder(email);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

}
