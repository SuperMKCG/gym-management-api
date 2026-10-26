package com.management.gym.repository;

import com.management.gym.entity.Member;
import com.management.gym.entity.Plan;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlanRepository planRepository;

    private Plan plan;
    private Member member;

    @BeforeAll
    public static void beforeTesting(){
        System.out.println("====Beginning of member repository tests====");
    }

    @AfterAll
    public static void afterTesting(){
        System.out.println("====Ending of member repository testing====");
    }

    @BeforeEach
    public void init(){
        this.chargeMember();
    }

    public void chargeMember(){
        plan = new Plan(null, "common", BigDecimal.valueOf(100.00), 12, true);
        member = new Member(null,"Abel Tesfaye","abel@gmail.com","3346778902",LocalDate.now(),true,plan);

        planRepository.save(plan);
        memberRepository.save(member);
    }

    //Test if exist email
    @Test
    @DisplayName("Exists email test")
    public void existsEmailTest() {
        Boolean exists = memberRepository.existsByEmail("joe@gym.com");
        assertFalse(exists);
    }

    //Test if exist phone
    @Test
    @DisplayName("Exists phone test")
    public void existsPhoneTest(){
        Boolean isPhoneExist = memberRepository.existsByPhone("3346778902");
        assertTrue(isPhoneExist);
    }

}
