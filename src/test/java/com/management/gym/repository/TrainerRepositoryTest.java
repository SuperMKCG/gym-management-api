package com.management.gym.repository;

import com.management.gym.entity.Trainer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TrainerRepositoryTest {
    @Autowired
    private TrainerRepository trainerRepository;

    private Trainer trainer;

    @BeforeAll
    public static void beforeTesting(){
        System.out.println("====Beginning of trainer repository tests====");
    }

    @AfterAll
    public static void afterTesting(){
        System.out.println("====Ending of trainer repository testing====");
    }

    @BeforeEach
    public void init(){
        this.chargeTrainers();
    }

    public void chargeTrainers(){
        trainer = new Trainer(
                null,
                "Joe Kitahara",
                "jk@gmail.com",
                "trainer",
                true
        );

        trainerRepository.save(trainer);
    }

    //Test if exists an email
    @Test
    @DisplayName("email trainer exist test")
    public void existsByEmailTest(){
        Boolean existsEmail = trainerRepository.existsByEmail("jk@gmail.com");
        assertTrue(existsEmail);
    }

    //Test if exists an email with different ID
    @Test
    @DisplayName("email trainer exist with diff ID test")
    public void existsByEmailAndIdTrainerNot(){
        Boolean existEmDiffId = trainerRepository.existsByEmailAndIdTrainerNot("gk@gmail.com", 1L);
        assertFalse(existEmDiffId);
    }

}
