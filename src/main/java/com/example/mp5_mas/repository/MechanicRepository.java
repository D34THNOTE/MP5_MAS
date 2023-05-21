package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.humans.Mechanic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MechanicRepository extends CrudRepository<Mechanic, Long> {
    public List<Mechanic> findByFirstName(String firstName);
    public List<Mechanic> findByLastName(String lastName);

    public List<Mechanic> findByFirstNameAndLastName(String firstName, String lastName);

    public List<Mechanic> findByFirstNameAndSalary(String firstName, double salary);

    @Query("FROM Mechanic WHERE salary > :salary")
    public List<Mechanic> findAllBySalaryBiggerThan(@Param("salary") double salary);

    @Query("FROM Mechanic WHERE salary < :salary")
    public List<Mechanic> findAllBySalarySmallerThan(@Param("salary") double salary);
}
