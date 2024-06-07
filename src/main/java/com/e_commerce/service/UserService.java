package com.e_commerce.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.dto.RequestUser;
import com.e_commerce.entity.User;
import com.e_commerce.exception.BadRequestException;
import com.e_commerce.exception.ResourceNotFoundException;
import com.e_commerce.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    
     public User findById(String id){
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id user not found"));
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public User create(RequestUser request){
        if(repository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already Exist!");
        }

        if(repository.existsByUsername(request.getUsername())){
            throw new BadRequestException("Username already Exist!");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setIsActive(true);
        user.setRoles(request.getRoles());
        return repository.save(user);
    }

    public User update(String id,RequestUser request){
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id user not found"));
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setIsActive(true);
        user.setRoles(request.getRoles());
        return repository.save(user);
    }
    
    public void deleteById(String id){
        repository.deleteById(id);
    }
}
