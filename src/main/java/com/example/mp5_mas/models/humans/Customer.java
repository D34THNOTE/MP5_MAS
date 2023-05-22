package com.example.mp5_mas.models.humans;

import com.example.mp5_mas.models.Car;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 255, message = "Given first name is too long or too short")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 255, message = "Given last name is too long or too short")
    private String lastName;

    @NotBlank(message = "Phone number name is required")
    @Size(min = 9, max = 16, message = "Given Phone number is too long or too short")
    private String phoneNumber;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Car> cars = new HashSet<>();
}
