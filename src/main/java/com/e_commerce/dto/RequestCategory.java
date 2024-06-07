package com.e_commerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestCategory {

    @NotBlank
    private String name;
}
