package com.management.gym.service;

import com.management.gym.dto.request.member.MemberCreateRequestDTO;
import com.management.gym.dto.request.member.MemberUpdateRequestDTO;
import com.management.gym.dto.response.MemberResponse;
import com.management.gym.entity.Member;
import com.management.gym.entity.Plan;
import com.management.gym.exception.BusinessRuleException;
import com.management.gym.exception.ResourceNotFoundException;
import com.management.gym.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlanService planService;

    //Create a member
    public MemberResponse createMember(MemberCreateRequestDTO memberCreateRequestDTO) {
        Plan plan = planService.findPlanById(memberCreateRequestDTO.planID());

        validateInformation(memberCreateRequestDTO.email(), memberCreateRequestDTO.phone(), null);

        Member member = new Member();

        member.setName(memberCreateRequestDTO.name());
        member.setEmail(memberCreateRequestDTO.email());
        member.setPhone(memberCreateRequestDTO.phone());
        member.setRegistrationDate(LocalDate.now());
        member.setActive(true);

        if(plan.getActive() == true){
            member.setPlan(plan);
        } else {
            throw new BusinessRuleException("This plan isn't active.. try with another plan");
        }

        return responseReturn(memberRepository.save(member));
    }


    //Get all members
    public List<MemberResponse> getAllMembers(){
        return memberRepository.findAll()
                .stream()
                .map(this::responseReturn)
                .toList();
    }

    //Find by ID
    public MemberResponse getMemberByID(Long id){

        Member member = findMemberById(id);

        return responseReturn(member);
    }

    //Update a member by ID
    public MemberResponse updateMemberByID(MemberUpdateRequestDTO memberUpdateRequestDTO, Long id){
        Member member = findMemberById(id);

        validateInformation(memberUpdateRequestDTO.email(), memberUpdateRequestDTO.phone(),id);

        if (memberUpdateRequestDTO.planID() != null) {
            Plan plan = planService.findPlanById(memberUpdateRequestDTO.planID());

            if (!plan.getActive()) {
                throw new BusinessRuleException("This plan isn't active. Try another plan");
            }

            member.setPlan(plan);
        }

        if (memberUpdateRequestDTO.active() != null) member.setActive(memberUpdateRequestDTO.active());
        if (memberUpdateRequestDTO.name() != null && !memberUpdateRequestDTO.name().isBlank())
            member.setName(memberUpdateRequestDTO.name());
        if (memberUpdateRequestDTO.email() != null && !memberUpdateRequestDTO.email().isBlank())
            member.setEmail(memberUpdateRequestDTO.email());
        if (memberUpdateRequestDTO.phone() != null && !memberUpdateRequestDTO.phone().isBlank())
            member.setPhone(memberUpdateRequestDTO.phone());

        memberRepository.save(member);
        return responseReturn(member);
    }

    //Delete member by ID
    public MemberResponse deleteMemberByID(Long id){
        Member member = findMemberById(id);

        memberRepository.delete(member);

        return responseReturn(member);
    }

    //Find members by plan and optional if it's active
    public List<MemberResponse> membersByPlan(Long idPlan, Boolean active) {

        if (active == null) {
            return memberRepository.findByPlanIdPlan(idPlan)
                    .stream()
                    .map(this::responseReturn)
                    .toList();
        }

        return memberRepository.findByPlanIdPlanAndActive(idPlan, active)
                .stream()
                .map(this::responseReturn)
                .toList();
    }


    //Aux function for response
    private MemberResponse responseReturn(Member member){
       return new MemberResponse(
                member.getIdMember(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getRegistrationDate(),
                member.getActive(),
                member.getPlan().getIdPlan()
        );
    }

    //Aux method to validate email
    private void validateInformation(String email, String phone, Long memberId) {

        boolean existsEmail = memberId == null
                ? memberRepository.existsByEmail(email)
                : memberRepository.existsByEmailAndIdMemberNot(email, memberId);

        if (existsEmail) {
            throw new BusinessRuleException("The email was registered");
        }

        boolean existsPhone = memberId == null
                ? memberRepository.existsByPhone(phone)
                : memberRepository.existsByPhoneAndIdMemberNot(phone, memberId);

        if (existsPhone){
            throw new BusinessRuleException("The phone was registered");
        }
    }

    //Aux function to find a member
    public Member findMemberById(Long id){
        return memberRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("There's no member with that ID"));
    }

}
