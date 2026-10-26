package com.management.gym.service;

import com.management.gym.dto.request.member.MemberCreateRequestDTO;
import com.management.gym.dto.request.member.MemberUpdateRequestDTO;
import com.management.gym.dto.response.MemberResponse;
import com.management.gym.entity.Member;
import com.management.gym.entity.Plan;
import com.management.gym.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PlanService planService;

    @InjectMocks
    private MemberService memberService;

    private Plan commonPlan, premiumPlan;
    private Member memberSaved;
    private MemberCreateRequestDTO memberInput;
    private MemberUpdateRequestDTO memberUpdate;
    private List<Member> memberList;

    @BeforeAll
    public static void beforeStart(){
        System.out.println("===Beginning of testing====");
    }

    @AfterAll
    public static void afterTesting(){
        System.out.println("===Ending of testing===");
    }

    @BeforeEach
    public void init(){
        this.chargeMembers();
    }

    public void chargeMembers(){
        commonPlan = new Plan(1L, "common", BigDecimal.valueOf(100.00), 12, true);
        premiumPlan = new Plan(2L, "premium", BigDecimal.valueOf(200.00), 12, true);

        memberSaved = new Member(
                1L,
                "Carlos Mendoza",
                "carlos.mendoza@email.com",
                "+525512345678",
                LocalDate.now(),
                true,
                commonPlan
        );

        memberInput = new MemberCreateRequestDTO(
                "Carlos Mendoza",
                "carlos.mendoza@email.com",
                "+525512345678",
                1L
        );

        memberUpdate = new MemberUpdateRequestDTO(
                "Silence suzuka",
                "",
                "559279012",
                true,
                2L
        );

        Member member2 = new Member(
                102L,
                "Ana Laura Gómez",
                "ana.gomez@email.com",
                "+34612345678",
                LocalDate.now(),
                true,
                commonPlan);

        Member member3 = new Member(
                103L,
                "Juan Pedro López",
                "juan.lopez@email.com",
                "+549112345678",
                LocalDate.of(2024, 6, 20),
                false,
                commonPlan);

        memberList = List.of(memberSaved, member2, member3);
    }

    //Testing creation of member
    @Test
    @DisplayName("Member creation test")
    public void createMemberTest(){

        when(planService.findPlanById(anyLong())).thenReturn(commonPlan);
        when(memberRepository.save(any(Member.class))).thenReturn(memberSaved);
        when(memberRepository.existsByEmail(anyString())).thenReturn(false);
        when(memberRepository.existsByPhone(anyString())).thenReturn(false);

        MemberResponse result = memberService.createMember(memberInput);

        assertNotNull(result);
        assertEquals(1L, result.idMember());
        assertEquals("Carlos Mendoza", result.name());

        verify(planService).findPlanById(1L);
        verify(memberRepository).save(any(Member.class));
    }

    //Testing searching all members
    @Test
    @DisplayName("Searching all members test")
    public void getAllMembersTest(){
        when(memberRepository.findAll()).thenReturn(memberList);

        List<MemberResponse> result = memberService.getAllMembers();

        assertNotNull(result);
        assertEquals(memberList.size(), result.size());
        assertEquals(memberSaved.getIdMember(), result.get(0).idMember());
        assertEquals(memberSaved.getName(), result.get(0).name());
        assertTrue(result.get(0).active());

        verify(memberRepository).findAll();
    }

    //Testing searching one member
    @Test
    @DisplayName("Searching member by ID test")
    public void getMemberByIDTest(){
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(memberSaved));

        MemberResponse result = memberService.getMemberByID(1L);

        assertNotNull(result);
        assertEquals("Carlos Mendoza", memberSaved.getName());
        assertEquals("common", commonPlan.getName());

        verify(memberRepository).findById(anyLong());
    }

    //Testing updating one member
    @Test
    @DisplayName("Updating a member by ID test")
    public void updateMemberByIDTest(){
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(memberSaved));
        when(planService.findPlanById(2L)).thenReturn(premiumPlan);
        when(memberRepository.existsByEmailAndIdMemberNot(anyString(), anyLong())).thenReturn(false);
        when(memberRepository.existsByPhoneAndIdMemberNot(anyString(), anyLong())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(memberSaved);

        MemberResponse memberUpdated = memberService.updateMemberByID(memberUpdate, 1L);

        assertNotNull(memberUpdated);
        assertEquals("Silence suzuka", memberUpdated.name());
        assertNotEquals("+525512345678", memberUpdated.phone());
        assertEquals(2L, memberUpdated.planId());

        verify(memberRepository).findById(1L);
        verify(planService).findPlanById(2L);
        verify(memberRepository).save(any(Member.class));
    }

    //Testing delete one member by id
    @Test
    @DisplayName("Deleting member by ID test")
    public void deleteMemberByIDTest(){
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(memberSaved));

        MemberResponse memberDeleted = memberService.deleteMemberByID(1L);

        assertNotNull(memberDeleted);

        verify(memberRepository).findById(1L);
        verify(memberRepository).delete(memberSaved);
    }

}
