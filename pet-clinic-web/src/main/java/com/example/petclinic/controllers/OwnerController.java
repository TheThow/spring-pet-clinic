package com.example.petclinic.controllers;

import com.example.petclinic.model.Owner;
import com.example.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping({"/", ""})
    public String processFindForm(@ModelAttribute("owner") Owner owner, BindingResult result, Model model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());

        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "notfound");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreateOwner(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/createOrUpdateOwner";
    }

    @PostMapping("/new")
    public String createOwner(@ModelAttribute("owner") @Validated Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return "/owners/createOrUpdateOwner";
        }
        else {
            Owner saved = ownerService.save(owner);
            return "redirect:/owners/" + saved.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwner(@PathVariable Long ownerId, Model model) {
        Owner owner = ownerService.findById(ownerId);
        model.addAttribute("owner", owner);
        return "owners/createOrUpdateOwner";
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@ModelAttribute("owner") @Validated Owner owner, BindingResult result, @PathVariable Long ownerId) {
        if (result.hasErrors()) {
            return "/owners/createOrUpdateOwner";
        }
        else {
            owner.setId(ownerId);
            Owner saved = ownerService.save(owner);
            return "redirect:/owners/" + saved.getId();
        }
    }


}
