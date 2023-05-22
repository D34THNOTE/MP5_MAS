package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.RepairShop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepairShopRepository extends CrudRepository<RepairShop, Long> {
    public List<RepairShop> findByName(String name);
    public List<RepairShop> findByNameAndAddress(String name, String address);

    @Query("FROM RepairShop WHERE name LIKE :name")
    public List<RepairShop> findByNameButWrittenAsQuery(@Param("name") String name);

    @Query("FROM RepairShop as rs LEFT JOIN FETCH rs.managers WHERE rs.id = :id")
    public Optional<RepairShop> findById_Managers(@Param("id") Long id);


    @Query("FROM RepairShop as rs LEFT JOIN FETCH rs.mechanics WHERE rs.id = :id")
    public Optional<RepairShop> findById_Mechanics(@Param("id") Long id);

    @Query("FROM RepairShop as rs LEFT JOIN FETCH rs.managers LEFT JOIN FETCH rs.mechanics WHERE rs.id = :id")
    public Optional<RepairShop> findById_ManagersAndMechanics(@Param("id") Long id);

}
