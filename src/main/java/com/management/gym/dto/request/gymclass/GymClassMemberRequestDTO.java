package com.management.gym.dto.request.gymclass;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record GymClassMemberRequestDTO(
        @NotEmpty(message = "You need to add at least one member...")
        List<Long> membersId
) {
}