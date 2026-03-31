package com.disproportionalisticity.school.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record StudentCreateRequestDTO (
    @NotBlank(message = "Student name is required")
    @Size(min = 2, max = 100, message = "Name must between 2 and 100 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    LocalDate birthDate,

    @NotNull(message = "Average grade is required")
    @DecimalMin(value = "0.01", message = "Value must be at least 0.01")
    @DecimalMax(value = "10.00", message = "Value cannot exceed 10.00")
    @Digits(integer = 2, fraction = 2, message = "Format must be 2 digits before and 2 digits after the decimal")
    BigDecimal averageGrade,

    @NotNull(message = "Class ID is required")
    Long schoolClassId
) {}
