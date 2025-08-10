package com.example.school.controllers;

import com.example.school.dto.StaffDTO;
import com.example.school.dto.StaffResponseDTO;
import com.example.school.models.PrimeAccount;
import com.example.school.models.StaffRegisterAccount;
import com.example.school.repositories.PrimeRepository;
import com.example.school.repositories.StaffRepository;
import com.example.school.security.TokenService;
import com.example.school.services.EmailService;
import com.example.school.services.PrimeService;
import com.example.school.services.StaffSignupService;

import jakarta.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Transactional
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/school/staff")
@Validated
public class StaffController {

    private final StaffSignupService staffService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PrimeService primeService;
    private final StaffRepository staffRepository;
    private final PrimeRepository primeRepository;
    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    public StaffController(
            StaffSignupService staffService,
            TokenService tokenService,
            EmailService emailService,
            PrimeService primeService,
            PrimeRepository primeRepository,
            StaffRepository staffRepository
    ) {
        this.staffService = staffService;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.primeService = primeService;
        this.primeRepository=primeRepository;
        this.staffRepository = staffRepository;
    }

    @PostMapping("/addstaff")
    @PreAuthorize("hasAuthority('ROLE_PRIMEADMIN')")
    public ResponseEntity<StaffResponseDTO> addStaff(@RequestBody StaffDTO staffDto) {
        try {
            logger.info("Received request to add staff: {}", staffDto.getEmail());
    
            String email = staffDto.getEmail().toLowerCase().trim();
            if (staffService.emailExists(email)) {
                logger.warn("Email already exists: {}", email);
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
            }
    
            // 🔐 Get authenticated PrimeAccount from JWT
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Long primeId = jwt.getClaim("id");
    
            if (primeId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID not found in JWT token.");
            }
    
            PrimeAccount primeAccount = primeService.findById(primeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prime account not found"));
    
            // ✳️ Set staff properties including primeAccount and school
            StaffRegisterAccount staff = new StaffRegisterAccount();
            staff.setFirstname(staffDto.getFirstname());
            staff.setLastname(staffDto.getLastname());
            staff.setEmail(email);
            staff.setRole(staffDto.getRole());
            staff.setIsVerified(staffDto.getIsVerified());
            staff.setPrimeAccount(primeAccount);
            
    
            // 🧠 Save the staff
            StaffRegisterAccount savedStaff = staffService.addStaffAccount(staff);
    
            // ✉️ Send email invite
            String token = tokenService.generateStaffInviteToken(email, primeId);
            try {
                emailService.sendSignupLink(email, token);
            } catch (Exception e) {
                logger.error("Email sending failed: {}", e.getMessage(), e);
            }
    
            // 🎯 Return full response with school and prime account attached
            StaffResponseDTO response = new StaffResponseDTO(savedStaff);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate email entry", e);
    
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    
        } catch (Exception e) {
            logger.error("Unexpected server error: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error", e);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllStaff(
        @RequestParam(defaultValue = "1") @Positive(message = "Page must be positive") int page,
        @RequestParam(defaultValue = "20") @Positive(message = "Limit must be positive") int limit,
        @RequestParam(required = false) String firstname,
        @RequestParam(required = false) String lastname,
        @RequestParam(required = false) String school,
        @RequestParam(required = false) String role,
        @RequestParam(required = false) Boolean isVerified

    ) {
        Pageable pageable = PageRequest.of(page - 1, Math.min(limit, 100));
    
        try {
            // Extract JWT claim
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Long userId = jwt.getClaim("id"); // ✅ extract ID from token
    
            Optional<PrimeAccount> primeAccountOptional = primeRepository.findById(userId);
            if (primeAccountOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Prime account not found for the authenticated user."));
            }
            PrimeAccount primeAccount = primeAccountOptional.get();
    
            Page<StaffRegisterAccount> staffPage = staffRepository.filterStaffByPrimeAccount(
                primeAccount.getId(), firstname, lastname, role, school, isVerified, pageable
            );
    
            List<StaffResponseDTO> staffDTOs = staffPage.getContent().stream()
                .map(StaffResponseDTO::new)
                .toList();
    
            Map<String, Object> response = new HashMap<>();
            response.put("students", staffDTOs);
            response.put("currentPage", staffPage.getNumber() + 1);
            response.put("totalItems", staffPage.getTotalElements());
            response.put("totalPages", staffPage.getTotalPages());
            response.put("pageSize", staffPage.getSize());
    
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An error occurred while fetching staff: " + e.getMessage()));
        }
    }
    
   

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteStaff(@PathVariable String id) {
        try {
            staffService.deleteStaff(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Staff deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error", e);
        }
    }
}
