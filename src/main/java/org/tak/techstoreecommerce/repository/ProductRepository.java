package org.tak.techstoreecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tak.techstoreecommerce.model.Category;
import org.tak.techstoreecommerce.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsProductByProductName(String productName);

    List<Product> findProductsByCategory(Category category);
}
