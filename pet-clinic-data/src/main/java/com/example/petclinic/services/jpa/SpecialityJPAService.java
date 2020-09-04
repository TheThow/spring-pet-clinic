package com.example.petclinic.services.jpa;

import com.example.petclinic.model.Speciality;
import com.example.petclinic.repositories.SpecialityRepository;
import com.example.petclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialityJPAService implements SpecialityService {

    private final SpecialityRepository specialtyRepository;

    public SpecialityJPAService(SpecialityRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> specialities = new HashSet<>();
        specialtyRepository.findAll().forEach(specialities::add);
        return specialities;
    }

    @Override
    public Speciality findById(Long aLong) {
        return specialtyRepository.findById(aLong).orElse(null);
    }

    @Override
    public Speciality save(Speciality object) {
        return specialtyRepository.save(object);
    }

    @Override
    public void delete(Speciality object) {
        specialtyRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        specialtyRepository.deleteById(aLong);
    }
}