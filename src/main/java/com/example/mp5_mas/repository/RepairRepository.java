package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.Repair;
import com.example.mp5_mas.models.humans.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RepairRepository extends CrudRepository<Repair, Long> {

    @Query("FROM Repair as repair LEFT JOIN FETCH repair.car LEFT JOIN FETCH repair.mechanic WHERE repair.id = :id")
    public Optional<Repair> findById(@Param("id") Long id);

}
