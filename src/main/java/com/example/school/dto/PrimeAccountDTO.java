package com.example.school.dto;

import com.example.school.models.PrimeAccount;

public class PrimeAccountDTO {
    private String email;
    private String username;
    private PrimeAccount school;
    private String password;
    private Boolean hasPayed;
    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public PrimeAccount getSchool() { return school; }
    public void setSchool(PrimeAccount school) { this.school = school; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getHasPayed() { return hasPayed; }
    public void setHasPayed(Boolean hasPayed) { this.hasPayed = hasPayed; }


}
