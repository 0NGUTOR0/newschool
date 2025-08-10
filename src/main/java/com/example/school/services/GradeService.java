package com.example.school.services;

import org.springframework.stereotype.Service;

import com.example.school.models.Grade;
import com.example.school.repositories.GradeRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @PostConstruct
    public void initializeGrades() {
        if (gradeRepository.count() == 0) {
            List<Grade> grades = List.of(
                new Grade("JSS1", 5),
                new Grade("JSS2", 5),
                new Grade("JSS3", 5),
                new Grade("SS1", 5),
                new Grade("SS2", 5),
                new Grade("SS3", 5)
            );
            gradeRepository.saveAll(grades);
        }
    }

    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }
}