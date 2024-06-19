package com.e_commerce.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.dto.OrderRequest;
import com.e_commerce.dto.OrderResponse;
import com.e_commerce.entity.Order;
import com.e_commerce.security.service.UserDetailsImpl;
import com.e_commerce.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/order")
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('user')")
    public OrderResponse create(@AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody @Valid OrderRequest request) {

        return orderService.create(user.getUsername(), request);
    }

    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize("hasAuthority('user')")
    public Order cancelOrderUser(
        @AuthenticationPrincipal UserDetailsImpl user, 
        @PathVariable("orderId") String orderId
    ){
        return orderService.cancelOrder(orderId, user.getUsername());

    }

    @PatchMapping("/{orderId}/acc")
    @PreAuthorize("hasAuthority('user')")
    public Order accOrderUser(
        @AuthenticationPrincipal UserDetailsImpl user, 
        @PathVariable("orderId") String orderId
    ){
        return orderService.accOrder(orderId, user.getUsername());
    }

    @PatchMapping("/{orderId}/confirm")
    @PreAuthorize("hasAuthority('admin')")
    public Order confirmOrderUser(
        @AuthenticationPrincipal UserDetailsImpl user, 
        @PathVariable("orderId") String orderId
    ){
        return orderService.confirmPayment(orderId, user.getUsername());
    }

    @PatchMapping("/{orderId}/packing")
    @PreAuthorize("hasAuthority('admin')")
    public Order packingOrderUser(
        @AuthenticationPrincipal UserDetailsImpl user, 
        @PathVariable("orderId") String orderId
    ){
        return orderService.packing(orderId, user.getUsername());
    }

    @PatchMapping("/{orderId}/delivery")
    @PreAuthorize("hasAuthority('admin')")
    public Order deliveryOrderUser(
        @AuthenticationPrincipal UserDetailsImpl user, 
        @PathVariable("orderId") String orderId
    ){
        return orderService.delivery(orderId, user.getUsername());
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user')")
    public List<Order> findAllOrderUser(
    @RequestParam(name = "page", defaultValue = "0", required = false) int page,
    @RequestParam(name = "limit", defaultValue = "25", required = false) int limit,
    @AuthenticationPrincipal UserDetailsImpl user
    ) {
        return orderService.findAllOrderUser(user.getUsername(), page, limit);
    }
    

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public List<Order> findAllOrderUserAdmin(
    @RequestParam(name = "page", defaultValue = "0", required = false) int page,
    @RequestParam(name = "limit", defaultValue = "25", required = false) int limit,
    @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText
    ) {
        return orderService.search(filterText, page, limit);
    }
    

}
