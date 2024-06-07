package com.e_commerce.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "categorys")
public class Category implements Serializable {

    @Id
    private String id;

    private String name;


}
