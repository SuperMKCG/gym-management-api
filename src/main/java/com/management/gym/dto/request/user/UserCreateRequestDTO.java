package com.management.gym.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequestDTO(
        @NotBlank(message = "The email is required")
        @Email(message = "The email must have a valid format")
        String email,

        @NotBlank(message = "The password is required")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d\\s])\\S{8,}$", message = "Password must be at least 8 characters long and include an uppercase letter, a lowercase letter, a number, and a special character. Spaces are not allowed")
        String password,

        @NotBlank(message = "The role of the user is required")
        String role
) {
}