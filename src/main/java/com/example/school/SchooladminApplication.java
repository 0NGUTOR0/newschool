package com.example.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.school.repositories")
public class SchooladminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchooladminApplication.class, args);
    }
}