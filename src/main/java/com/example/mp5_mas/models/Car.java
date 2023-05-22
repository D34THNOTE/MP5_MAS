package com.example.mp5_mas.models;

import com.example.mp5_mas.models.humans.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotBlank(message = "Model is required")
    private String model;

    @OneToMany(mappedBy = "car", cascade = CascadeType.REMOVE) // This cascade option means that if we remove the Mechanic we also remove the associations
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Repair> repairs = new HashSet<>();

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_owner", nullable = false, updatable = false)
    private Customer owner;
}
