package com.disproportionalisticity.school.dto;

import lombok.Builder;

@Builder
public record SchoolClassUpdateRequestDTO (
    String name,
    String room
) {}
