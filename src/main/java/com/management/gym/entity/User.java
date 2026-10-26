package com.management.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TBL_USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(email) <> '')")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(password) <> '')")
    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(role) <> '')")
    private String role;
}
