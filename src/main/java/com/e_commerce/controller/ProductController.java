package com.e_commerce.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.dto.RequestProduct;
import com.e_commerce.entity.Product;
import com.e_commerce.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
@PreAuthorize("isAuthenticated()")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping()
    public ResponseEntity findAll() {
        List<Product> data = service.findAll();
        HashMap response = new HashMap<>();
        response.put("success", true);
        response.put("status_code", 200);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Product data = service.findById(id);
        HashMap response = new HashMap<>();
        response.put("success", true);
        response.put("status_code", 200);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody @Valid RequestProduct request) {
        Product data = service.create(request);
        HashMap response = new HashMap<>();
        response.put("success", true);
        response.put("status_code", 201);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid RequestProduct request) {
        Product data = service.update(id, request);
        HashMap response = new HashMap<>();
        response.put("success", true);
        response.put("status_code", 200);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        service.deleteById(id);
        HashMap response = new HashMap<>();
        response.put("success", true);
        response.put("status_code", 200);
        response.put("messages", "Success Delete Data");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
