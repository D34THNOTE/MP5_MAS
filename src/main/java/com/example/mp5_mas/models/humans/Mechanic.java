package com.example.mp5_mas.models.humans;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
}
