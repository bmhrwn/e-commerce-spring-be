package com.e_commerce.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestUser implements Serializable {

    @NotBlank
    private String password;

    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String roles;

}
