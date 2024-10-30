package org.tak.techstoreecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tak.techstoreecommerce.dto.PaymentDTO;
import org.tak.techstoreecommerce.dto.PaymentRequestDTO;
import org.tak.techstoreecommerce.model.Order;
import org.tak.techstoreecommerce.model.Payment;
import org.tak.techstoreecommerce.repository.OrderRepository;
import org.tak.techstoreecommerce.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentDTO initiatePayment(PaymentRequestDTO paymentRequestDTO) {
        Order order = orderRepository.findById(paymentRequestDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentStatus("PENDING");
        payment.setPaymentMethod(paymentRequestDTO.getPaymentMethod());
        payment.setAmount(paymentRequestDTO.getAmount());

        payment = paymentRepository.save(payment);

        PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);

        return paymentDTO;
    }

}
