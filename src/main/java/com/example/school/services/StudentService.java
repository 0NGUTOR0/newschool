package com.example.school.services;

import com.example.school.models.Student;
import com.example.school.repositories.StudentRepositories.StudentRepository;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public Student createStudent(Student student) {
        return studentRepository.save(student);
    } 
     public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Student not found"));
    }
    
}
