package com.e_commerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.e_commerce.entity.Order;
import com.e_commerce.entity.OrderItem;

import lombok.Data;

@Data
public class OrderResponse implements Serializable {

    private String id;

    private String number;

    private Date date;

    private String nameUser;

    private String sendAddress;

    private Date orderTime;

    private BigDecimal total;
    
    private BigDecimal shipCost;
    
    private BigDecimal totalAll;

    private List<OrderResponse.Item> items;

    public OrderResponse(Order order, List<OrderItem> orderItems){
        this.id = order.getId();
        this.number = order.getNumber();
        this.date = order.getDate();
        this.nameUser = order.getUser().getName();
        this.sendAddress = order.getSendAddress();
        this.orderTime = order.getOrderTime();
        this.total = order.getTotal();
        this.shipCost = order.getShipCost();
        this.totalAll = order.getTotalAll();
        items = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            Item item = new Item();
            item.setProductId(orderItem.getProduct().getId());
            item.setProductName(orderItem.getDescription());
            item.setQty(orderItem.getQty());
            item.setPrice(orderItem.getPrice());
            item.setTotal(orderItem.getTotal());
            items.add(item);
        }
    }

    @Data
    public static class Item implements Serializable {
        private String productId;
        private String productName;
        private Double qty;
        private BigDecimal price;
        private BigDecimal total;
    }
}
