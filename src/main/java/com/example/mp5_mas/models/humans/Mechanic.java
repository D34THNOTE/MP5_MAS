package com.example.mp5_mas.models.humans;

import com.example.mp5_mas.models.Repair;
import com.example.mp5_mas.models.RepairShop;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

// special builder for inheritance
@SuperBuilder
public class Mechanic extends Worker {

    @ElementCollection
    @CollectionTable(name = "Specializations List", joinColumns = @JoinColumn(name="mechanic_id_worker"))
    @Builder.Default
    private Set<String> specialiationList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_repairShop")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RepairShop worksIn;

    @OneToMany(mappedBy = "mechanic", cascade = CascadeType.REMOVE) // This cascade option means that if we remove the Mechanic we also remove the associations
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Repair> repairs;


}
