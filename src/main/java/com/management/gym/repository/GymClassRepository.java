package com.management.gym.repository;

import com.management.gym.entity.GymClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymClassRepository extends JpaRepository<GymClass, Long> {
    Boolean existsByName(String name);
    Boolean existsByNameAndIdClassNot(String email, Long id);
}
