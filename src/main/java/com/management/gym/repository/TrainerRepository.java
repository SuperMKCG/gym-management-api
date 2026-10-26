package com.management.gym.repository;

import com.management.gym.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByEmailAndIdTrainerNot(String email, Long id);
}
