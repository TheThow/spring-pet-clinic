package com.example.petclinic.services.map;

import com.example.petclinic.model.Vet;
import com.example.petclinic.services.SpecialityService;
import com.example.petclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet vet) {

        if (vet != null) {
            vet.getSpecialities().forEach(speciality -> {
                specialityService.save(speciality);
            });
        }

        return super.save(vet);
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public Vet findByLastName(String lastName) {
        return super.findAll()
                .stream()
                .filter(Vet -> Vet.getLastName().equals(lastName))
                .findFirst()
                .get();
    }
}
