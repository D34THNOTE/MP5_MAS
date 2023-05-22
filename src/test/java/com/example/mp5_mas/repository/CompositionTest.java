package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.Car;
import com.example.mp5_mas.models.humans.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CompositionTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Customer customer1;

    Car car1;

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
    }


    @Test
    public void testRequiredDependencies() {
        assertNotNull(carRepository);
        assertNotNull(customerRepository);
    }

    @Test
    public void testSave() {
        customer1.getCars().add(car1);
        customerRepository.save(customer1);
        car1.setOwner(customer1);
        carRepository.save(car1);

        Optional<Car> byId = carRepository.findById(car1.getId());
        assertTrue(byId.isPresent());

        System.out.println(byId.get().getOwner());
        assertNotNull(byId.get().getOwner());
        assertEquals(customer1, byId.get().getOwner());

        // Customer findById test
        Optional<Customer> byIdCustomer = customerRepository.findById(customer1.getId());
        assertTrue(byIdCustomer.isPresent());

        System.out.println(byIdCustomer.get().getCars());
        assertNotNull(byIdCustomer.get().getCars());
        assertEquals(1, byIdCustomer.get().getCars().size());

        // .contains doesn't work properly unfortunately, so I have to use other methods of comparing
        Optional<Car> byIdCar = byIdCustomer.get().getCars().stream().findFirst();
        assertTrue(byIdCar.isPresent());

        assertEquals(car1.getId(), byIdCar.get().getId());
        assertEquals(car1.getManufacturer(), byIdCar.get().getManufacturer());
        assertEquals(car1.getModel(), byIdCar.get().getModel());
        assertEquals(car1.getOwner(), byIdCar.get().getOwner());

        // Testing if Car persists if we remove Customer(the "whole")
        customerRepository.delete(customer1);

        Optional<Car> shouldNotExist = carRepository.findById(car1.getId());
        assertFalse(shouldNotExist.isPresent());
    }

}
