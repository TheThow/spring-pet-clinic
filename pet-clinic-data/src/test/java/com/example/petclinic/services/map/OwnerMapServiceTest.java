package com.example.petclinic.services.map;

import com.example.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long ownerId = 1L;
    final Long owner2Id = 2L;

    final String ownerName = "Johnson";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(ownerName);

        Owner owner2 = new Owner();
        owner2.setId(owner2Id);
        owner2.setLastName("Thompson");

        ownerMapService.save(owner);
        ownerMapService.save(owner2);
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void save() {
        Owner owner3 = new Owner();
        owner3.setId(3L);
        owner3.setLastName("Peterson");
        Owner saved = ownerMapService.save(owner3);

        Set<Owner> all = ownerMapService.findAll();

        assertEquals(1, all.stream().filter(obj -> obj.getId().equals(3L)).count());
        assertEquals(3, all.size());
        assertNotNull(saved);
    }

    @Test
    void saveExisting() {
        ownerMapService.save(ownerMapService.findById(ownerId));
        Set<Owner> all = ownerMapService.findAll();

        assertEquals(2, all.size());
    }

    @Test
    void findAll() {
        Set<Owner> all = ownerMapService.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        Set<Owner> all = ownerMapService.findAll();

        assertEquals(0, all.stream().filter(obj -> obj.getId().equals(ownerId)).count());
    }

    @Test
    void delete() {
        Owner owner = ownerMapService.findById(ownerId);
        ownerMapService.delete(owner);
        Set<Owner> all = ownerMapService.findAll();

        assertEquals(0, all.stream().filter(obj -> obj.getId().equals(ownerId)).count());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerMapService.findByLastName(ownerName);

        assertNotNull(owner);
        assertEquals(ownerName, owner.getLastName());
    }
}