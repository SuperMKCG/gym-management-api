package com.management.gym.service;

import com.management.gym.dto.request.trainer.TrainerCreateRequestDTO;
import com.management.gym.dto.request.trainer.TrainerUpdateRequestDTO;
import com.management.gym.dto.response.TrainerResponse;
import com.management.gym.entity.Trainer;
import com.management.gym.repository.TrainerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainerSaved;
    private TrainerCreateRequestDTO trainerInput;
    private TrainerUpdateRequestDTO trainerUpdate;
    private List<Trainer> trainerList = new ArrayList<>();

    @BeforeAll
    public static void beforeTesting(){
        System.out.println("===Beginning of testing====");
    }

    @AfterAll
    public static void afterTesting(){
        System.out.println("===Ending of testing===");
    }

    @BeforeEach
    public void init(){
        this.chargeTrainers();
    }

    public void chargeTrainers(){
        trainerSaved = new Trainer(
          1L,
          "Joe Kitahara",
          "jk@gmail.com",
          "trainer",
          true
        );

        trainerInput = new TrainerCreateRequestDTO(
                "Joe kitahara",
                "jk@hotmail.com",
                "trainer"
        );

        trainerUpdate = new TrainerUpdateRequestDTO(
                "Goku",
                "gok@gmail.com",
                "fighter",
                false
        );

        Trainer trainer2 = new Trainer(
                2L,
                "Yes",
                "y@gmail.com",
                "Cardio",
                true
        );

        Trainer trainer3 = new Trainer(
                3L,
                "Chris",
                "ch@gmail.com",
                "Dancing",
                true
        );

        trainerList = List.of(trainerSaved, trainer2, trainer3);
    }

    //Testing creation of trainer
    @Test
    @DisplayName("Trainer creation test")
    public void createTrainerTest(){
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainerSaved);
        when(trainerRepository.existsByEmail(anyString())).thenReturn(false);

        TrainerResponse result = trainerService.createTrainer(trainerInput);

        assertNotNull(result);
        assertEquals("Joe Kitahara".toLowerCase(), result.name().toLowerCase());
        assertTrue(result.active());

        verify(trainerRepository).save(any(Trainer.class));
    }

    //Testing get all trainers
    @Test
    @DisplayName("Get all trainers test")
    public void getAllTrainersTest(){
        when(trainerRepository.findAll()).thenReturn(trainerList);
        List<TrainerResponse> result = trainerService.getAllTrainers();

        assertNotNull(result);
        assertEquals("Joe Kitahara".toLowerCase(), result.get(0).name().toLowerCase());
        assertEquals("cardio".toLowerCase(), result.get(1).speciality().toLowerCase());

        verify(trainerRepository).findAll();
    }

    //Testing get trainer by ID
    @Test
    @DisplayName("Get trainer by id")
    public void getTrainerById(){
        when(trainerRepository.findById(any(Long.class))).thenReturn(Optional.of(trainerSaved));
        TrainerResponse result = trainerService.getTrainerById(1L);

        assertNotNull(result);
        assertEquals("jk@gmail.com",result.email());
        assertEquals("trainer", result.speciality());

        verify(trainerRepository).findById(1L);
    }

    //Testing update trainer by id
    @Test
    @DisplayName("Update data trainer by id test")
    public void updateTrainerByIdTest(){
        when(trainerRepository.findById(any(Long.class))).thenReturn(Optional.of(trainerSaved));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainerSaved);
        when(trainerRepository.existsByEmailAndIdTrainerNot(anyString(),any(Long.class))).thenReturn(false);

        TrainerResponse result = trainerService.updateTrainerById(trainerUpdate, 1L);

        assertNotNull(result);
        assertEquals("gok@gmail.com", result.email());
        assertFalse(result.active());

        verify(trainerRepository).findById(1L);
        verify(trainerRepository).save(any(Trainer.class));
    }

    //Testing delete trainer by id
    @Test
    @DisplayName("Delete trainer by id test")
    public void deleteTrainerById(){
        when(trainerRepository.findById(any(Long.class))).thenReturn(Optional.of(trainerSaved));

        TrainerResponse result = trainerService.deleteTrainerById(1L);
        assertNotNull(result);

        verify(trainerRepository).findById(1L);
        verify(trainerRepository).delete(trainerSaved);
    }

}