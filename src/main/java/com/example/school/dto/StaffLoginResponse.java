package com.example.school.dto;

public class StaffLoginResponse {
    private String token;

    public StaffLoginResponse(String token) {
        this.token=token;      
    }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
