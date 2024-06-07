package com.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,String> {

}
