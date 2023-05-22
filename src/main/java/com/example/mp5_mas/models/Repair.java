package com.example.mp5_mas.models;

import com.example.mp5_mas.models.humans.Mechanic;
import com.example.mp5_mas.models.humans.Worker;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_car", "id_mechanic"})
})
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_car", nullable = false)
    @NotNull
    private Car car;

    @ManyToOne
    @JoinColumn(name = "id_mechanic", nullable = false)
    @NotNull
    private Mechanic mechanic;


    @NotNull(message = "Cost of the repair is required")
    @Min(value = 0, message = "Cost cannot be smaller than 0")
    private double cost;

    @NotNull(message = "Start date of the repair is required")
    private LocalDate startDate;

    private LocalDate endDate;
}
