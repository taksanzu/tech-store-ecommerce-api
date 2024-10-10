package org.tak.techstoreecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @JoinColumn(name = "product_name")
    @NotBlank(message = "Product name is required")
    private String productName;

    @JoinColumn(name = "product_description")
    @NotBlank(message = "Product description is required")
    private String productDescription;

    @JoinColumn(name = "product_price")
    @Min(value = 0, message = "Product price must be greater than or equal to 0")
    private Double productPrice;

    @JoinColumn(name = "discount")
    private Double discount;

    @JoinColumn(name = "discounted_price")
    private Double discountedPrice;

    @JoinColumn(name = "product_stock")
    @Min(value = 0, message = "Product stock must be greater than or equal to 0")
    private Integer productStock;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductImage> productImages = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();

    @CreationTimestamp
    @JoinColumn(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @JoinColumn(name = "updated_at")
    private Date updatedAt;

    public void removeImage(ProductImage image) {
        productImages.remove(image);
        image.setProduct(null);
    }
}
