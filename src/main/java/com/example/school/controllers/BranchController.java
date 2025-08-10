package com.example.school.controllers;

import com.example.school.dto.SubjectDTO;
import com.example.school.models.Branch;
import com.example.school.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school/grades")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/{gradeName}")
    @PreAuthorize("hasAuthority('ROLE_PRIMEADMIN')")
    public ResponseEntity<Branch> addBranch(@PathVariable String gradeName) {
        return ResponseEntity.ok(branchService.addBranch(gradeName));
    }

    @GetMapping("/{gradeName}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PRIMEADMIN')")
    public ResponseEntity<List<Branch>> getBranchesByGrade(@PathVariable String gradeName) {
        return ResponseEntity.ok(branchService.getBranchesByGrade(gradeName));
    }

    @PostMapping("/{branchId}/subjects")
    @PreAuthorize("hasAuthority('ROLE_PRIMEADMIN')")
    public ResponseEntity<String> assignSubjects(@PathVariable Long branchId, @RequestBody List<SubjectDTO> subjectDTOs) {
        branchService.assignSubjects(branchId, subjectDTOs);
        return ResponseEntity.ok("Subjects assigned");
    }
}
