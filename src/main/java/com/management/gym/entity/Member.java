package com.management.gym.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_MEMBERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMember;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(name) <> '')")
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(email) <> '')")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(phone) <> '')")
    private String phone;

    @Column(nullable = false)
    @NotNull
    private LocalDate registrationDate;

    @Column(nullable = false)
    @NotNull
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan", nullable = false)
    @NotNull
    private Plan plan;
}
