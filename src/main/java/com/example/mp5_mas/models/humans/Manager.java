package com.example.mp5_mas.models.humans;

import com.example.mp5_mas.models.RepairShop;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

// special builder for inheritance
@SuperBuilder
public class Manager extends Worker {
    @NotNull
    @Min(value = 200, message = "Bonus cannot be smaller than 200")
    private double bonus;

    @ManyToOne
    @JoinColumn(name = "id_repairShop")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RepairShop worksIn;
}
