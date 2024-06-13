package com.e_commerce.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest implements Serializable{

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
