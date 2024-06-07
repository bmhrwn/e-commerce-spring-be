package com.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.entity.OrderLog;

public interface OrderLogRepository extends JpaRepository<OrderLog,String> {

}
