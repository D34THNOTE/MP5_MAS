package com.example.mp5_mas.repository;

import com.example.mp5_mas.models.RepairShop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RepairShopRepositoryTest {

    @Autowired
    private RepairShopRepository repairShopRepository;

    @PersistenceContext
    private EntityManager entityManager;

    RepairShop repairShop1;
    @BeforeEach
    public void initializeData() {
        repairShop1 = RepairShop.builder()
                .name("Test repairex")
                .address("Kadrowa 84")
                .build();
    }

    @Test
    public void testRequiredDependencies() {
        assertNotNull(repairShopRepository);
    }

    @Test
    public void testFetchRepairShops() {
        Iterable<RepairShop> all = repairShopRepository.findAll();

        for(RepairShop repairShop : all) {
            System.out.println(repairShop);
        }
    }

    @Test
    public void testSaveRepairShop() {
        repairShopRepository.save(repairShop1);
        entityManager.flush(); // saved into the database immediately
        assertEquals(3, repairShopRepository.count());
    }

    @Test
    public void testSaveInvalidRepairShop() {
        RepairShop invalidRepairShop = RepairShop.builder().name("Invalid").address("2").build();
        repairShopRepository.save(invalidRepairShop);
        assertThrows(ConstraintViolationException.class, () -> entityManager.flush());
    }

    @Test
    public void testFindByName() {
        List<RepairShop> foundHopefully = repairShopRepository.findByName("Repairex");
        assertEquals(1, foundHopefully.size());
    }

    @Test
    public void testFindByNameAndAddress() {
        List<RepairShop> foundHopefully = repairShopRepository.findByNameAndAddress("Repairex", "Kadrowa 75a, Warszawa");
        assertEquals(1, foundHopefully.size());
    }

    @Test
    public void testFindByNameButSQLMethod() {
        List<RepairShop> foundHopefully = repairShopRepository.findByNameButWrittenAsQuery("Repairex");
        assertEquals(1, foundHopefully.size());
    }
}