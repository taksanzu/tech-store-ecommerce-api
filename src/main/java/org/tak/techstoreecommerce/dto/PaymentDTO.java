package org.tak.techstoreecommerce.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {
    private Long paymentId;
    private Long orderId;
    private Date paymentDate;
    private String paymentMethod;
    private Double amount;
    private String paymentStatus;
    private String transactionId;
}
