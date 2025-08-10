package com.example.school.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean elective; // true for elective, false for mandatory

    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
