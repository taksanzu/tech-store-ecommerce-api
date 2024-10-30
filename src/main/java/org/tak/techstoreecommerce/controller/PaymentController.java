package org.tak.techstoreecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tak.techstoreecommerce.dto.PaymentDTO;
import org.tak.techstoreecommerce.dto.PaymentRequestDTO;
import org.tak.techstoreecommerce.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> initiatePayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentDTO paymentDTO = paymentService.initiatePayment(paymentRequestDTO);
        return ResponseEntity.ok(paymentDTO);
    }

}
