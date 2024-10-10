package org.tak.techstoreecommerce.service;

import org.springframework.transaction.annotation.Transactional;
import org.tak.techstoreecommerce.dto.CartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> getAllCart();

    CartDTO getCartById(String email, Long cartId);

    CartDTO addToCart(Long productId, Integer quantity);

    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    void updateProductInCarts(Long cartId, Long productId);

    String deleteProductFromCart(Long cartId, Long productId);
}
