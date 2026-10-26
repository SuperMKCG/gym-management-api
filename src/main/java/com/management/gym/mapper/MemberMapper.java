package com.management.gym.mapper;

import com.management.gym.dto.response.MemberResponse;
import com.management.gym.entity.Member;

public class MemberMapper {
    public static MemberResponse toResponse(Member member){
        if(member == null) return null;
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
}
