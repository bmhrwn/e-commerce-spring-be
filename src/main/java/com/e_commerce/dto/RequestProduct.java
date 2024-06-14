package com.e_commerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestProduct implements Serializable {


    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String image;

    @NotBlank
    private String category_id;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Double stock;
}
