package com.example.school.dto;

public class PrimeLoginResponseDTO {
    private String token;

    public PrimeLoginResponseDTO(String token) {
        this.token=token;      
    }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
