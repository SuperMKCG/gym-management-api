package com.management.gym.controller;

import com.management.gym.dto.request.plan.PlanCreateRequestDTO;
import com.management.gym.dto.request.plan.PlanUpdateRequestDTO;
import com.management.gym.dto.response.PlanResponse;
import com.management.gym.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@Valid @RequestBody PlanCreateRequestDTO planCreateRequestDTO){
        PlanResponse response = planService.createPlan(planCreateRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getAllPlans(){
        List<PlanResponse> response = planService.getAllPlans();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> getPlanByID(@PathVariable Long id){
        PlanResponse response = planService.getPlanByID(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlanResponse> updatePlanByID(@Valid @RequestBody PlanUpdateRequestDTO planUpdateRequestDTO,
                                                       @PathVariable Long id){
        PlanResponse response = planService.UpdatePlanByID(planUpdateRequestDTO,id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlanResponse> deletePlan(@PathVariable Long id){
        PlanResponse response = planService.deletePlan(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
