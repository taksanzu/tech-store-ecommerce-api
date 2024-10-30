package org.tak.techstoreecommerce.service;

import org.tak.techstoreecommerce.dto.PaymentDTO;
import org.tak.techstoreecommerce.dto.PaymentRequestDTO;

public interface PaymentService {

    PaymentDTO initiatePayment(PaymentRequestDTO paymentRequestDTO);
}
