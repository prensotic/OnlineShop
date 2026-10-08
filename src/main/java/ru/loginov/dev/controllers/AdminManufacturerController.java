package ru.loginov.dev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.loginov.dev.entities.Manufacturer;
import ru.loginov.dev.services.ManufacturerService;

@Controller
@RequestMapping("/dashboard")
public class AdminManufacturerController {
    private final ManufacturerService manufacturerService;

    public AdminManufacturerController(ManufacturerService manufacturerService){
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/manufacturers")
    public String manufacturerPage(Model model){
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        return "dashboard/manufacturers/manufacturers";
    }

    @GetMapping("/manufacturers/{id}")
    public String manufacturerDetailsPage(@PathVariable("id") long id, Model model){
        model.addAttribute("manufacturer", manufacturerService.findManufacturerById(id));
        return "dashboard/manufacturers/manufacturer_details";
    }

    @GetMapping("/manufacturers/new-manufacturer")
    public String createManufacturerPage(Model model){
        model.addAttribute("manufacturer", new Manufacturer());
        return "dashboard/manufacturers/new_manufacturer";
    }

    @PostMapping("/manufacturers/new-manufacturer")
    public String createManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer){
        manufacturerService.createManufacturer(manufacturer.getManufacturerName(),
                manufacturer.getManufacturerDescription(),
                manufacturer.getCountry());
        return "redirect:/dashboard/manufacturers";
    }

    @GetMapping("/manufacturers/edit-manufacturer/{id}")
    public String editManufacturerPage(@PathVariable("id") long id, Model model){
        model.addAttribute("manufacturer", manufacturerService.findManufacturerById(id));
        return "dashboard/manufacturers/edit_manufacturer";
    }

    @PatchMapping("/manufacturers/edit-manufacturer/{id}")
    public String editManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer,
                                   @PathVariable("id") long id){
        manufacturerService.updateManufacturer(id,
                manufacturer.getManufacturerName(),
                manufacturer.getManufacturerDescription(),
                manufacturer.getCountry());
        return "redirect:/dashboard/manufacturers";
    }

    @DeleteMapping("/manufacturers/{id}")
    public String deleteManufacturer(@PathVariable("id") long id){
        manufacturerService.deleteManufacturer(id);
        return "redirect:/dashboard/manufacturers";
    }
}