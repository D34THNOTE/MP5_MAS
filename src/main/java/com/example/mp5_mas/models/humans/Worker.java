package com.example.mp5_mas.models.humans;

import com.example.mp5_mas.models.RepairShop;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@MappedSuperclass // no table for Worker, only its implementations will have tables
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idWorker;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 255, message = "Given last name is too long or too short")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 255, message = "Given last name is too long or too short")
    private String lastName;

    @NotNull(message = "Birth date is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Salary is required")
    @Min(value = 1000, message = "Salary cannot be smaller than 1000")
    private double salary;
}
