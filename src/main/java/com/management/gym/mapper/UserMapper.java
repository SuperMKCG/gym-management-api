package com.management.gym.mapper;

import com.management.gym.dto.response.UserResponse;
import com.management.gym.entity.User;

public class UserMapper {
    public static UserResponse toResponse(User user){
        if(user == null) return null;
        return new UserResponse(
                user.getIdUser(),
                user.getEmail(),
                user.getRole()
        );
    }
}
