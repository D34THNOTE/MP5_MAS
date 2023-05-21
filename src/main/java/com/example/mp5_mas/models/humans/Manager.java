package com.example.mp5_mas.models.humans;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

// special builder for inheritance
@SuperBuilder
public class Manager extends Worker {
    @Min(value = 200, message = "Bonus cannot be smaller than 200")
    private double bonus;
}
