package com.disproportionalisticity.school.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.disproportionalisticity.school.entity.SchoolClass;
import com.disproportionalisticity.school.entity.SchoolClass_;
import com.disproportionalisticity.school.entity.Student;
import com.disproportionalisticity.school.entity.Student_;

import jakarta.persistence.criteria.Join;

public class StudentSpecifications {
    public static Specification<Student> hasId(Long id) {
        return (root, query, cb) -> id == null ? null : cb.equal(root.get(Student_.ID), id);
    }

    public static Specification<Student> hasName(String name) {
        return (root, query, cb) -> (name == null || name.isEmpty()) ? null : cb.like(cb.lower(root.get(Student_.NAME)), "%" + name + "%");
    }

    public static Specification<Student> hasEmail(String email) {
        return (root, query, cb) -> (email == null || email.isEmpty()) ? null : cb.like(cb.lower(root.get(Student_.EMAIL)), "%" + email + "%");
    }

    public static Specification<Student> hasMinGrade(BigDecimal minGrade) {
        return (root, query, cb) -> minGrade == null ? null : cb.greaterThanOrEqualTo(root.get(Student_.AVERAGE_GRADE), minGrade);
    }

    public static Specification<Student> hasMaxGrade(BigDecimal maxGrade) {
        return (root, query, cb) -> maxGrade == null ? null : cb.lessThanOrEqualTo(root.get(Student_.AVERAGE_GRADE), maxGrade);
    }

    public static Specification<Student> hasExactGrade(BigDecimal exactGrade) {
        return (root, query, cb) -> exactGrade == null ? null : cb.equal(root.get(Student_.AVERAGE_GRADE), exactGrade);
    }

    public static Specification<Student> bornBefore(LocalDate bornBeforeDate) {
        return (root, query, cb) -> bornBeforeDate == null ? null : cb.lessThan(root.get(Student_.BIRTH_DATE), bornBeforeDate);
    }

    public static Specification<Student> bornAfter(LocalDate bornAfterDate) {
        return (root, query, cb) -> bornAfterDate == null ? null : cb.greaterThan(root.get(Student_.BIRTH_DATE), bornAfterDate);
    }

    public static Specification<Student> bornExact(LocalDate exactDate) {
        return (root, query, cb) -> exactDate == null ? null : cb.equal(root.get(Student_.BIRTH_DATE), exactDate);
    }

    public static Specification<Student> belongsToClass(String schoolClassName) {
        return (root, query, cb) -> {
            if (schoolClassName == null || schoolClassName.isEmpty()) return null;
            
            // Use Student_.schoolClass instead of "schoolClass"
            Join<Student, SchoolClass> classJoin = root.join(Student_.schoolClass);
            
            // Use SchoolClass_.name instead of "name"
            return cb.equal(classJoin.get(SchoolClass_.name), schoolClassName);
        };
    }
}
