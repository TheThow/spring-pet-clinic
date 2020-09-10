package com.example.petclinic.services.jpa;

import com.example.petclinic.model.Owner;
import com.example.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerJPAServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerJPAService ownerJPAService;

    private final Long OWNER_ID = 1L;
    private final String OWNER_NAME = "Smith";

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByLastName() {
        Owner dummy = new Owner();
        dummy.setId(OWNER_ID);
        dummy.setLastName(OWNER_NAME);

        when(ownerRepository.findByLastName(any()))
                .thenReturn(dummy);

        Owner returnedOwner = ownerJPAService.findByLastName(OWNER_NAME);

        assertNotNull(returnedOwner);
        assertEquals(OWNER_NAME, returnedOwner.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findById() {
        Owner dummy = new Owner();
        dummy.setId(OWNER_ID);
        dummy.setLastName(OWNER_NAME);

        when(ownerRepository.findById(eq(OWNER_ID)))
                .thenReturn(Optional.of(dummy));

        Owner owner = ownerJPAService.findById(OWNER_ID);

        assertNotNull(owner);
        assertEquals(OWNER_ID, owner.getId());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Owner owner = ownerJPAService.findById(OWNER_ID + 42);

        assertNull(owner);
    }


    @Test
    void save() {
        Owner dummy = new Owner();
        dummy.setId(OWNER_ID);
        dummy.setLastName(OWNER_NAME);

        when(ownerRepository.save(any()))
                .thenReturn(dummy);

        Owner returned = ownerJPAService.save(dummy);

        assertNotNull(returned);
        verify(ownerRepository).save(dummy);
    }

    @Test
    void findAll() {
        Owner dummy1 = new Owner();
        dummy1.setId(OWNER_ID);
        dummy1.setLastName(OWNER_NAME);

        Owner dummy2 = new Owner();
        dummy2.setId(2L);
        dummy2.setLastName("Owner2");

        Set<Owner> ownerSet = new HashSet<>() {{
            add(dummy1);
            add(dummy2);
        }};

        when(ownerRepository.findAll()).thenReturn(ownerSet);

        Set<Owner> all = ownerJPAService.findAll();

        assertNotNull(all);
        assertEquals(ownerSet.size(), all.size());
        verify(ownerRepository).findAll();
    }

    @Test
    void delete() {
        Owner dummy = new Owner();
        dummy.setId(OWNER_ID);
        dummy.setLastName(OWNER_NAME);

        ownerJPAService.delete(dummy);

        verify(ownerRepository).delete(dummy);
    }

    @Test
    void deleteById() {
        Owner dummy = new Owner();
        dummy.setId(OWNER_ID);
        dummy.setLastName(OWNER_NAME);

        ownerJPAService.deleteById(dummy.getId());

        verify(ownerRepository).deleteById(dummy.getId());
    }
}