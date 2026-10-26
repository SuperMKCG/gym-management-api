package com.management.gym.controller;

import com.management.gym.dto.request.user.UserCreateRequestDTO;
import com.management.gym.dto.response.UserResponse;
import com.management.gym.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO){
        UserResponse response = customUserDetailsService.registerUser(userCreateRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
