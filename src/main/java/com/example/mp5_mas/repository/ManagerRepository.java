package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.humans.Manager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerRepository extends CrudRepository<Manager, Long> {

    public List<Manager> findByFirstName(String firstName);
    public List<Manager> findByLastName(String lastName);

    public List<Manager> findByFirstNameAndLastName(String firstName, String lastName);

    public List<Manager> findByFirstNameAndSalary(String firstName, double salary);

    @Query("FROM Manager WHERE salary > :salary")
    public List<Manager> findAllBySalaryBiggerThan(@Param("salary") double salary);

    @Query("FROM Manager WHERE salary < :salary")
    public List<Manager> findAllBySalarySmallerThan(@Param("salary") double salary);
}
