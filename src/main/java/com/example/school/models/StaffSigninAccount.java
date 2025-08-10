package com.example.school.models;

import jakarta.persistence.*;

@Entity
@Table(name = "staff_logins")
public class StaffSigninAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String password;
    private String confirmPassword;
    private String token;
    @ManyToOne
    private PrimeAccount primeAccount;

    @ManyToOne
    private PrimeAccount school;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstname;
    }
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }
    public void setLastName(String lastname) {
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
   

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public PrimeAccount getPrimeAccount() {
        return primeAccount;
    }

    public void setPrimeAccount(PrimeAccount primeAccount) {
        this.primeAccount = primeAccount;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
