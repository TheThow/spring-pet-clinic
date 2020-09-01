package com.example.petclinic.controllers;

import com.example.petclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets", "/vets.html"})
    public String listVets(Model model) {

        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }
}
