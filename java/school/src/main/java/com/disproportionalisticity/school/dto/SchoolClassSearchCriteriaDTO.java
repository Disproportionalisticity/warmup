package com.disproportionalisticity.school.dto;

public record SchoolClassSearchCriteriaDTO (
    Long id,
    String name,
    String room,
    Integer minStudentCount,
    Integer maxStudentCount,
    Integer exactStudentCount,

    Integer page,
    Integer size,
    String sortBy,
    String sortDir
) {
    public SchoolClassSearchCriteriaDTO {
        if (page == null) page = 0;
        if (size == null) size = 10;
        if (sortBy == null) sortBy = "id";
        if (sortDir == null) sortDir = "asc";
    } 
}
