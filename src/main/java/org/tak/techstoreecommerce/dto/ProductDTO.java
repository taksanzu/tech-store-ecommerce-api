package org.tak.techstoreecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;

    private String productName;

    private String productDescription;

    private Double productPrice;

    private Double discount;

    private Double discountedPrice;

    private Integer productStock;

    private Long categoryId;

    private List<String> productImages = new ArrayList<>();
}
