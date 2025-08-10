package com.example.school.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    @JsonBackReference  // Prevents infinite recursion
    private Grade grade;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();


    // Utility methods if needed
    public List<Subject> getMandatorySubjects() {
        return subjects.stream()
                .filter(subject -> !subject.isElective())
                .toList();
    }

    public List<Subject> getElectiveSubjects() {
        return subjects.stream()
                .filter(Subject::isElective)
                .toList();
    }
}
