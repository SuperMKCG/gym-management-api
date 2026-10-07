package com.management.gym.dto.request.trainer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record TrainerUpdateRequestDTO(
        @Size(
                min = 3,
                max = 50,
                message = "The name must have between 3 and 50 characters"
        )
        String name,

        @Email(message = "The email must have a valid format")
        String email,

        @Size(
                min = 6,
                max = 25,
                message = "The speciality of the trainer must have between 6 and 25 characters"
        )
        String speciality,

        Boolean active
) {
}
