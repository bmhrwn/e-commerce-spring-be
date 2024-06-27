package com.e_commerce.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.dto.RequestCategory;
import com.e_commerce.entity.Category;
import com.e_commerce.exception.ResourceNotFoundException;
import com.e_commerce.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category findById(String id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id not found"));
    }

    public List<Category> findAll(){
        return repository.findAll();
    }

    public Category create(RequestCategory request){
        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setName(request.getName());
        return repository.save(category);
    }

    public Category update(Category request){
        return repository.save(request);
    }
    
    public void deleteById(String id){
        repository.deleteById(id);
    }
}
