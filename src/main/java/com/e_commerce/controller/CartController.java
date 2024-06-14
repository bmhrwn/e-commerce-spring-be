package com.e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.dto.CartRequest;
import com.e_commerce.entity.Cart;
import com.e_commerce.security.service.UserDetailsImpl;
import com.e_commerce.service.CartService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public List<Cart> findByUserId(@AuthenticationPrincipal UserDetailsImpl user) {
        return cartService.findByUserId(user.getUsername());
    }

    @PostMapping("")
    public Cart createCart(@RequestBody @Valid CartRequest request ,@AuthenticationPrincipal UserDetailsImpl user) {
        return cartService.addCart(user.getUsername(), request.getProductId(), request.getQty());
    }

    @PatchMapping("/{productId}")
    public Cart update(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable("productId") String productId, @RequestParam("qty") Double qty){
        return cartService.update(user.getUsername(), productId, qty);
    }

    @DeleteMapping("/{productId}")
    public void delete(@PathVariable("productId") String productId, @AuthenticationPrincipal UserDetailsImpl user){
         cartService.delete(user.getUsername(), productId);
    }
}
