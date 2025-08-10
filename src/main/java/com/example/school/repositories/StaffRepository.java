package com.example.school.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.school.models.PrimeAccount;
import com.example.school.models.StaffRegisterAccount;
    @Repository
public interface StaffRepository extends JpaRepository<StaffRegisterAccount, String> {
    Optional<StaffRegisterAccount> findByEmail(String email);
    List<StaffRegisterAccount> findByPrimeAccount(PrimeAccount primeAccount);
    @Query("""
        SELECT s FROM StaffRegisterAccount s
        WHERE s.primeAccount.id = :primeAccountId
          AND (:firstname IS NULL OR LOWER(s.firstname) LIKE LOWER(CONCAT('%', :firstname, '%')))
          AND (:lastname IS NULL OR LOWER(s.lastname) LIKE LOWER(CONCAT('%', :lastname, '%')))
          AND (:role IS NULL OR s.role = :role)
          AND (:isVerified IS NULL OR s.isVerified = :isVerified)
          AND (:school IS NULL OR s.school.id = :school)
    """)
    Page<StaffRegisterAccount> filterStaffByPrimeAccount(
        @Param("primeAccountId") Long primeAccountId,
        @Param("firstname") String firstname,
        @Param("lastname") String lastname,
        @Param("role") String role,
        @Param("school") String school,
        @Param("isVerified") Boolean isVerified,
        Pageable pageable
    );
    


}
