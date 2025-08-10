package com.example.school.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "prime_accounts")
@Data
public class PrimeAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name ="school", nullable = false)
    private String school;

    @Column(name = "password", nullable = false)
    private String password;

    

    @OneToMany(mappedBy = "primeAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StaffRegisterAccount> staffAccounts;

    public Long getId() {
        return id;
    }

    public String getRole() {
        return "PRIMEADMIN";
    }
}