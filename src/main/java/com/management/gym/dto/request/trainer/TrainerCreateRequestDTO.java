package com.management.gym.dto.request.trainer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrainerCreateRequestDTO(

        @NotBlank(message = "The name is required")
        @Size(
                min = 3,
                max = 50,
                message = "The name of the trainer must have between 3 and 50 characters"
        )
        String name,

        @NotBlank(message = "The email is required")
        @Email(message = "The email must have a valid format")
        String email,

        @NotBlank(message = "The speciality is required")
        @Size(
                min = 6,
                max = 25,
                message = "The speciality of the trainer must have between 6 and 25 characters"
        )
        String speciality
) {
}
