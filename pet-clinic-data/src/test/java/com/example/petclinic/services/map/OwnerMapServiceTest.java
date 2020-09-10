package com.example.petclinic.services.map;

import com.example.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long OWNER_ID_1 = 1L;
    final Long OWNER_ID_2 = 2L;

    final String OWNER_NAME_1 = "Johnson";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        Owner owner = new Owner();
        owner.setId(OWNER_ID_1);
        owner.setLastName(OWNER_NAME_1);

        Owner owner2 = new Owner();
        owner2.setId(OWNER_ID_2);
        owner2.setLastName("Thompson");

        ownerMapService.save(owner);
        ownerMapService.save(owner2);
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(OWNER_ID_1);
        assertEquals(OWNER_ID_1, owner.getId());
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
        ownerMapService.save(ownerMapService.findById(OWNER_ID_1));
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
        ownerMapService.deleteById(OWNER_ID_1);
        Set<Owner> all = ownerMapService.findAll();

        assertEquals(0, all.stream().filter(obj -> obj.getId().equals(OWNER_ID_1)).count());
    }

    @Test
    void delete() {
        Owner owner = ownerMapService.findById(OWNER_ID_1);
        ownerMapService.delete(owner);
        Set<Owner> all = ownerMapService.findAll();

        assertEquals(0, all.stream().filter(obj -> obj.getId().equals(OWNER_ID_1)).count());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerMapService.findByLastName(OWNER_NAME_1);

        assertNotNull(owner);
        assertEquals(OWNER_NAME_1, owner.getLastName());
    }
}