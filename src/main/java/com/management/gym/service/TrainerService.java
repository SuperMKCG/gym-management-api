package com.management.gym.service;

import com.management.gym.dto.request.trainer.TrainerCreateRequestDTO;
import com.management.gym.dto.request.trainer.TrainerUpdateRequestDTO;
import com.management.gym.dto.response.TrainerResponse;
import com.management.gym.entity.Trainer;
import com.management.gym.exception.BusinessRuleException;
import com.management.gym.exception.ResourceNotFoundException;
import com.management.gym.mapper.TrainerMapper;
import com.management.gym.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    //Create a trainer
    public TrainerResponse createTrainer(TrainerCreateRequestDTO trainerCreateRequestDTO){
        validateTrainerEmail(trainerCreateRequestDTO.email(), null);

        Trainer trainer = new Trainer();
        trainer.setName(trainerCreateRequestDTO.name());
        trainer.setEmail(trainerCreateRequestDTO.email());
        trainer.setSpeciality(trainerCreateRequestDTO.speciality());
        trainer.setActive(true);

        trainerRepository.save(trainer);

        return TrainerMapper.toResponse(trainer);
    }

    //Get all trainers
    public List<TrainerResponse> getAllTrainers(){
        return trainerRepository.findAll()
                .stream()
                .map(TrainerMapper::toResponse)
                .toList();
    }

    //Get trainer by id
    public TrainerResponse getTrainerById(Long id){
        Trainer trainer = findTrainerById(id);

        return TrainerMapper.toResponse(trainer);
    }

    //Update trainer by id
    public TrainerResponse updateTrainerById(TrainerUpdateRequestDTO trainerUpdateRequestDTO, Long id){
        Trainer trainer = findTrainerById(id);

        if(trainerUpdateRequestDTO.name() != null && !trainerUpdateRequestDTO.name().isBlank())
            trainer.setName(trainerUpdateRequestDTO.name());
        if(trainerUpdateRequestDTO.email() != null){
            validateTrainerEmail(trainerUpdateRequestDTO.email(), id);
            trainer.setEmail(trainerUpdateRequestDTO.email());
        }
        if(trainerUpdateRequestDTO.speciality() != null) trainer.setSpeciality(trainerUpdateRequestDTO.speciality());
        if(trainerUpdateRequestDTO.active() != null) trainer.setActive(trainerUpdateRequestDTO.active());

        trainerRepository.save(trainer);

        return TrainerMapper.toResponse(trainer);
    }

    //Delete trainer by id
    public TrainerResponse deleteTrainerById(Long id){
        Trainer trainer = findTrainerById(id);

        trainerRepository.delete(trainer);

        return TrainerMapper.toResponse(trainer);
    }


    // Aux function for find trainer by id
    public Trainer findTrainerById(Long id){
        return trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There's no trainer with this id"));
    }

    //Aux validate information for trainer
    private void validateTrainerEmail(String email, Long trainerId){
        Boolean existsTrainerEmail = trainerId == null
                ? trainerRepository.existsByEmail(email)
                : trainerRepository.existsByEmailAndIdTrainerNot(email, trainerId);

        if(existsTrainerEmail){
            throw new BusinessRuleException("This email is registered..try with another one");
        }
    }

}
