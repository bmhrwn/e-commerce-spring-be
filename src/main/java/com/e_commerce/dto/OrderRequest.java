package com.e_commerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest implements Serializable {

    @NotNull
    private BigDecimal shipCost;

    @NotBlank
    private String sendAddress;

    @NotNull
    private List<CartRequest> items;
}
