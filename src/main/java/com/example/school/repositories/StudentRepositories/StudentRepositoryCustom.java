package com.example.school.repositories.StudentRepositories;

import com.example.school.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentRepositoryCustom {
    Page<Student> filterStudents(String name, String grade, Integer age, String sex, String religion,
                                    String nationality, String state, String LGA, String school, Pageable pageable);
}