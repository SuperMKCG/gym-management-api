package com.management.gym.repository;

import com.management.gym.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    Boolean existsByEmailAndIdMemberNot(String email, Long id);
    Boolean existsByPhoneAndIdMemberNot(String phone, Long id);
    List<Member> findByPlanIdPlan(Long idPlan);
    List<Member> findByPlanIdPlanAndActive(Long idPlan, Boolean active);
}
