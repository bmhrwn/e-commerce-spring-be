package com.e_commerce.entity;

import java.io.Serializable;

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

    private String password;

    private String username;

    private String name;
    private String address;
    private String email;
    private String phone;
    private String roles;

    private Boolean isActive;


    
    public User(String username2) {
      this.id = username;
    }




    
}
