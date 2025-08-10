package com.example.school.repositories.StudentRepositories;

import com.example.school.models.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.primeAccount.id = :primeAccountId " +
    "AND (:name IS NULL OR s.name LIKE %:name%) " +
    "AND (:grade IS NULL OR s.grade = :grade) " +
    "AND (:age IS NULL OR s.age = :age) " +
    "AND (:sex IS NULL OR s.sex = :sex) " +
    "AND (:religion IS NULL OR s.religion = :religion) " +
    "AND (:nationality IS NULL OR s.nationality = :nationality) " +
    "AND (:state IS NULL OR s.state = :state) " +
    "AND (:lga IS NULL OR s.lga = :lga) " +
    "AND (:school IS NULL OR s.school = :school)")
Page<Student> filterStudentsByPrimeAccount(
    @Param("primeAccountId") Long primeAccountId,
    @Param("name") String name,
    @Param("grade") String grade,
    @Param("age") Integer age,
    @Param("sex") String sex,
    @Param("religion") String religion,
    @Param("nationality") String nationality,
    @Param("state") String state,
    @Param("lga") String lga,  
    @Param("school") String school,
    Pageable pageable);

    
}
