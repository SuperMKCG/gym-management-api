package com.management.gym.service;

import com.management.gym.dto.request.plan.PlanCreateRequestDTO;
import com.management.gym.dto.request.plan.PlanUpdateRequestDTO;
import com.management.gym.dto.response.PlanResponse;
import com.management.gym.entity.Plan;
import com.management.gym.exception.BusinessRuleException;
import com.management.gym.exception.ResourceNotFoundException;
import com.management.gym.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    //Create a plan
    public PlanResponse createPlan(PlanCreateRequestDTO planCreateRequestDTO) {
        doesNameExist(planCreateRequestDTO.name(), null);

        Plan plan = new Plan();
        plan.setName(planCreateRequestDTO.name());
        plan.setPrice(planCreateRequestDTO.price());
        plan.setDurationMonths(planCreateRequestDTO.durationMonths());
        plan.setActive(true);

        return responseReturn(planRepository.save(plan));
    }

    //Get all plans
    public List<PlanResponse> getAllPlans(){
        return planRepository.findAll().
                stream().
                map(this::responseReturn).
                toList();
    }

    //Get plan by ID
    public PlanResponse getPlanByID(Long id){
        Plan plan = findPlanById(id);

        return responseReturn(plan);
    }

    //Update a plan by ID
    public PlanResponse UpdatePlanByID(PlanUpdateRequestDTO planUpdateRequestDTO, Long id){
        Plan plan = findPlanById(id);

        doesNameExist(planUpdateRequestDTO.name(), id);

        if (planUpdateRequestDTO.name() != null && !planUpdateRequestDTO.name().isBlank()) {
            plan.setName(planUpdateRequestDTO.name());
        }

        if(planUpdateRequestDTO.Price() != null){
            plan.setPrice(planUpdateRequestDTO.Price());
        }

        if(planUpdateRequestDTO.durationMonths() != null){
            plan.setDurationMonths(planUpdateRequestDTO.durationMonths());
        }

        if(planUpdateRequestDTO.active() != null){
            plan.setActive(planUpdateRequestDTO.active());
        }

        return responseReturn(planRepository.save(plan));
    }

    //Delete a plan
    public PlanResponse deletePlan(Long id){
        Plan plan = findPlanById(id);

        planRepository.delete(plan);

        return responseReturn(plan);
    }


    //Aux function for response
    private PlanResponse responseReturn(Plan plan){
        return new PlanResponse(
                plan.getIdPlan(),
                plan.getName(),
                plan.getPrice(),
                plan.getDurationMonths(),
                plan.getActive()
        );
    }

    //Aux function for verify name's plan exists in DB
    private void doesNameExist(String name, Long idPlan){
        boolean exists = idPlan == null
                ? planRepository.existsByNameIgnoreCase(name)
                : planRepository.existsByNameIgnoreCaseAndIdPlanNot(name, idPlan);

        if(exists){
            throw new BusinessRuleException("This name of plan was registered");
        }
    }

    //Aux functions for find plan
    public Plan findPlanById(Long id){
        return planRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("There's no plan with that ID"));
    }

}
