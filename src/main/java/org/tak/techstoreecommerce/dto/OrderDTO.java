package org.tak.techstoreecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;

    private Date orderDate;

    private String orderStatus;

    private Long totalAmount;

    private String shippingAddress;

    private String paymentMethod;

    private String paymentStatus;

    private Long userId;

    private List<OrderItemDTO> orderItems;

}
