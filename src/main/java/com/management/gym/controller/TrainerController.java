package com.management.gym.controller;

import com.management.gym.dto.request.trainer.TrainerCreateRequestDTO;
import com.management.gym.dto.request.trainer.TrainerUpdateRequestDTO;
import com.management.gym.dto.response.TrainerResponse;
import com.management.gym.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;

    @PostMapping
    public ResponseEntity<TrainerResponse> createTrainer(@Valid @RequestBody TrainerCreateRequestDTO trainerCreateRequestDTO){
        TrainerResponse trainerResponse = trainerService.createTrainer(trainerCreateRequestDTO);
        return new ResponseEntity<>(trainerResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TrainerResponse>> getAllTrainers(){
        List<TrainerResponse> trainerResponse = trainerService.getAllTrainers();
        return new ResponseEntity<>(trainerResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TrainerResponse> getTrainerById(@PathVariable Long id){
        TrainerResponse trainerResponse = trainerService.getTrainerById(id);
        return new ResponseEntity<>(trainerResponse, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TrainerResponse> updateTrainerById( @Valid
            @RequestBody TrainerUpdateRequestDTO trainerUpdateRequestDTO, @PathVariable Long id){
        TrainerResponse trainerResponse = trainerService.updateTrainerById(trainerUpdateRequestDTO, id);
        return new ResponseEntity<>(trainerResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TrainerResponse> deleteTrainerById(@PathVariable Long id){
        TrainerResponse trainerResponse = trainerService.deleteTrainerById(id);
        return new ResponseEntity<>(trainerResponse, HttpStatus.OK);
    }
}
