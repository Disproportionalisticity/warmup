package com.disproportionalisticity.school.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public record StudentSearchCriteriaDTO (
    Long id,
    String name,
    String email,
    BigDecimal minGrade,
    BigDecimal maxGrade,
    BigDecimal exactGrade,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bornBeforeDate,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bornAfterDate,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bornExactDate,
    String schoolClassName,
    Integer page,
    Integer size,
    String sortBy,
    String sortDir
) {
    public StudentSearchCriteriaDTO {
        if (page == null) page = 0;
        if (size == null) size = 10;
        if (sortBy == null) sortBy = "id";
        if (sortDir == null) sortDir = "asc";
    }
}
