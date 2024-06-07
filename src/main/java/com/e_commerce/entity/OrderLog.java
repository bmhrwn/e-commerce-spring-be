package com.e_commerce.entity;

import java.io.Serializable;
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
@Table(name = "order_logs")
public class OrderLog implements Serializable {
 
    @Id
    private String id;

    @ManyToOne
    @JoinColumn
    private Order order;

    @ManyToOne
    @JoinColumn
    private User user;

    private Integer logType;

    private String logMessage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}
