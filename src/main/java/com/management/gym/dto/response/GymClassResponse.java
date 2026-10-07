package com.management.gym.dto.response;

import java.util.List;

public record GymClassResponse(
        Long idClass,
        String name,
        String schedule,
        Long idTrainer,
        List<Long> idMembers,
        Boolean active
) {
}
