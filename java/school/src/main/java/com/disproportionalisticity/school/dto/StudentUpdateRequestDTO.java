package com.disproportionalisticity.school.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record StudentUpdateRequestDTO (
    String name,
    String email,
    LocalDate birthDate,
    BigDecimal averageGrade,
    Long schoolClassId
) {}