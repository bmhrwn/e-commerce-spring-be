package com.e_commerce.repository;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.e_commerce.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByUserId(String userId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE LOWER(o.number) LIKE %:filterText% OR LOWER(o.user.name) LIKE %:filterText%")
    List<Order> search(@Param("filterText") String filterText, Pageable pageable);


}
