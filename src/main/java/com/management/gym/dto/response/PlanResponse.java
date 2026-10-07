package com.management.gym.dto.response;

import java.math.BigDecimal;

public record PlanResponse(
        Long idPlan,
        String name,
        BigDecimal price,
        Integer durationMonths,
        Boolean active
) {
}
