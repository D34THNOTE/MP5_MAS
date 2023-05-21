package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.RepairShop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepairShopRepository extends CrudRepository<RepairShop, Long> {
    public List<RepairShop> findByName(String name);
    public List<RepairShop> findByNameAndAddress(String name, String address);

    @Query("FROM RepairShop WHERE name LIKE :name")
    public List<RepairShop> findByNameButWrittenAsQuery(@Param("name") String name);
}
