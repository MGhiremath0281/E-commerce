package com.e_commerce.project.controller;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.project.model.Cart;
import com.e_commerce.project.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Cart> addItem(RequestEntity<AddItemRequest> requestEntity) {

        AddItemRequest request = requestEntity.getBody();

        Cart cart = cartService.addItem(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity());

        return ResponseEntity.ok(cart);
    }

    public static class AddItemRequest {
        private Long userId;
        private Long productId;
        private int quantity;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<Cart> updateQuantity(RequestEntity<AddItemRequest> requestEntity) {

        AddItemRequest request = requestEntity.getBody();

        Cart cart = cartService.updateQuantity(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity());

        return ResponseEntity.ok(cart);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeItem(RequestEntity<RemoveItemRequest> requestEntity) {

        RemoveItemRequest request = requestEntity.getBody();

        Cart cart = cartService.removeItem(
                request.getUserId(),
                request.getProductId());

        return ResponseEntity.ok(cart);
    }

    public static class RemoveItemRequest {

        private Long userId;
        private Long productId;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}
