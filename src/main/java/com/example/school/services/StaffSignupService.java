package com.example.school.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.school.models.PrimeAccount;
import com.example.school.models.StaffRegisterAccount;
import com.example.school.models.StaffSigninAccount;
import com.example.school.repositories.PrimeRepository;
import com.example.school.repositories.StaffRepository;
import com.example.school.security.TokenService;
import com.example.school.security.JwtUtil;


@Service
public class StaffSignupService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PrimeRepository primeRepository;

    @Autowired
    private TokenService tokenService;
    
    public StaffRegisterAccount addStaffAccount(StaffRegisterAccount staff) {
       
        
        staff.setIsVerified(false); 
        return staffRepository.save(staff);
    }
    
    public StaffSigninAccount getSignupInfo(String token) {
        if (!tokenService.isTokenValid(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        String email = tokenService.extractEmailFromToken(token);
        StaffRegisterAccount staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No staff account found for email: " + email));

        StaffSigninAccount response = new StaffSigninAccount();
        response.setToken(token);
        response.setFirstName(staff.getFirstname());
        response.setLastName(staff.getLastname());
        response.setEmail(staff.getEmail());

        return response;
    }

    public boolean emailExists(String email) {
        return staffRepository.findByEmail(email.toLowerCase().trim()).isPresent();
    }
    
    
    public String login(String email, String password) {
    StaffRegisterAccount staff = staffRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

    if (!passwordEncoder.matches(password, staff.getPassword())) {
        throw new BadCredentialsException("Invalid email or password");
    }

    

    // Generate token with primeAccountId included
    return jwtUtil.generateToken(staff.getEmail(), staff.getRole(), staff.getPrimeAccount().getId());
}


    public void completeSignup(String token, String password, String confirmPassword) {
        if (!tokenService.isTokenValid(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        String email = tokenService.extractEmailFromToken(token);
        StaffRegisterAccount staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Staff account not found"));

        staff.setPassword(passwordEncoder.encode(password));
        staff.setIsVerified(true); // optional: mark as verified
        staffRepository.save(staff);
    }

    public Page<StaffRegisterAccount> getAllStaff(int page, int size) {
    return staffRepository.findAll(PageRequest.of(page, size));
}


    public Optional<StaffRegisterAccount> getStaffById(String id) {
        return staffRepository.findById(id);}

	public void deleteStaff(String id) {
		staffRepository.deleteById(id);}

        public StaffRegisterAccount updateStaffAccount(
            String staffId,
            String firstName,
            String lastName,
            String email,
            String role,
            String password,
            String primeAccountId
    ) {
        StaffRegisterAccount staff = staffRepository.findById(String.valueOf(staffId))
            .orElseThrow(() -> new IllegalArgumentException("Staff with ID " + staffId + " not found"));
    
        if (firstName != null && !firstName.isBlank()) staff.setFirstname(firstName);
        if (lastName != null && !lastName.isBlank()) staff.setLastname(lastName);
    
        if (email != null && !email.isBlank()) {
            Optional<StaffRegisterAccount> existingWithEmail = staffRepository.findByEmail(email);
            if (existingWithEmail.isPresent() && !existingWithEmail.get().getId().equals(staff.getId())) {
                throw new IllegalArgumentException("Another staff already uses email: " + email);
            }
            staff.setEmail(email);
        }
    
        if (role != null && !role.isBlank()) staff.setRole(role);
        if (password != null && !password.isBlank()) staff.setPassword(passwordEncoder.encode(password));
    
        if (primeAccountId != null) {
            PrimeAccount prime = primeRepository.findById(Long.valueOf(primeAccountId))
                .orElseThrow(() -> new IllegalArgumentException("Prime account not found"));
            staff.setPrimeAccount(prime);
        }
    
        return staffRepository.save(staff);
    } 

}
