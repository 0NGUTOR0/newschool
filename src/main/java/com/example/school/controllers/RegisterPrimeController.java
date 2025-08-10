package com.example.school.controllers;

import com.example.school.dto.PrimeAccountDTO;
import com.example.school.dto.PrimeLoginResponseDTO;
import com.example.school.dto.PrimeRequestDTO;

import com.example.school.services.PrimeService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterPrimeController {

    @Autowired
    private PrimeService primeService;

    @PostMapping("/school/registerprime")
    public ResponseEntity<?> registerPrime(@RequestBody PrimeAccountDTO dto) {
    try {
        String jwt = primeService.register(dto);
        return ResponseEntity.ok(jwt);
    } catch (Exception ex) {
        ex.printStackTrace();  // 👈 Shows exact error in logs
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
    @PostMapping("/school/prime/login")
    public ResponseEntity<?> login(@RequestBody PrimeRequestDTO primeloginRequestDTO) {
        try {
            String token = primeService.login(primeloginRequestDTO.getEmail(), primeloginRequestDTO.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new PrimeLoginResponseDTO("Invalid email or password"));
        }
    }

}