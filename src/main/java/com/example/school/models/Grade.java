package com.example.school.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String name;

    private final int totalSubjects;

    @JsonManagedReference
    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Branch> branches;

    protected Grade() {
        this.name = null;
        this.totalSubjects = 0;
    }

    public Grade(String name, int totalSubjects) {
        this.name = name;
        this.totalSubjects = totalSubjects;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalSubjects() {
        return totalSubjects;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public String toString() {
        return "Grade{id=" + id + ", name='" + name + "', totalSubjects=" + totalSubjects + "}";
    }
}
