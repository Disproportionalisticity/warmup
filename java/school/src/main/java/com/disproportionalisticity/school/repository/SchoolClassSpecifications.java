package com.disproportionalisticity.school.repository;

import org.springframework.data.jpa.domain.Specification;

import com.disproportionalisticity.school.entity.SchoolClass;
import com.disproportionalisticity.school.entity.SchoolClass_;

public class SchoolClassSpecifications {
    public static Specification<SchoolClass> hasId(Long id) {
        return (root, query, cb) -> id == null ? null : cb.equal(root.get(SchoolClass_.ID), id);
    }

    public static Specification<SchoolClass> hasName(String name) {
        return (root, query, cb) -> (name == null || name.isEmpty()) ? null : cb.like(cb.lower(root.get(SchoolClass_.NAME)), ("%" + name + "%").toLowerCase());
    }

    public static Specification<SchoolClass> hasRoom(String room) {
        return (root, query, cb) -> (room == null || room.isEmpty()) ? null : cb.like(cb.lower(root.get(SchoolClass_.ROOM)), ("%" + room + "%").toLowerCase());
    }

    public static Specification<SchoolClass> hasMinStudentCount(Integer minStudentCount) {
        return (root, query, cb) -> minStudentCount == null ? null : cb.greaterThanOrEqualTo(root.get(SchoolClass_.STUDENT_COUNT), minStudentCount);
    }

    public static Specification<SchoolClass> hasMaxStudentCount(Integer maxStudentCount) {
        return (root, query, cb) -> maxStudentCount == null ? null : cb.lessThanOrEqualTo(root.get(SchoolClass_.STUDENT_COUNT), maxStudentCount);
    }

    public static Specification<SchoolClass> hasExactStudentCount(Integer exactStudentCount) {
        return (root, query, cb) -> exactStudentCount == null ? null : cb.equal(root.get(SchoolClass_.STUDENT_COUNT), exactStudentCount);
    }
}
