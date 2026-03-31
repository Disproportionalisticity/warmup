package com.disproportionalisticity.school.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Formula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "school_classes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString @EqualsAndHashCode
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Class name is required")
    @Size(min = 5, max = 100, message = "Class name must between 5 and 100 characters")
    private String name;

    @NotBlank(message = "Class room is required")
    @Pattern(regexp = "^[A-F][1-4](0[1-9]|1[0-9]|20)$", message = "Room must be a letter (A-F), a floor (1-4) and room number (1-20). For example A101 or F420")
    private String room;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @Formula("(SELECT COUNT(*) FROM students s WHERE s.class_id = id)")
    private int studentCount;
}
