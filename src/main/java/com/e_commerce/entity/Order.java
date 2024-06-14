package com.e_commerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.e_commerce.model.StatusPemesanan;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order implements Serializable{

    @Id
    private String id;

    private String number;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn
    private User user;

    private String sendAddress;

    private BigDecimal total;

    private BigDecimal shipCost;

    private BigDecimal totalAll;

    @Enumerated(EnumType.STRING)
    private StatusPemesanan status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;

}
