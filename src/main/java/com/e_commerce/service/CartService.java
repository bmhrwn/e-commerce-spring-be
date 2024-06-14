package com.e_commerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.entity.Cart;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import com.e_commerce.exception.BadRequestException;
import com.e_commerce.repository.CartRepository;
import com.e_commerce.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    public final static Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Cart addCart(String username, String productId, Double qty) {
        // Apakah produk ada
        // apakah sudah ada didalam kernjang user
        // jika ada maka update qty dan hitung total
        // jika belum ada maka tambahkan

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException("Product " + productId + " not Found"));

        Optional<Cart> optional = cartRepository.findByUserIdAndProductId(username, productId);
        Cart cart;
        if (optional.isPresent()) {
            cart = optional.get();
            cart.setQty(cart.getQty() + qty);
            cart.setTotal(new BigDecimal(cart.getPrice().doubleValue() * cart.getQty()));
            cartRepository.save(cart);
        } else {
            cart = new Cart();
            cart.setId(UUID.randomUUID().toString());
            cart.setQty(qty);
            cart.setPrice(product.getPrice());
            cart.setTotal(new BigDecimal(product.getPrice().doubleValue() * qty));
            cart.setProduct(product);
            cart.setUser(new User(username));
            cartRepository.save(cart);
        }

        return cart;
    }

    @Transactional
    public Cart update(String username, String productId, Double qty) {
        Cart cart = cartRepository.findByUserIdAndProductId(username, productId)
                .orElseThrow(() -> new BadRequestException("Product " + productId + " not found"));
        cart.setQty(qty);
        cart.setTotal(new BigDecimal(cart.getPrice().doubleValue() * qty));
        cartRepository.save(cart);
        return cart;
    }

    @Transactional
    public void delete(String username, String productId) {
        Cart cart = cartRepository.findByUserIdAndProductId(username, productId)
                .orElseThrow(() -> new BadRequestException("Product " + productId + " not found"));
        cartRepository.delete(cart);
    }

    public List<Cart> findByUserId(String username) {
        return cartRepository.findByUserId(username);
    }
}
