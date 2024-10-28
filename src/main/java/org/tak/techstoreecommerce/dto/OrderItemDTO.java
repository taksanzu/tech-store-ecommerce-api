package org.tak.techstoreecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tak.techstoreecommerce.model.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderItemId;

    private Product product;

    private Integer quantity;

    private Double discount;

    private Double totalAmount;
}
