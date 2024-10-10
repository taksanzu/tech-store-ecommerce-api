package org.tak.techstoreecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tak.techstoreecommerce.model.Product;
import org.tak.techstoreecommerce.model.ProductImage;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findProductImagesByProduct(Product savedProduct);

    void deleteProductImagesByProduct(Product product);
}
