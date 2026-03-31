package com.disproportionalisticity.school.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;

@Builder
public record ApiErrorDTO (
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path,
    Map<String, String> validationErrors
) {}
