package org.tak.techstoreecommerce.service;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import org.tak.techstoreecommerce.dto.ProductDTO;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getProductsByCategory(Long categoryId);

    ProductDTO getProductById(Long productId);

    ProductDTO addProduct(ProductDTO productDTO, Long categoryId, List<MultipartFile> file) throws IOException;

    ProductDTO updateProduct(Long categoryId, Long productId, @Valid ProductDTO productDTO);

    ProductDTO updateProductImages(Long productId, List<MultipartFile> file);

    String deleteProduct(Long productId);

    String deleteProductImage(Long productId, Long imageId);
}
