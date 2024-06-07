package com.e_commerce.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.dto.RequestProduct;
import com.e_commerce.entity.Category;
import com.e_commerce.entity.Product;
import com.e_commerce.exception.ResourceNotFoundException;
import com.e_commerce.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired 
    private CategoryService service;

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product create(RequestProduct request){
        Category category = service.findById(request.getCategory_id());
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);
        return repository.save(product);
    }

    public Product update(String id,RequestProduct request){
        Category category = service.findById(request.getCategory_id());
        Product product = this.findById(id);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);
        return repository.save(product);
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }

    public Product findById(String id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));
    }

    public Product updateImage(String id,String image){
        Product product = findById(id);
        product.setImage(image);
        return repository.save(product);
    }

}
