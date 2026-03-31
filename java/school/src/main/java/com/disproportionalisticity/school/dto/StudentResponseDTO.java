package com.disproportionalisticity.school.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record StudentResponseDTO(
    Long id,
    String name,
    String email,
    LocalDate birthDate,
    BigDecimal averageGrade,
    String schoolClassName
) {}
