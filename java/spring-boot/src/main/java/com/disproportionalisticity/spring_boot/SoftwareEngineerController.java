package com.disproportionalisticity.spring_boot;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;
    
    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping
    public List<SoftwareEngineer> getEngineers() {
        return this.softwareEngineerService.getAllSoftwareEngineers();
    }

    @GetMapping("{id}")
    public SoftwareEngineer getEngineerById(@PathVariable Integer id) {
        return this.softwareEngineerService.getAllSoftwareEngineerById(id);
    }

    @PostMapping
    public void addSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer) {
        this.softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }

}
