package org.tak.techstoreecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tak.techstoreecommerce.dto.CartDTO;
import org.tak.techstoreecommerce.dto.ProductDTO;
import org.tak.techstoreecommerce.exception.APIException;
import org.tak.techstoreecommerce.exception.ResourceNotFoundException;
import org.tak.techstoreecommerce.model.Cart;
import org.tak.techstoreecommerce.model.Category;
import org.tak.techstoreecommerce.model.Product;
import org.tak.techstoreecommerce.model.ProductImage;
import org.tak.techstoreecommerce.repository.CartRepository;
import org.tak.techstoreecommerce.repository.CategoryRepository;
import org.tak.techstoreecommerce.repository.ProductImageRepository;
import org.tak.techstoreecommerce.repository.ProductRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Value("${project.image}/products")
    private String path;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        List<Product> products = productRepository.findProductsByCategory(category);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found in this category");
        }
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        products.forEach(product -> {
            List<String> productImages = productImageRepository.findProductImagesByProduct(product)
                    .stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList());
            productDTOS.forEach(productDTO -> {
                if (productDTO.getProductId().equals(product.getProductId())) {
                    productDTO.setProductImages(productImages);
                }
            });
        });
        return productDTOS;
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        List<String> productImages = productImageRepository.findProductImagesByProduct(product).stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setProductImages(productImages);
        return productDTO;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId, List<MultipartFile> productImage) throws IOException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        if (productRepository.existsProductByProductName(productDTO.getProductName())) {
            throw new APIException("Product already exists");
        }
        if (productImage.size() > 5) {
            throw new IllegalArgumentException("A product can have a maximum of 5 images.");
        }
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);

        double discountedPrice = productDTO.getProductPrice() - (productDTO.getProductPrice() * (productDTO.getDiscount()/100));
        product.setDiscountedPrice(discountedPrice);

        product.setProductImages(productImage.stream()
                .map(file -> {
                    try {
                        return fileService.uploadImage(path, file);
                    } catch (IOException e) {
                        throw new APIException("Failed to upload image");
                    }
                })
                .map(imageUrl -> {
                    ProductImage productImage1 = new ProductImage();
                    productImage1.setImageUrl(imageUrl);
                    productImage1.setProduct(product);
                    return productImage1;
                })
                .collect(Collectors.toList()));
        productRepository.save(product);

        ProductDTO savedProduct = modelMapper.map(product, ProductDTO.class);
        savedProduct.setProductImages(product.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList()));
        return savedProduct;
    }

    @Override
    public ProductDTO updateProduct(Long categoryId, Long productId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));

        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setProductPrice(productDTO.getProductPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setCategory(category);

        double discountedPrice = productDTO.getProductPrice() - (productDTO.getProductPrice() * (productDTO.getDiscount()/100));
        product.setDiscountedPrice(discountedPrice);

        Product updatedProduct = productRepository.save(product);
        List<Cart> carts = cartRepository.findCartsByProductId(productId);

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).collect(Collectors.toList());

        cartDTOs.forEach(cart -> cartServiceImpl.updateProductInCarts(cart.getId(), productId));

        ProductDTO updateProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);
        updateProductDTO.setProductImages(product.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList()));

        return updateProductDTO;
    }

    @Override
    public ProductDTO updateProductImages(Long productId, List<MultipartFile> file) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));

        List<ProductImage> productImages = productImageRepository.findProductImagesByProduct(product);
        if (productImages.size() + file.size() > 5) {
            throw new IllegalArgumentException("A product can have a maximum of 5 images.");
        }
            product.setProductImages(file.stream()
                .map(image -> {
                    try {
                        return fileService.uploadImage(path, image);
                    } catch (IOException e) {
                        throw new APIException("Failed to upload image");
                    }
                })
                .map(imageUrl -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(imageUrl);
                    productImage.setProduct(product);
                    return productImage;
                })
                .collect(Collectors.toList()));

        Product updatedProduct = productRepository.save(product);

        ProductDTO updateProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);
        updateProductDTO.setProductImages(updatedProduct.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList()));

        return updateProductDTO;
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        productImageRepository.deleteProductImagesByProduct(product);
        productRepository.delete(product);
        return "Product with id: " + productId + " deleted successfully";
    }

    @Override
    public String deleteProductImage(Long productId, Long imageId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        ProductImage productImage = product.getProductImages().stream()
                .filter(image -> image.getProductImageId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product Image", "Id", imageId));
        product.removeImage(productImage);
        productRepository.save(product);
        return "Product image with id: " + imageId + " deleted successfully";
    }
}
