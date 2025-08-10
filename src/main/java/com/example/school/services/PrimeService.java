package com.example.school.services;

import com.example.school.dto.PrimeAccountDTO;
import com.example.school.models.PrimeAccount;
import com.example.school.repositories.PrimeRepository;
import com.example.school.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrimeService {

    private final PrimeRepository primeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public PrimeService(PrimeRepository primeRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.primeRepository = primeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    public Optional<PrimeAccount> findByEmail(String email) {
        return primeRepository.findByEmail(email);
    }

    public Optional<PrimeAccount> findByUsername(String username) {
        return primeRepository.findByUsername(username);
    }
    
    public Optional<PrimeAccount> findById(Long id) {
        return primeRepository.findById(id);
    }

    public String login(String email, String password) {
    PrimeAccount prime = primeRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

    if (!passwordEncoder.matches(password, prime.getPassword())) {
        throw new BadCredentialsException("Invalid email or password");
    }


    // Generate token with primeAccountId included
    return jwtUtil.generateToken(prime.getEmail(), prime.getRole(), prime.getId());
}
    

    public String register(PrimeAccountDTO dto) {
        
        

        Optional<PrimeAccount> existing = primeRepository.findByEmail(dto.getEmail());
        if (existing.isPresent()) {
            throw new DataIntegrityViolationException("An account with email " + dto.getEmail() + " already exists");
        }
        PrimeAccount prime = new PrimeAccount();
        prime.setEmail(dto.getEmail());
        prime.setUsername(dto.getUsername());
        prime.setPassword(passwordEncoder.encode(dto.getPassword()));
        primeRepository.save(prime);

        return jwtUtil.generateToken(dto.getUsername(), "PRIMEADMIN", primeRepository.save(prime).getId());
    }

    public Optional<PrimeAccount> findBySchool(PrimeAccount school) {
       return primeRepository.findBySchool(school) ; }
}
