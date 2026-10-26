package com.management.gym.dto.request.plan;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PlanCreateRequestDTO(
        @NotBlank
        @Size(
                min = 3,
                max = 50,
                message = "The name of the plan must have between 3 and 50 characters"
        )
        String name,

        @NotNull(message = "The price is required")
        @DecimalMin(value = "0.01", message = "The price must be greater than 0")
        @Digits(integer = 4, fraction = 2, message = "The price must have up to 4 integer digits and 2 decimal places")
        @Positive(message = "The price must be positive")
        BigDecimal price,

        @NotNull(message = "The duration in months of the plan is required")
        @Digits(integer = 2, fraction = 0, message = "The months must have up to 2 integer digits")
        @Positive(message = "The number of months must be positive")
        Integer durationMonths
) {
}
