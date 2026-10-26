package com.management.gym.mapper;

import com.management.gym.dto.response.TrainerResponse;
import com.management.gym.entity.Trainer;

public class TrainerMapper {
    public static TrainerResponse toResponse(Trainer trainer){
        if(trainer == null) return null;
        return new TrainerResponse(
                trainer.getIdTrainer(),
                trainer.getName(),
                trainer.getEmail(),
                trainer.getSpeciality(),
                trainer.getActive()
        );
    }
}
