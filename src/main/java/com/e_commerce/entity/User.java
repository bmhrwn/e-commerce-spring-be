package com.e_commerce.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements Serializable {


    @Id
    private String id;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String username;

    private String name;
    @JsonIgnore
    private String address;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String phone;
    @JsonIgnore
    private String roles;
    @JsonIgnore
    private Boolean isActive;


    
    public User(String username) {
      this.id = username;
    }




    
}
