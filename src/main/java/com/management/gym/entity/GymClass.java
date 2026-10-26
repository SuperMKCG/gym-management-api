package com.management.gym.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_GYM_CLASSES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GymClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClass;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(name) <> '')")
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(schedule) <> '')")
    private String schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trainer", nullable = false)
    private Trainer trainer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "gym_class_member",
            joinColumns = @JoinColumn(name = "id_class"),
            inverseJoinColumns = @JoinColumn(name = "id_member")
    )
    private List<Member> members = new ArrayList<>();

    @Column(nullable = false)
    private Boolean active;
}