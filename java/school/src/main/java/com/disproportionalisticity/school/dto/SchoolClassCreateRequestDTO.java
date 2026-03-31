package com.disproportionalisticity.school.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SchoolClassCreateRequestDTO (
    @NotBlank(message = "Class name is required")
    @Size(min = 5, max = 100, message = "Class name must between 5 and 100 characters")
    String name,

    @NotBlank(message = "Class room is required")
    @Pattern(regexp = "^[A-F][1-4](0[1-9]|1[0-9]|20)$", message = "Room must be a letter (A-F), a floor (1-4) and room number (1-20). For example A101 or F420")
    String room
) {}
