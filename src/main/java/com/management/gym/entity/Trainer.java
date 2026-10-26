package com.management.gym.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TBL_TRAINERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTrainer;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(name) <> '')")
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(email) <> '')")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(speciality) <> '')")
    private String speciality;

    @Column(nullable = false)
    @NotNull
    private Boolean active;

}