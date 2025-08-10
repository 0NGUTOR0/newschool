package com.example.school.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.school.dto.StaffLoginRequest;
import com.example.school.dto.StaffLoginResponse;
import com.example.school.models.StaffSigninAccount;
import com.example.school.repositories.StaffLoginRepository;
import com.example.school.security.*;
import com.example.school.services.StaffSignupService;;
@RestController
public class AuthController {

    
    @Autowired
    private StaffTokenUtil tokenUtil;
    @Autowired
    private StaffLoginRepository staffLoginRepository;
    @Autowired
    private StaffSignupService staffSignupService;

    
    @PostMapping("/school/staff/login")
    public ResponseEntity<StaffLoginResponse> login(@RequestBody StaffLoginRequest loginRequest) {
    String token = staffSignupService.login(loginRequest.getEmail(), loginRequest.getPassword());
    return ResponseEntity.ok(new StaffLoginResponse(token));
}

    @PostMapping("/school/complete-signup")
    public ResponseEntity<?> completeSignup(@RequestParam String token, @RequestParam String password) {
        String email;
        try {
            email = tokenUtil.validateAndExtractEmail(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        Optional<StaffSigninAccount> staffOpt = staffLoginRepository.findByEmail(email);
        if (staffOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Staff account not found");
        }

        StaffSigninAccount staff = staffOpt.get();
        staffLoginRepository.save(staff);

        return ResponseEntity.ok("Password set successfully. You can now log in.");
    }
    
}

class LoginRequest {
    private String username;
    private String password;

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
