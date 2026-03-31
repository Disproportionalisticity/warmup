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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.disproportionalisticity.school.dto.StudentCreateRequestDTO;
import com.disproportionalisticity.school.dto.StudentResponseDTO;
import com.disproportionalisticity.school.dto.StudentSearchCriteriaDTO;
import com.disproportionalisticity.school.dto.StudentUpdateRequestDTO;
import com.disproportionalisticity.school.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDTO create(@Valid @RequestBody StudentCreateRequestDTO request) {
        return studentService.createStudent(request);
    }

    @GetMapping("/{id}")
    public StudentResponseDTO getById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/search")
    public Page<StudentResponseDTO> search(@ParameterObject StudentSearchCriteriaDTO criteria) {
        // Spring automatically populates 'criteria' from the URL query string
        return studentService.searchStudents(criteria);
    }  

    @PutMapping("/{id}")
    public StudentResponseDTO update(@PathVariable Long id, @Valid @RequestBody StudentCreateRequestDTO request) {
        return studentService.updateStudent(id, request);
    }

    @PatchMapping("/{id}")
    public StudentResponseDTO patch(@PathVariable Long id, @RequestBody StudentUpdateRequestDTO request) {
        return studentService.patchStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
