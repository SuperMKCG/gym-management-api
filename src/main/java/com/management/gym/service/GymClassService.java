package com.management.gym.service;

import com.management.gym.dto.request.gymclass.GymClassMemberRequestDTO;
import com.management.gym.dto.request.gymclass.GymClCreateRequestDTO;
import com.management.gym.dto.request.gymclass.GymClUpdateRequestDTO;
import com.management.gym.dto.response.GymClassResponse;
import com.management.gym.entity.GymClass;
import com.management.gym.entity.Member;
import com.management.gym.entity.Trainer;
import com.management.gym.exception.BusinessRuleException;
import com.management.gym.exception.ResourceNotFoundException;
import com.management.gym.mapper.GymClassMapper;
import com.management.gym.repository.GymClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class GymClassService {
    @Autowired
    private GymClassRepository gymClassRepository;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private MemberService memberService;


    //Create a gym class
    public GymClassResponse createGymClass(GymClCreateRequestDTO gymClCreateRequestDTO){
        validateClassName(gymClCreateRequestDTO.name(), null);
        Trainer trainer = trainerService.findTrainerById(gymClCreateRequestDTO.trainerId());
        GymClass gymClass = new GymClass();
        gymClass.setName(gymClCreateRequestDTO.name());
        gymClass.setSchedule(gymClCreateRequestDTO.schedule());

        if(trainer.getActive().equals(true)){
            gymClass.setTrainer(trainer);
        } else {
            throw new BusinessRuleException("This trainer isn't active.. try adding another trainer");
        }

        gymClass.setActive(true);
        gymClassRepository.save(gymClass);

        return GymClassMapper.toResponse(gymClass);
    }

    //Add members to gym class
    public GymClassResponse addMembersToClass(Long idClass, GymClassMemberRequestDTO gymClassMemberRequestDTO){
        GymClass gymClass = findGymClassById(idClass);

        for(Long memberId: gymClassMemberRequestDTO.membersId()){
            Member member = memberService.findMemberById(memberId);

            if (member.getActive() == null || !member.getActive()) {
                throw new BusinessRuleException("The member with ID " + memberId + " isn't active.. try with another");
            }

            if (gymClass.getMembers().contains(member)) {
                throw new BusinessRuleException("The member with ID " + memberId + " is already registered.");
            }

            if(!gymClass.getActive().equals(true)){
                throw new BusinessRuleException("The class with ID " + gymClass.getIdClass() + " isn't active");
            }

            gymClass.getMembers().add(member);
        }

        GymClass updatedClass = gymClassRepository.save(gymClass);
        return GymClassMapper.toResponse(updatedClass);
    }

    //Get all classes
    public List<GymClassResponse> getAllClasses(){
        return gymClassRepository.findAll()
                .stream()
                .map(GymClassMapper::toResponse)
                .toList();
    }

    //Get class by id
    public GymClassResponse getClassById(Long id){
        GymClass gymClass = findGymClassById(id);

        return GymClassMapper.toResponse(gymClass);
    }

    //Update a class by id
    public GymClassResponse updateGymClass(GymClUpdateRequestDTO gymClUpdateRequestDTO, Long id){
        GymClass gymClass = findGymClassById(id);
        validateClassName(gymClUpdateRequestDTO.name(), id);

        if(gymClUpdateRequestDTO.trainerId() != null){
            Trainer trainer = trainerService.findTrainerById(gymClUpdateRequestDTO.trainerId());

            if (!trainer.getActive()) {
                throw new BusinessRuleException("This trainer isn't active. Try with another trainer");
            }

            gymClass.setTrainer(trainer);
        }

        if(gymClUpdateRequestDTO.name() != null && !gymClUpdateRequestDTO.name().isBlank())
            gymClass.setName(gymClUpdateRequestDTO.name());
        if(gymClUpdateRequestDTO.schedule() != null && !gymClUpdateRequestDTO.schedule().isBlank())
            gymClass.setSchedule(gymClUpdateRequestDTO.schedule());
        if(gymClUpdateRequestDTO.active() != null)
            gymClass.setActive(gymClUpdateRequestDTO.active());

        gymClassRepository.save(gymClass);

        return GymClassMapper.toResponse(gymClass);
    }


    //Delete gymclass by id
    @Transactional
    public GymClassResponse deleteGymClassById(Long id){
        GymClass gymClass = findGymClassById(id);
        gymClassRepository.delete(gymClass);

        return GymClassMapper.toResponse(gymClass);
    }

    // Remove a member from a gym class
    public GymClassResponse removeMemberFromClass(Long classId, Long memberId) {
        GymClass gymClass = findGymClassById(classId);

        Member member = memberService.findMemberById(memberId);

        if (!gymClass.getMembers().contains(member)) {
            throw new BusinessRuleException("The member with ID " + memberId + " is not enrolled in this class.");
        }

        gymClass.getMembers().remove(member);

        GymClass updatedClass = gymClassRepository.save(gymClass);

        return GymClassMapper.toResponse(updatedClass);
    }


    //Aux function to find a gym class
    private GymClass findGymClassById(Long id){
        return gymClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There's no gym class with this id..."));
    }

    //Aux function to avoid duplicate name
    private void validateClassName(String name, Long classId){
        Boolean existsClassName = classId == null
                ? gymClassRepository.existsByName(name)
                : gymClassRepository.existsByNameAndIdClassNot(name, classId);

        if(existsClassName){
            throw new BusinessRuleException("This name class is registered..try with another one");
        }
    }

}
