package com.disproportionalisticity.school.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disproportionalisticity.school.dto.StudentCreateRequestDTO;
import com.disproportionalisticity.school.dto.StudentResponseDTO;
import com.disproportionalisticity.school.dto.StudentSearchCriteriaDTO;
import com.disproportionalisticity.school.dto.StudentUpdateRequestDTO;
import com.disproportionalisticity.school.entity.SchoolClass;
import com.disproportionalisticity.school.entity.SchoolClass_;
import com.disproportionalisticity.school.entity.Student;
import com.disproportionalisticity.school.entity.Student_;
import com.disproportionalisticity.school.exception.DataConflictException;
import com.disproportionalisticity.school.exception.ResourceNotFoundException;
import com.disproportionalisticity.school.repository.SchoolClassRepository;
import com.disproportionalisticity.school.repository.StudentRepository;
import com.disproportionalisticity.school.repository.StudentSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;

    private static final Map<String, String> STUDENT_SORT_MAP = Map.of(
        "id", Student_.ID,
        "name", Student_.NAME,
        "email", Student_.EMAIL,
        "birthDate", Student_.BIRTH_DATE,
        "averageGrade", Student_.AVERAGE_GRADE,
        "schoolClassName", Student_.SCHOOL_CLASS + "." + SchoolClass_.NAME
    );

    @Transactional
    public StudentResponseDTO createStudent(StudentCreateRequestDTO request) {
        Long classId = request.schoolClassId();

        SchoolClass schoolClass = schoolClassRepository.findById(classId)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot create Student: School Class with ID " + classId + " not found!"));

        Student student = Student.builder()
            .name(request.name())
            .email(request.email())
            .birthDate(request.birthDate())
            .averageGrade(request.averageGrade())
            .schoolClass(schoolClass)
            .build();

        Student saved = studentRepository.save(student);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        return studentRepository.findById(id).map(this::mapToResponse).orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " not found!"));
    }

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> searchStudents(StudentSearchCriteriaDTO criteria) {
        validateSearchRanges(criteria);

        String mappedSortBy = STUDENT_SORT_MAP.getOrDefault(criteria.sortBy(), Student_.ID);

        Sort sort = criteria.sortDir().equalsIgnoreCase("asc") 
            ? Sort.by(mappedSortBy).ascending() 
            : Sort.by(mappedSortBy).descending();

        PageRequest pageable = PageRequest.of(criteria.page(), Math.min(criteria.size(), 100), sort);

        Specification<Student> spec = Specification.allOf(
                StudentSpecifications.hasId(criteria.id()),
                StudentSpecifications.hasName(criteria.name()),
                StudentSpecifications.hasEmail(criteria.email()),
                StudentSpecifications.hasMinGrade(criteria.minGrade()),
                StudentSpecifications.hasMaxGrade(criteria.maxGrade()),
                StudentSpecifications.hasExactGrade(criteria.exactGrade()),
                StudentSpecifications.bornBefore(criteria.bornBeforeDate()),
                StudentSpecifications.bornAfter(criteria.bornAfterDate()),
                StudentSpecifications.bornExact(criteria.bornExactDate()),
                StudentSpecifications.belongsToClass(criteria.schoolClassName())
        );

        Page<Student> studentPage = studentRepository.findAll(spec, pageable);

        return studentPage.map(this::mapToResponse);
    }

    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentCreateRequestDTO request) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " not found!"));

        if (!student.getSchoolClass().getId().equals(request.schoolClassId())) {
            SchoolClass newClass = schoolClassRepository.findById(request.schoolClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class with id " + request.schoolClassId() + " not found!"));
            student.setSchoolClass(newClass);
        }

        student.setName(request.name());
        student.setEmail(request.email());
        student.setBirthDate(request.birthDate());
        student.setAverageGrade(request.averageGrade());

        return mapToResponse(studentRepository.save(student));
    }

    @Transactional
    public StudentResponseDTO patchStudent(Long id, StudentUpdateRequestDTO request) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " not found!"));

        if (request.name() != null) student.setName(request.name());
        if (request.email() != null) student.setEmail(request.email());
        if (request.birthDate() != null) student.setBirthDate(request.birthDate());
        if (request.averageGrade() != null) student.setAverageGrade(request.averageGrade());
        
        if (request.schoolClassId() != null) {
            SchoolClass newClass = schoolClassRepository.findById(request.schoolClassId()).orElseThrow(() -> new ResourceNotFoundException("Class with id " + request.schoolClassId() + " not found!"));
            student.setSchoolClass(newClass);
        }

        return mapToResponse(studentRepository.save(student));
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student with ID " + id + " not found!");
        }
        studentRepository.deleteById(id);
    }

    private StudentResponseDTO mapToResponse(Student student) {
        return StudentResponseDTO.builder()
            .id(student.getId())
            .name(student.getName())
            .email(student.getEmail())
            .birthDate(student.getBirthDate())
            .averageGrade(student.getAverageGrade())
            .schoolClassName(student.getSchoolClass() != null ? student.getSchoolClass().getName() : "Unassigned")
            .build();
    }

    private void validateSearchRanges(StudentSearchCriteriaDTO c) {
        if (c.minGrade() != null && c.maxGrade() != null && c.minGrade().compareTo(c.maxGrade()) > 0) {
            throw new DataConflictException("Minimum grade cannot be greater than maximum grade!");
        }
        if (c.bornAfterDate() != null && c.bornBeforeDate() != null && c.bornAfterDate().isAfter(c.bornBeforeDate())) {
            throw new DataConflictException("Born After date cannot be after Born Before date!");
        }
    }
}
