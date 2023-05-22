package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {

    @Query("FROM Car as car LEFT JOIN FETCH car.owner WHERE car.id = :id")
    public Optional<Car> findById(@Param("id") Long id);

}
