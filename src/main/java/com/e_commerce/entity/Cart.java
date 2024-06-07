package com.e_commerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "carts")
public class Cart implements Serializable {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn
    private Product product;

    @ManyToOne
    @JoinColumn
    private User user;

    private Double qty;

    private BigDecimal price;

    private BigDecimal total;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
