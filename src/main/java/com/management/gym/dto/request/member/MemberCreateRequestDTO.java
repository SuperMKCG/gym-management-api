package com.management.gym.dto.request.member;

import jakarta.validation.constraints.*;

public record MemberCreateRequestDTO(
        @NotBlank(message = "The name is required")
        @Size(
                min = 3,
                max = 50,
                message = "The name must have between 3 and 50 characters"
        )
        String name,

        @NotBlank(message = "The email is required")
        @Email(message = "The email must have a valid format")
        String email,

        @NotBlank(message = "The phone is required")
        @Pattern(
                regexp = "^\\+?[0-9]{10,15}$",
                message = "The phone must have between 10 and 15 digits"
        )
        String phone,

        @NotNull(message = "You have to put the number of the id of one plan")
        Long planID
){}
