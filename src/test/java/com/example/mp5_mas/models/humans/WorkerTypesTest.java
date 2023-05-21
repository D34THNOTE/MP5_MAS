package com.example.mp5_mas.models.humans;

import com.example.mp5_mas.repository.ManagerRepository;
import com.example.mp5_mas.repository.MechanicRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WorkerTypesTest {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private MechanicRepository mechanicRepository;

    @PersistenceContext
    private EntityManager entityManager;


    Manager manager1, manager2;
    Mechanic mechanic1, mechanic2;

    @BeforeEach
    public void initializeData() {
        manager1 = Manager.builder()
                .firstName("Post")
                .lastName("Malone")
                .dateOfBirth(LocalDate.of(1996, 5, 12))
                .salary(2000)
                .bonus(200)
                .build();

        manager2 = Manager.builder()
                .firstName("Message")
                .lastName("Malone")
                .dateOfBirth(LocalDate.of(1997, 3, 17))
                .salary(2500)
                .bonus(400)
                .build();

        mechanic1 = Mechanic.builder()
                .firstName("Water")
                .lastName("Malone")
                .dateOfBirth(LocalDate.of(2000, 6, 10))
                .salary(1500)
                .specialiationList(new HashSet<>(Collections.singleton("Fixing stuff")))
                .build();

        mechanic2 = Mechanic.builder()
                .firstName("Reply")
                .lastName("Malone")
                .dateOfBirth(LocalDate.of(2001, 12, 12))
                .salary(2200)
                .specialiationList(new HashSet<>(Collections.singleton("Electric things")))
                .build();
    }

    @Test
    public void testRequiredDependencies() {
        assertNotNull(managerRepository);
        assertNotNull(mechanicRepository);
    }

    @Test
    public void testSaveAll() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();
        assertEquals(2, managerRepository.count());
        assertEquals(2, mechanicRepository.count());
    }
}