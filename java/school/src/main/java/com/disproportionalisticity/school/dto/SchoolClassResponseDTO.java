package com.disproportionalisticity.school.dto;

import lombok.Builder;

@Builder
public record SchoolClassResponseDTO (
    Long id,
    String name,
    String room,
    int studentCount
) {}
