package com.disproportionalisticity.spring_boot;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SoftwareEngineerService {
    
    private final SoftwareEngineerRepository softwareEngineerRepository;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return this.softwareEngineerRepository.findAll();
    }

    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        this.softwareEngineerRepository.save(softwareEngineer);
    }

    public SoftwareEngineer getAllSoftwareEngineerById(Integer id) {
        return this.softwareEngineerRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + " not found."));
    }

}
