package com.management.gym.dto.request.gymclass;

import jakarta.validation.constraints.Size;

public record GymClUpdateRequestDTO(
        @Size(
                min = 3,
                max = 50,
                message = "The name of class must have between 3 and 50 characters"
        )
        String name,

        String schedule,

        Long trainerId,

        Boolean active
) {
}
