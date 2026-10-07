package com.management.gym.dto.response;

public record TrainerResponse(
        Long idTrainer,
        String name,
        String email,
        String speciality,
        Boolean active
) {
}
