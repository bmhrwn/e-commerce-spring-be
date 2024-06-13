package com.e_commerce.service;


import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.entity.Cart;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import com.e_commerce.exception.BadRequestException;
import com.e_commerce.repository.CartRepository;
import com.e_commerce.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart addCart(String username, String productId,Double qty){
        //Apakah produk ada
        //apakah sudah ada didalam kernjang user 
        //jika ada maka update qty dan hitung total
        // jika belum ada maka tambahkan

        Product product = productRepository.findById(productId).orElseThrow(() -> new BadRequestException("Product " + productId+ " not Found"));

        Optional<Cart> optional = cartRepository.findByUserIdAndProductId(username,productId);
        Cart cart;
        if(optional.isPresent()){
            cart = optional.get();
            cart.setQty(cart.getQty() + qty);
            cart.setTotal(new BigDecimal(cart.getPrice().doubleValue() * cart.getQty()));
            cartRepository.save(cart);
        }else{
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
}
