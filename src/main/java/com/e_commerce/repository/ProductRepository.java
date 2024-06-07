package com.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product,String> {

}
