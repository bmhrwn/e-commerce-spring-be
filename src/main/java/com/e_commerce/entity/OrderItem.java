package com.e_commerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn
    private Product product;

    @ManyToOne
    @JoinColumn
    private Order order;

    private String description;

    private Double qty;

    private BigDecimal price;

    private BigDecimal total;

}
