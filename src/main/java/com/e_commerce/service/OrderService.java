package com.e_commerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.e_commerce.controller.OrderController;
import com.e_commerce.dto.CartRequest;
import com.e_commerce.dto.OrderRequest;
import com.e_commerce.dto.OrderResponse;
import com.e_commerce.entity.Order;
import com.e_commerce.entity.OrderItem;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import com.e_commerce.exception.BadRequestException;
import com.e_commerce.model.StatusPemesanan;
import com.e_commerce.repository.OrderItemRepository;
import com.e_commerce.repository.OrderRepository;
import com.e_commerce.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderLogService orderLogService;

    @Transactional
    public OrderResponse create(String username, OrderRequest request) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setDate(new Date());
        order.setNumber(generateNomerPesanan());
        order.setUser(new User(username));
        order.setSendAddress(request.getSendAddress());
        order.setStatus(StatusPemesanan.DRAFT);
        order.setOrderTime(new Date());

        List<OrderItem> items = new ArrayList<>();
        for (CartRequest k : request.getItems()) {
            Product product = productRepository.findById(k.getProductId())
                    .orElseThrow(() -> new BadRequestException("Product " + k.getProductId() + " not found"));

            if (product.getStock() < k.getQty()) {
                throw new BadRequestException("Stock not enough!");
            }

            OrderItem oi = new OrderItem();
            oi.setId(UUID.randomUUID().toString());
            oi.setProduct(product);
            oi.setDescription(product.getName());
            oi.setQty(k.getQty());
            oi.setPrice(product.getPrice());
            oi.setTotal(new BigDecimal(product.getPrice().doubleValue() * k.getQty()));
            oi.setOrder(order);
            items.add(oi);
        }

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : items) {
            total = total.add(orderItem.getTotal());
        }

        order.setTotal(total);
        order.setShipCost(request.getShipCost());
        order.setTotalAll(order.getTotal().add(order.getShipCost()));

        Order saved = orderRepository.save(order);

        for (OrderItem orderItem : items) {
            orderItemRepository.save(orderItem);
            Product product = orderItem.getProduct();
            product.setStock(product.getStock() - orderItem.getQty());
            productRepository.save(product);
            cartService.delete(username, product.getId());
        }

        orderLogService.createLog(username, order, OrderLogService.DRAFT, "Order success created");

        OrderResponse orderResponse = new OrderResponse(saved, items);

        return orderResponse;
    }

    @Transactional
    public Order accOrder(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("order id " + orderId + " not found"));
        if (!userId.equals(order.getUser().getId())) {
            throw new BadRequestException("Orders can only be canceled by the person concerned");
        }

        if (!StatusPemesanan.DELIVERY.equals(order.getStatus())) {
            throw new BadRequestException("This order cannot be canceled because it has already been processed");
        }

        order.setStatus(StatusPemesanan.CANCEL);
        Order saved = orderRepository.save(order);

        orderLogService.createLog(userId, saved, OrderLogService.CANCEL, "Order success Acc");

        return saved;
    }

    @Transactional
    public Order cancelOrder(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("order id " + orderId + " not found"));
        if (!userId.equals(order.getUser().getId())) {
            throw new BadRequestException("Orders can only be canceled by the person concerned");
        }

        if (!StatusPemesanan.DRAFT.equals(order.getStatus())) {
            throw new BadRequestException("This order cannot be canceled because it has already been processed");
        }

        order.setStatus(StatusPemesanan.CANCEL);
        Order saved = orderRepository.save(order);

        orderLogService.createLog(userId, saved, OrderLogService.CANCEL, "Order success cancel");

        return saved;
    }

    public List<Order> findAllOrderUser(String userId, int page, int limit) {
        return orderRepository.findByUserId(userId, PageRequest.of(page, limit, Sort.by("orderTime").descending()));
    }

    public List<Order> search(String filterText, int page, int limit) {
    return orderRepository.search(filterText.toLowerCase(),
    PageRequest.of(page, limit, Sort.by("orderTime").descending()));
    }

    private String generateNomerPesanan() {
        return String.format("%016d", System.nanoTime());
    }

    @Transactional
    public Order confirmPayment(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("order id " + orderId + " not found"));

        if (!StatusPemesanan.DRAFT.equals(order.getStatus())) {
            throw new BadRequestException("This order cannot be Payment because it has already been processed");
        }

        order.setStatus(StatusPemesanan.PAYMENT);
        Order saved = orderRepository.save(order);

        orderLogService.createLog(userId, saved, OrderLogService.PAYMENT, "Payment success confirm");

        return saved;
    }

    @Transactional
    public Order packing(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("order id " + orderId + " not found"));

        if (!StatusPemesanan.PAYMENT.equals(order.getStatus())) {
            throw new BadRequestException("This order cannot be Packing because it has already been processed");
        }

        order.setStatus(StatusPemesanan.GASKET);
        Order saved = orderRepository.save(order);

        orderLogService.createLog(userId, saved, OrderLogService.GASKET, "Gasket success confirm");

        return saved;
    }

    @Transactional
    public Order delivery(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException("order id " + orderId + " not found"));

        if (!StatusPemesanan.GASKET.equals(order.getStatus())) {
            throw new BadRequestException("This order cannot be Gasket because it has already been processed");
        }

        order.setStatus(StatusPemesanan.DELIVERY);
        Order saved = orderRepository.save(order);

        orderLogService.createLog(userId, saved, OrderLogService.DELIVERY, "Delivery success confirm");

        return saved;
    }

}
