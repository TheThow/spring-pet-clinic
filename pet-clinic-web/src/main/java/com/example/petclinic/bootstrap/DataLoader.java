package com.example.petclinic.bootstrap;

import com.example.petclinic.model.*;
import com.example.petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadDemoData();
        }

    }

    void loadDemoData() {
        PetType dogPetType = new PetType();
        dogPetType.setName("dog");
        petTypeService.save(dogPetType);

        PetType catPetType = new PetType();
        catPetType.setName("cat");
        petTypeService.save(catPetType);

        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");
        specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");
        specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");
        specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Foobar, Seattle");
        owner1.setTelephone("123456789");

        Pet michaelsPet = new Pet();
        michaelsPet.setPetType(dogPetType);
        michaelsPet.setOwner(owner1);
        michaelsPet.setBirthDate(LocalDate.of(2010, 8, 11));
        michaelsPet.setName("Rosco");
        owner1.getPets().add(michaelsPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("123 Brickerel, Miami");
        owner2.setTelephone("98765423");

        Pet fionasPet = new Pet();
        fionasPet.setPetType(catPetType);
        fionasPet.setOwner(owner2);
        fionasPet.setBirthDate(LocalDate.of(2012, 9, 7));
        fionasPet.setName("Kitty");
        owner2.getPets().add(fionasPet);

        ownerService.save(owner2);

        System.out.println("Loaded Owners....");

        Visit catVisit = new Visit();
        catVisit.setPet(fionasPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Cat Visit");

        visitService.save(catVisit);

        System.out.println("Loaded Visits....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(surgery);
        vet1.getSpecialities().add(radiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(radiology);
        vet2.getSpecialities().add(dentistry);

        vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }
}
