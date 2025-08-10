package com.example.school.controllers;

import com.example.school.dto.StudentResponseDTO;
import com.example.school.models.PrimeAccount;
import com.example.school.models.Student;
import com.example.school.repositories.PrimeRepository;
import com.example.school.repositories.StudentRepositories.StudentRepository;
import com.example.school.services.PrimeService;

import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/school/students")
@Validated
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PrimeRepository primeRepository;

    @Autowired
    private PrimeService primeService;

    @PostMapping(value = "/addStudents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_PRIMEADMIN')")
    public ResponseEntity<Student> addLearner(
        @RequestParam("name") String name,
        @RequestParam("grade") String grade,
        @RequestParam("birthYear") int birthYear,
        @RequestParam("sex") String sex,
        @RequestParam("address") String address,
        @RequestParam("religion") String religion,
        @RequestParam("contactEmail") String contactEmail,
        @RequestParam("contactNumber") String contactNumber,
        @RequestParam("nationality") String nationality,
        @RequestParam("state") String state,
        @RequestParam("LGA") String LGA,
        @RequestParam(value = "image", required = false) MultipartFile imageFile,
        @RequestParam(value = "addressimage", required = false) MultipartFile addressImageFile,
        @RequestParam(value = "parentid", required = false) MultipartFile parentIdFile,
        @RequestParam("school") String school

    ) {
        try {
            if (name.isBlank() || grade.isBlank() || sex.isBlank() || address.isBlank() || religion.isBlank()
                || contactEmail.isBlank() || contactNumber.isBlank() || nationality.isBlank()
                || state.isBlank() || LGA.isBlank()) {
                throw new IllegalArgumentException("All text fields are required.");
            }

            if (!contactEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new IllegalArgumentException("Invalid email format.");
            }

            if (!contactNumber.matches("^\\+?[1-9]\\d{1,14}$")) {
                throw new IllegalArgumentException("Invalid phone number format.");
            }

            int currentYear = Year.now().getValue();
            if (birthYear <= 1900 || birthYear > currentYear) {
                throw new IllegalArgumentException("Invalid birth year.");
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Long id = jwt.getClaim("id");
            if (id == null ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID not found in JWT token.");
            }
            

            PrimeAccount primeAccount = primeService.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prime account not found"));
                    // PrimeAccount primeAccount = primeService.findById(id);
                

            int age = currentYear - birthYear;
            String uploadDir = "C:/Uploads/";
            String imagePath = (imageFile != null && !imageFile.isEmpty()) ? saveFile(uploadDir, imageFile) : null;
            String addressImagePath = (addressImageFile != null && !addressImageFile.isEmpty()) ? saveFile(uploadDir, addressImageFile) : null;
            String parentIdPath = (parentIdFile != null && !parentIdFile.isEmpty()) ? saveFile(uploadDir, parentIdFile) : null;

            Student student = new Student(
                name, grade, age, sex, address, religion,
                contactEmail, contactNumber, nationality,
                state, LGA, imagePath, addressImagePath, parentIdPath, primeAccount, school
            );
            

            Student savedStudent = studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A student with the same unique identifier already exists.");
        } catch (java.io.IOException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save image files: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid student data: " + e.getMessage());
        }
    }

    private String saveFile(String uploadDir, MultipartFile file) throws java.io.IOException, IllegalStateException {
        if (file == null || file.isEmpty()) return null;

        File dir = new File(uploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new java.io.IOException("Failed to create directory: " + uploadDir);
        }

        String uniqueName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File savedFile = new File(dir, uniqueName);
        file.transferTo(savedFile);

        return "/Uploads/" + uniqueName;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStudents(
        @RequestParam(defaultValue = "1") @Positive(message = "Page must be positive") int page,
        @RequestParam(defaultValue = "20") @Positive(message = "Limit must be positive") int limit,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String grade,
        @RequestParam(required = false) Integer age,
        @RequestParam(required = false) String sex,
        @RequestParam(required = false) String religion,
        @RequestParam(required = false) String nationality,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String LGA,
        @RequestParam(required = false) String school
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
    
            Page<Student> studentsPage = studentRepository.filterStudentsByPrimeAccount(
                primeAccount.getId(), name, grade, age, sex, religion, nationality, state, LGA, school, pageable
            );
    
            List<StudentResponseDTO> studentDTOs = studentsPage.getContent().stream()
                .map(StudentResponseDTO::new)
                .toList();
    
            Map<String, Object> response = new HashMap<>();
            response.put("students", studentDTOs);
            response.put("currentPage", studentsPage.getNumber() + 1);
            response.put("totalItems", studentsPage.getTotalElements());
            response.put("totalPages", studentsPage.getTotalPages());
            response.put("pageSize", studentsPage.getSize());
    
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An error occurred while fetching students: " + e.getMessage()));
        }
    }
    
@GetMapping("/{id}")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_PRIMEADMIN')")
public ResponseEntity<?> getStudentById(@PathVariable Long id) {
    try {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            StudentResponseDTO studentDTO = new StudentResponseDTO(studentOptional.get());
            return ResponseEntity.ok(studentDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Student not found with ID: " + id));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "An error occurred while fetching student: " + e.getMessage()));
    }
}

}
