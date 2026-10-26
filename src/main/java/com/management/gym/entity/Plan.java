package com.management.gym.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TBL_GYM_PLANS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (TRIM(name) <> '')")
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    @Positive(message = "The price must be greater than zero")
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull
    @Positive(message = "The months must be greater than zero")
    @Min(value = 1, message = "The months must be greater than 1")
    @Max(value = 12, message = "The months must be less or equal than 12")
    private Integer durationMonths;

    @Column(nullable = false)
    @NotNull
    private Boolean active;
}
