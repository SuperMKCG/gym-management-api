package com.management.gym.dto.request.plan;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

public record PlanUpdateRequestDTO(

        @Size(
                min = 3,
                max = 50,
                message = "The name of the plan must have between 3 and 50 characters"
        )
        String name,

        @DecimalMin(value = "0.01", message = "The price must be greater than 0")
        @Digits(integer = 4, fraction = 2, message = "The price must have up to 4 integer digits and 2 decimal places")
        BigDecimal Price,

        @Positive(message = "The number of months must be positive")
        @Min(value = 1, message = "The months must be greater than 1")
        @Max(value = 12, message = "The months must be less or equal than 12")
        Integer durationMonths,

        Boolean active

) {
}
