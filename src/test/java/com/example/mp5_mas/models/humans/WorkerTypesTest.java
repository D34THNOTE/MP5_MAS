package com.example.mp5_mas.models.humans;

import com.example.mp5_mas.repository.ManagerRepository;
import com.example.mp5_mas.repository.MechanicRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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

    @Test
    public void testInvalidMechanic() {
        Mechanic invalidMechanic = Mechanic.builder()
                .firstName("Water")
                .lastName("")
                .dateOfBirth(LocalDate.of(2000, 6, 10))
                .salary(1500)
                .specialiationList(new HashSet<>(Collections.singleton("Fixing stuff")))
                .build();

        mechanicRepository.save(invalidMechanic);
        assertThrows(ConstraintViolationException.class, () -> entityManager.flush());
    }

    @Test
    public void testInvalidManager() {
        Manager invalidManager = Manager.builder()
                .firstName("")
                .lastName("Malone")
                .dateOfBirth(null)
                .salary(2500)
                .bonus(400)
                .build();

        managerRepository.save(invalidManager);
        assertThrows(ConstraintViolationException.class, () -> entityManager.flush());
    }



    // tests for Mechanic
    @Test
    public void testMechanicFindByFirstName() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Mechanic> foundHopefully = mechanicRepository.findByFirstName("Water");
        assertEquals(1, foundHopefully.size());
        assertEquals("Water", foundHopefully.get(0).getFirstName());
    }

    @Test
    public void testMechanicFindByLastName() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Mechanic> foundHopefully = mechanicRepository.findByLastName("Malone");
        assertEquals(2, foundHopefully.size());
    }

    @Test
    public void testMechanicFindByFirstNameAndLastName() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Mechanic> foundHopefully = mechanicRepository.findByFirstNameAndLastName("Water", "Malone");
        assertEquals(1, foundHopefully.size());
        assertEquals("Water", foundHopefully.get(0).getFirstName());
        assertEquals("Malone", foundHopefully.get(0).getLastName());
    }

    @Test
    public void testMechanicFindByFirstNameAndSalary() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Mechanic> foundHopefully = mechanicRepository.findByFirstNameAndSalary("Reply", 2200);
        assertEquals(1, foundHopefully.size());
        assertEquals("Reply", foundHopefully.get(0).getFirstName());
        assertEquals(2200, foundHopefully.get(0).getSalary());

        foundHopefully = mechanicRepository.findByFirstNameAndSalary("Water", 3000);
        assertEquals(0, foundHopefully.size());
    }

    @Test
    public void testMechanicFindAllBySalaryBiggerThan() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Mechanic> foundHopefully = mechanicRepository.findAllBySalaryBiggerThan(2000);
        assertEquals(1, foundHopefully.size());
        assertTrue(foundHopefully.get(0).getSalary() > 2000);
    }

    @Test
    public void testMechanicFindAllBySalarySmallerThan() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Mechanic> foundHopefully = mechanicRepository.findAllBySalarySmallerThan(2000);
        assertEquals(1, foundHopefully.size());
        assertTrue(foundHopefully.get(0).getSalary() < 2000);
    }




    // Test for Manager
    @Test
    public void testManagerFindByFirstName() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Manager> foundHopefully = managerRepository.findByFirstName("Post");
        assertEquals(1, foundHopefully.size());
        assertEquals("Post", foundHopefully.get(0).getFirstName());
    }

    @Test
    public void testManagerFindByLastName() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Manager> foundHopefully = managerRepository.findByLastName("Malone");
        assertEquals(2, foundHopefully.size());
    }

    @Test
    public void testManagerFindByFirstNameAndLastName() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Manager> foundHopefully = managerRepository.findByFirstNameAndLastName("Message", "Malone");
        assertEquals(1, foundHopefully.size());
        assertEquals("Message", foundHopefully.get(0).getFirstName());
        assertEquals("Malone", foundHopefully.get(0).getLastName());
    }

    @Test
    public void testManagerFindByFirstNameAndSalary() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Manager> foundHopefully = managerRepository.findByFirstNameAndSalary("Message", 2500);
        assertEquals(1, foundHopefully.size());
        assertEquals("Message", foundHopefully.get(0).getFirstName());
        assertEquals(2500, foundHopefully.get(0).getSalary());

        foundHopefully = managerRepository.findByFirstNameAndSalary("Message", 2400);
        assertEquals(0, foundHopefully.size());
    }

    @Test
    public void testManagerFindAllBySalaryBiggerThan() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Manager> foundHopefully = managerRepository.findAllBySalaryBiggerThan(2000);
        assertEquals(1, foundHopefully.size());
        assertTrue(foundHopefully.get(0).getSalary() > 2000);
    }

    @Test
    public void testManagerFindAllBySalarySmallerThan() {
        managerRepository.saveAll(Arrays.asList(manager1, manager2));
        mechanicRepository.saveAll(Arrays.asList(mechanic1, mechanic2));
        entityManager.flush();

        List<Manager> foundHopefully = managerRepository.findAllBySalarySmallerThan(2100);
        assertEquals(1, foundHopefully.size());
        assertTrue(foundHopefully.get(0).getSalary() < 2100);
    }
}