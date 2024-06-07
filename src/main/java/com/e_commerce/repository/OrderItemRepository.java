package com.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,String> {

}
