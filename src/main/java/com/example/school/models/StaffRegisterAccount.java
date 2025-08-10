package com.example.school.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff_accounts")
public class StaffRegisterAccount {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private Boolean isVerified = false;

   @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "prime_account_id")
private PrimeAccount primeAccount;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private PrimeAccount school;

    // Getters and Setters
    public Long getId() {
        return id;
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
    }

    

    public PrimeAccount getPrimeAccount() {
        return primeAccount;
    }

    public void setPrimeAccount(PrimeAccount primeAccount) {
        this.primeAccount = primeAccount;
    }

    public PrimeAccount getSchool() {
        return school;
    }

    public void setSchool(PrimeAccount school) {
        this.school = school;
}

    public void setPassword(String password) {
        this.password = password  ; }

    public String getPassword() {
       return password;}
}