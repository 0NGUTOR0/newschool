package com.example.school.dto;


public class StaffDTO {
    private String firstname;
    private String lastname;
    private String email;
    private Boolean isVerified;
    private String role;

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getIsVerified() { return isVerified; }
    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

   
}