package com.example.school.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.school.models.Grade;
import com.example.school.services.GradeService;

import java.util.List;

@RestController
@RequestMapping("/school")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PRIMEADMIN')")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<Grade>> getGrades() {
        return ResponseEntity.ok(gradeService.getGrades());
    }
}