package com.management.gym.dto.request.gymclass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GymClCreateRequestDTO(
        @NotBlank(message = "The name is required")
        @Size(
                min = 3,
                max = 50,
                message = "The name of class must have between 3 and 50 characters"
        )
        String name,

        @NotBlank(message = "The schedule is required")
        String schedule,

        @NotNull(message = "The id of the trainer is required")
        Long trainerId
) {
}
