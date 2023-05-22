package com.example.mp5_mas.models;

import com.example.mp5_mas.models.humans.Manager;
import com.example.mp5_mas.models.humans.Mechanic;
import com.example.mp5_mas.models.humans.Worker;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RepairShop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min=3)
    private String address;

    @OneToMany(mappedBy = "worksIn", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Manager> managers = new HashSet<>();

    @OneToMany(mappedBy = "worksIn", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Mechanic> mechanics = new HashSet<>();
}
