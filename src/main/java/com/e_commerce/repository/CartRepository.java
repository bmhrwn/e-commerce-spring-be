package com.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUserIdAndProductId(String username, String productId);

}
