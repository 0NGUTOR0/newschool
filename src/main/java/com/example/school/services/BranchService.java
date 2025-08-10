package com.example.school.services;

import com.example.school.dto.SubjectDTO;
import com.example.school.models.Branch;
import com.example.school.models.Grade;
import com.example.school.models.Subject;
import com.example.school.repositories.BranchRepository;
import com.example.school.repositories.GradeRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
    private final BranchRepository branchRepository;
    private final GradeRepository gradeRepository;
   

    public BranchService(BranchRepository branchRepository, GradeRepository gradeRepository) {
        this.branchRepository = branchRepository;
        this.gradeRepository = gradeRepository;
        
    }

    public Branch addBranch(String gradeName) {
        Grade grade = gradeRepository.findByName(gradeName)
            .orElseThrow(() -> new RuntimeException("Grade not found: " + gradeName));
        List<Branch> existingBranches = branchRepository.findByGradeId(grade.getId());
        char nextBranchLetter = (char) ('A' + existingBranches.size());
        Branch branch = new Branch();
        branch.setGrade(grade); // Use setGrade instead of setGradeId
        branch.setName(String.valueOf(nextBranchLetter));
        return branchRepository.save(branch);
    }
    public List<Branch> getBranchesByGrade(String gradeName) {
        Grade grade = gradeRepository.findByName(gradeName)
            .orElseThrow(() -> new RuntimeException("Grade not found: " + gradeName));
        return branchRepository.findByGradeId(grade.getId());
    }
    
    public void assignSubjects(Long branchId, List<SubjectDTO> subjectDTOs) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    
        List<Subject> subjects = subjectDTOs.stream().map(dto -> {
            Subject subject = new Subject();
            subject.setName(dto.getName());
            subject.setElective(dto.isElective());
            subject.setAvailable(true);
            subject.setBranch(branch);
            return subject;
        }).toList();
    
        branch.getSubjects().addAll(subjects);
        branchRepository.save(branch); // cascade will save subjects too
    }
}