package com.e_commerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;

    public JwtResponse(
            String accessToken,
            String email,
            String username) {
        this.username = username;
        this.email = email;
        this.token = accessToken;
    }
}
