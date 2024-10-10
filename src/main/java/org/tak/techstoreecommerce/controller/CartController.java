package org.tak.techstoreecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tak.techstoreecommerce.dto.CartDTO;
import org.tak.techstoreecommerce.model.Cart;
import org.tak.techstoreecommerce.repository.CartRepository;
import org.tak.techstoreecommerce.service.CartServiceImpl;
import org.tak.techstoreecommerce.util.AuthUtil;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartServiceImpl cartServiceImpl;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping
    public List<CartDTO> getAllCart() {
        return cartServiceImpl.getAllCart();
    }

    @GetMapping("/users")
    public ResponseEntity<CartDTO> getCartById() {
        String email = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(email);
        Long cartId = cart.getId();
        CartDTO cartDTO = cartServiceImpl.getCartById(email, cartId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PostMapping("/product/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity) {

        CartDTO cartDTO = cartServiceImpl.addToCart(productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation) {

        CartDTO cartDTO = cartServiceImpl.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId) {
        String status = cartServiceImpl.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }

}