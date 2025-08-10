package com.example.school.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.school.models.PrimeAccount;

public interface PrimeRepository extends JpaRepository<PrimeAccount, Long>{
    Optional<PrimeAccount> findByEmail(String email);
    Optional<PrimeAccount> findByUsername(String username);
    Optional<PrimeAccount> findBySchool(PrimeAccount school);
    
}
   
