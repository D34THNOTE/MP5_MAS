package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.RepairShop;
import com.example.mp5_mas.models.humans.Manager;
import com.example.mp5_mas.models.humans.Mechanic;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AssociationTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private MechanicRepository mechanicRepository;

    @Autowired
    private RepairShopRepository repairShopRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Mechanic mechanic1;

    Manager manager1;

    RepairShop repairShop1;

    @BeforeEach
    public void initializeData() {
        repairShop1 = RepairShop.builder()
                .name("Test repairex")
                .address("Kadrowa 84")
                .build();

        mechanic1 = Mechanic.builder()
                .firstName("Water")
                .lastName("Malone")
                .dateOfBirth(LocalDate.of(2000, 6, 10))
                .salary(1500)
                .specialiationList(new HashSet<>(Collections.singleton("Fixing stuff")))
                .build();

        manager1 = Manager.builder()
                .firstName("Post")
                .lastName("Malone")
                .dateOfBirth(LocalDate.of(1996, 5, 12))
                .salary(2000)
                .bonus(200)
                .build();
    }

    @Test
    public void testRequiredDependencies() {
        assertNotNull(repairShopRepository);
        assertNotNull(mechanicRepository);
        assertNotNull(managerRepository);
    }

    @Test
    public void testSave() {
        // MECHANIC
        repairShop1.getMechanics().add(mechanic1);
        repairShopRepository.save(repairShop1);
        mechanic1.setWorksIn(repairShop1);
        mechanicRepository.save(mechanic1);

        Optional<RepairShop> byId = repairShopRepository.findById_Mechanics(repairShop1.getId());
        assertTrue(byId.isPresent());

        System.out.println(byId.get().getMechanics());
        assertEquals(1, byId.get().getMechanics().size());
        assertTrue(repairShop1.getMechanics().contains(mechanic1));

        // MANAGER
        repairShop1.getManagers().add(manager1);
        repairShopRepository.save(repairShop1);
        manager1.setWorksIn(repairShop1);
        managerRepository.save(manager1);

        byId = repairShopRepository.findById_Managers(repairShop1.getId());
        assertTrue(byId.isPresent());

        System.out.println(byId.get().getManagers());
        assertEquals(1, byId.get().getManagers().size());
        assertTrue(repairShop1.getManagers().contains(manager1));



        // MECHANIC AND MANAGER
        byId = repairShopRepository.findById_ManagersAndMechanics(repairShop1.getId());
        assertTrue(byId.isPresent());

        System.out.println(byId.get().getMechanics());
        assertEquals(1, byId.get().getMechanics().size());
        assertTrue(repairShop1.getMechanics().contains(mechanic1));

        System.out.println("\n------------------------------------\n" + byId.get().getManagers());
        assertEquals(1, byId.get().getManagers().size());

        assertEquals(200, manager1.getBonus());
        assertTrue(repairShop1.getManagers().contains(manager1));
    }


}
