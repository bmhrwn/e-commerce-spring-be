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
@Table(name = "products")
public class Product implements Serializable {

    @Id
    private String id;
    private String name;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn
    private Category category;

    private BigDecimal price;

    private Double stock;
}
