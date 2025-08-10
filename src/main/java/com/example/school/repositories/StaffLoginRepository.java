package com.example.school.repositories;

import com.example.school.models.StaffSigninAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffLoginRepository extends JpaRepository<StaffSigninAccount, String> {
    Optional<StaffSigninAccount> findByEmail(String email);


    
}