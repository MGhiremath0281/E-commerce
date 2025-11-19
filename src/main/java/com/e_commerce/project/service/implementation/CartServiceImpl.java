package com.e_commerce.project.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.project.model.Cart;
import com.e_commerce.project.model.CartItem;
import com.e_commerce.project.model.Product;
import com.e_commerce.project.repositry.CartItemRepository;
import com.e_commerce.project.repositry.CartRepository;
import com.e_commerce.project.repositry.ProductRepositry;
import com.e_commerce.project.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepositry productRepositry;

    @Override
    public Cart getOrCreate(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    cart.setTotalPrice(0.0);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart addItem(Long userId, long productId, int quantity) {

        Cart cart = getOrCreate(userId);
        Product product = productRepositry.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        CartItem cartItem;

        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }

        cartItem.setSubTotal(product.getPrice() * cartItem.getQuantity());

        updateTotal(cart);

        return cartRepository.save(cart);
    }

    private void updateTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
        cart.setTotalPrice(total);
    }

    @Override
    public Cart updateQuantity(Long userId, Long productId, int quantity) {

        Cart cart = getOrCreate(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        item.setQuantity(quantity);
        item.setSubTotal(item.getProduct().getPrice() * quantity);

        updateTotal(cart);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItem(Long userId, Long productId) {

        Cart cart = getOrCreate(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        updateTotal(cart);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getOrCreate(userId);
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long userId) {
        return getOrCreate(userId);
    }
}
