package com.disproportionalisticity.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disproportionalisticity.school.dto.SchoolClassCreateRequestDTO;
import com.disproportionalisticity.school.dto.SchoolClassResponseDTO;
import com.disproportionalisticity.school.dto.SchoolClassSearchCriteriaDTO;
import com.disproportionalisticity.school.dto.SchoolClassUpdateRequestDTO;
import com.disproportionalisticity.school.entity.SchoolClass;
import com.disproportionalisticity.school.exception.DataConflictException;
import com.disproportionalisticity.school.exception.ResourceNotFoundException;
import com.disproportionalisticity.school.repository.SchoolClassRepository;
import com.disproportionalisticity.school.repository.SchoolClassSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;

    @Transactional
    public SchoolClassResponseDTO createSchoolClass(SchoolClassCreateRequestDTO request) {
        SchoolClass schoolClass = SchoolClass.builder()
            .name(request.name())
            .room(request.room())
            .build();

        SchoolClass saved = schoolClassRepository.save(schoolClass);
        return mapToResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public SchoolClassResponseDTO getSchoolClassById(Long id) {
        return schoolClassRepository.findById(id).map(this::mapToResponse).orElseThrow(() -> new ResourceNotFoundException("School Class with ID " + id + " not found!"));
    }

    @Transactional(readOnly = true)
    public Page<SchoolClassResponseDTO> searchSchoolClasses(SchoolClassSearchCriteriaDTO criteria) {
        validateSearchRanges(criteria);

        Sort sort = criteria.sortDir().equalsIgnoreCase("asc") 
            ? Sort.by(criteria.sortBy()).ascending() 
            : Sort.by(criteria.sortBy()).descending();

        PageRequest pageable = PageRequest.of(criteria.page(), Math.min(criteria.size(), 100), sort);

        Specification<SchoolClass> spec = Specification.allOf(
            SchoolClassSpecifications.hasId(criteria.id()),
            SchoolClassSpecifications.hasName(criteria.name()),
            SchoolClassSpecifications.hasRoom(criteria.room()),
            SchoolClassSpecifications.hasMinStudentCount(criteria.minStudentCount()),
            SchoolClassSpecifications.hasMaxStudentCount(criteria.maxStudentCount()),
            SchoolClassSpecifications.hasExactStudentCount(criteria.exactStudentCount())
        );

        Page<SchoolClass> schoolClassPage = schoolClassRepository.findAll(spec, pageable);

        return schoolClassPage.map(this::mapToResponse);
    }

    @Transactional
    public SchoolClassResponseDTO updateSchoolClass(Long id, SchoolClassCreateRequestDTO request) {
        SchoolClass schoolClass = schoolClassRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("School class with ID " + id + " not found!"));

        schoolClass.setName(request.name());
        schoolClass.setRoom(request.room());

        return mapToResponse(schoolClassRepository.save(schoolClass));
    }

    @Transactional
    public SchoolClassResponseDTO patchSchoolClass(Long id, SchoolClassUpdateRequestDTO request) {
        SchoolClass schoolClass = schoolClassRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("School class with ID " + id + " not found!"));

        if (request.name() != null) schoolClass.setName(request.name());
        if (request.room() != null) schoolClass.setRoom(request.room());

        return mapToResponse(schoolClassRepository.save(schoolClass));
    }

    @Transactional
    public void deleteSchoolClass(@NonNull Long id, boolean confirmCascade) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("School Class with ID " + id + " not found!"));

        if (!schoolClass.getStudents().isEmpty() && !confirmCascade) {
            // This triggers the @ExceptionHandler in the Global class
            throw new DataConflictException("This class contains " + schoolClass.getStudents().size() 
                + " students. Deleting it will remove them all. Please confirm.");
        }

        schoolClassRepository.deleteById(id);
    }

    private SchoolClassResponseDTO mapToResponse(SchoolClass schoolClass) {
        return SchoolClassResponseDTO.builder()
            .id(schoolClass.getId())
            .name(schoolClass.getName())
            .room(schoolClass.getRoom())
            .studentCount(schoolClass.getStudentCount())
            .build();
    }

    private void validateSearchRanges(SchoolClassSearchCriteriaDTO c) {
        if (c.minStudentCount() != null && c.maxStudentCount() != null && c.minStudentCount().compareTo(c.maxStudentCount()) > 0) {
            throw new DataConflictException("Minimum student count cannot be greater than maximum student count!");
        }
    }
}
