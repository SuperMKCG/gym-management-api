package com.management.gym.mapper;

import com.management.gym.dto.response.AuthResponse;

public class AuthMapper {
    public static AuthResponse toResponse(String token){
        if(token == null) return null;
        return new AuthResponse(
                token
        );
    }
}
