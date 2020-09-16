package com.example.petclinic.controllers;

import com.example.petclinic.model.Owner;
import com.example.petclinic.model.Pet;
import com.example.petclinic.model.PetType;
import com.example.petclinic.model.Visit;
import com.example.petclinic.services.OwnerService;
import com.example.petclinic.services.PetService;
import com.example.petclinic.services.PetTypeService;
import com.example.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private final PetService petService;
    private final VisitService visitService;

    public VisitController(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("visit")
    public void initVisitBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("pet")
    public Pet findPet(@PathVariable Long petId) {
        return petService.findById(petId);
    }

    @GetMapping("/visits/new")
    public String initCreateVisit(@ModelAttribute("pet") Pet pet, Model model) {
        Visit visit = new Visit();
        visit.setPet(pet);
        model.addAttribute("visit", visit);
        return "pets/createOrUpdateVisit";
    }

    @PostMapping("/visits/new")
    public String createVisit(@ModelAttribute("pet") Pet pet, @ModelAttribute("visit") @Validated Visit visit, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("visit", visit);
            return "pets/createOrUpdateVisit";
        }

        visit.setPet(pet);
        pet.getVisits().add(visit);
        petService.save(pet);

        return "redirect:/owners/{ownerId}";
    }

}
