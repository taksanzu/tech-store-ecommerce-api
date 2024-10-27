package org.tak.techstoreecommerce.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tak.techstoreecommerce.dto.ProductDTO;
import org.tak.techstoreecommerce.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        ProductDTO product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<ProductDTO> addProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                                 @PathVariable Long categoryId,
                                                 @RequestPart("file") List<MultipartFile> file) throws IOException {
        ProductDTO savedProduct = productService.addProduct(productDTO, categoryId, file);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/category/{categoryId}/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long categoryId, @PathVariable Long productId, @Valid @ModelAttribute ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(categoryId, productId, productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PutMapping("/{productId}/images")
    public ResponseEntity<ProductDTO> updateProductImages(@PathVariable Long productId, @RequestParam("file") List<MultipartFile> file) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImages(productId, file);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        String deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Long productId, @PathVariable Long imageId) {
        String deletedProductImage = productService.deleteProductImage(productId, imageId);
        return new ResponseEntity<>(deletedProductImage, HttpStatus.OK);
    }

}
