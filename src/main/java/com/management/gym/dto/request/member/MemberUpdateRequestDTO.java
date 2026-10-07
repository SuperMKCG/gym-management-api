package com.management.gym.dto.request.member;

import jakarta.validation.constraints.*;

public record MemberUpdateRequestDTO(
        @Size(
                min = 3,
                max = 50,
                message = "The name must have between 3 and 50 characters"
        )
        String name,

        @Email(message = "The email must have a valid format")
        String email,

        @Pattern(
                regexp = "^\\+?[0-9]{10,15}$",
                message = "The phone must have between 10 and 15 digits"
        )
        String phone,

        Boolean active,

        Long planID
) {
}
