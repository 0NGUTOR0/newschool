package com.example.school.repositories;

import com.example.school.models.ElectivePool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectivePoolRepository extends JpaRepository<ElectivePool, Long> {
}
