package com.management.gym.mapper;

import com.management.gym.dto.response.GymClassResponse;
import com.management.gym.entity.GymClass;
import com.management.gym.entity.Member;

import java.util.List;

public class GymClassMapper {
    public static GymClassResponse toResponse(GymClass gymClass){
        if(gymClass == null) return null;
        List<Long> memberIds = gymClass.getMembers().stream()
                .map(Member::getIdMember)
                .toList();

        return new GymClassResponse(
                gymClass.getIdClass(),
                gymClass.getName(),
                gymClass.getSchedule(),
                gymClass.getTrainer().getIdTrainer(),
                memberIds,
                gymClass.getActive()
        );
    }
}
