package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.Car;
import com.example.mp5_mas.models.RepairShop;
import com.example.mp5_mas.models.humans.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public List<Customer> findByFirstName(String firstName);

    @Query("FROM Customer as customer LEFT JOIN FETCH customer.cars WHERE customer.id = :id")
    public Optional<Customer> findById(@Param("id") Long id);

}
