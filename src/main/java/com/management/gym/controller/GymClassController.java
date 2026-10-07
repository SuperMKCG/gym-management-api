package com.management.gym.controller;

import com.management.gym.dto.request.gymclass.GymClCreateRequestDTO;
import com.management.gym.dto.request.gymclass.GymClUpdateRequestDTO;
import com.management.gym.dto.request.gymclass.GymClassMemberRequestDTO;
import com.management.gym.dto.response.GymClassResponse;
import com.management.gym.service.GymClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gymclass")
public class GymClassController {
    @Autowired
    private GymClassService gymClassService;

    @PostMapping
    public ResponseEntity<GymClassResponse> createGymClass(@Valid @RequestBody GymClCreateRequestDTO gymClCreateRequestDTO){
        GymClassResponse gymClassResponse = gymClassService.createGymClass(gymClCreateRequestDTO);
        return new ResponseEntity<>(gymClassResponse, HttpStatus.OK);
    }

    @PostMapping("/{idClass}/members")
    public ResponseEntity<GymClassResponse> addMembersToClass(@PathVariable Long idClass, @Valid @RequestBody GymClassMemberRequestDTO gymClassMemberRequestDTO){
        GymClassResponse gymClassResponse = gymClassService.addMembersToClass(idClass, gymClassMemberRequestDTO);
        return new ResponseEntity<>(gymClassResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<GymClassResponse>> getAllClasses(){
        List<GymClassResponse> gymClassResponses = gymClassService.getAllClasses();
        return new ResponseEntity<>(gymClassResponses, HttpStatus.OK);
    }

    @GetMapping("/{idClass}")
    public ResponseEntity<GymClassResponse> getClassById(@PathVariable Long idClass){
        GymClassResponse gymClassResponse = gymClassService.getClassById(idClass);
        return new ResponseEntity<>(gymClassResponse, HttpStatus.OK);
    }

    @PatchMapping("/{idClass}")
    public ResponseEntity<GymClassResponse> updateGymClass(@Valid @RequestBody GymClUpdateRequestDTO gymClUpdateRequestDTO, @PathVariable Long idClass){
        GymClassResponse gymClassResponse = gymClassService.updateGymClass(gymClUpdateRequestDTO,idClass);
        return new ResponseEntity<>(gymClassResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{idClass}")
    public ResponseEntity<GymClassResponse> deleteGymClassById(@PathVariable Long idClass){
        GymClassResponse gymClassResponse = gymClassService.deleteGymClassById(idClass);
        return new ResponseEntity<>(gymClassResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{classId}/members/{memberId}")
    public ResponseEntity<GymClassResponse> removeMemberFromClass(@PathVariable Long classId,
                                                                  @PathVariable Long memberId){
        GymClassResponse gymClassResponse = gymClassService.removeMemberFromClass(classId, memberId);
        return new ResponseEntity<>(gymClassResponse, HttpStatus.OK);
    }
}
