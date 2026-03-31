package com.disproportionalisticity.school.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.disproportionalisticity.school.dto.SchoolClassCreateRequestDTO;
import com.disproportionalisticity.school.dto.SchoolClassResponseDTO;
import com.disproportionalisticity.school.dto.SchoolClassSearchCriteriaDTO;
import com.disproportionalisticity.school.dto.SchoolClassUpdateRequestDTO;
import com.disproportionalisticity.school.service.SchoolClassService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/school-classes")
@RequiredArgsConstructor
public class SchoolClassController {
    private final SchoolClassService schoolClassService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolClassResponseDTO create(@Valid @RequestBody SchoolClassCreateRequestDTO request) {
        return schoolClassService.createSchoolClass(request);
    }

    @GetMapping("/{id}")
    public SchoolClassResponseDTO getById(@PathVariable Long id) {
        return schoolClassService.getSchoolClassById(id);
    }

    @GetMapping("/search")
    public Page<SchoolClassResponseDTO> search(@ParameterObject SchoolClassSearchCriteriaDTO criteria) {
        // Spring automatically populates 'criteria' from the URL query string
        return schoolClassService.searchSchoolClasses(criteria);
    }  

    @PutMapping("/{id}")
    public SchoolClassResponseDTO update(@PathVariable Long id, @Valid @RequestBody SchoolClassCreateRequestDTO request) {
        return schoolClassService.updateSchoolClass(id, request);
    }

    @PatchMapping("/{id}")
    public SchoolClassResponseDTO patch(@PathVariable Long id, @RequestBody SchoolClassUpdateRequestDTO request) {
        return schoolClassService.patchSchoolClass(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean confirmCascade) {
        schoolClassService.deleteSchoolClass(id, confirmCascade);
    }
}
