package com.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,String> {

}
