package com.example.school.dto;

import com.example.school.models.PrimeAccount;
import com.example.school.models.StaffRegisterAccount;

public class StaffResponseDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private Boolean isVerified;
    private PrimeAccount primeAccount;
    private String school;




    // ✅ Constructor that accepts StaffRegisterAccount
    public StaffResponseDTO(StaffRegisterAccount staff) {
        this.id = staff.getId();
        this.firstname = staff.getFirstname();
        this.lastname = staff.getLastname();
        this.email = staff.getEmail();
        this.role = staff.getRole();
        this.isVerified = staff.getIsVerified();
        this.primeAccount = staff.getPrimeAccount();
        this.primeAccount = staff.getSchool();
    }

  

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    } public PrimeAccount getPrimeAccount() { return primeAccount; }
    public void setPrimeAccount(PrimeAccount primeAccount) { this.primeAccount = primeAccount; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
}
