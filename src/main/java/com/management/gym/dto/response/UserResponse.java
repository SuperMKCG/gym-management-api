package com.management.gym.dto.response;

public record UserResponse(
        Long idUser,
        String email,
        String role
) {
}
