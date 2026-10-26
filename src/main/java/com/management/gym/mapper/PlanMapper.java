package com.management.gym.mapper;

import com.management.gym.dto.response.PlanResponse;
import com.management.gym.entity.Plan;

public class PlanMapper {
    public static PlanResponse toResponse(Plan plan){
        if(plan == null) return null;
        return new PlanResponse(
                plan.getIdPlan(),
                plan.getName(),
                plan.getPrice(),
                plan.getDurationMonths(),
                plan.getActive()
        );
    }
}
