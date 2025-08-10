package com.example.school.repositories;

import com.example.school.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByGradeId(Long gradeId); // Queries based on grade_id foreign key
}