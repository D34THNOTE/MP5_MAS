package com.example.mp5_mas.repository;
import com.example.mp5_mas.models.Car;
import com.example.mp5_mas.models.Repair;
import com.example.mp5_mas.models.humans.Customer;
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
public class WithAttributeTest {

    @Autowired
    private MechanicRepository mechanicRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Mechanic mechanic1;

    Customer customer1;

    Car car1;

    Repair repair1;



    @BeforeEach
    public void setup() {
        customer1 = Customer.builder()
                .firstName("Steve")
                .lastName("Jobs")
                .phoneNumber("974387546")
                .build();

        car1 = Car.builder()
                .manufacturer("Honda")
                .model("Civic")
                .owner(customer1)
                .build();

        mechanic1 = Mechanic.builder()
                .firstName("Water")
                .lastName("Malone")
                .dateOfBirth(LocalDate.of(2000, 6, 10))
                .salary(1500)
                .specialiationList(new HashSet<>(Collections.singleton("Fixing stuff")))
                .build();

        repair1 = Repair.builder()
//                .car(car1)
//                .mechanic(mechanic1)
                .cost(200)
                .startDate(LocalDate.now().minusDays(3))
                .build();
    }

    @Test
    public void testRequiredDependencies() {
        assertNotNull(carRepository);
        assertNotNull(mechanicRepository);
        assertNotNull(repairRepository);
    }


    @Test
    public void testSave() {
        // first have to save these objects
        customer1.getCars().add(car1);
        customerRepository.save(customer1);

        mechanic1.getRepairs().add(repair1);
        mechanicRepository.save(mechanic1);

        car1.getRepairs().add(repair1);
        carRepository.save(car1);

        repair1.setCar(car1);
        repair1.setMechanic(mechanic1);
        repairRepository.save(repair1);


        Optional<Repair> byId = repairRepository.findById(repair1.getId());
        assertTrue(byId.isPresent());

        assertEquals(mechanic1, byId.get().getMechanic());
        assertEquals(car1, byId.get().getCar());

        // Testing if removing Repair removes it from Mechanic's repairs and Car's repairs
        repairRepository.delete(repair1);

        Optional<Car> hopefullyNoRepair1Car = carRepository.findById(car1.getId());
        assertTrue(hopefullyNoRepair1Car.isPresent());
        assertFalse(hopefullyNoRepair1Car.get().getRepairs().contains(repair1));

        Optional<Mechanic> hopefullyNoRepair1Mechanic = mechanicRepository.findById(mechanic1.getIdWorker());
        assertTrue(hopefullyNoRepair1Mechanic.isPresent());
        assertFalse(hopefullyNoRepair1Mechanic.get().getRepairs().contains(repair1));
    }

}
