package com.example.school.repositories.StudentRepositories;

import com.example.school.models.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Student> filterStudents(String name, String grade, Integer age, String sex, 
                    String religion, String nationality, String state, String LGA, String school, Pageable pageable) {
        // Build JPQL query dynamically
        StringBuilder jpql = new StringBuilder("SELECT s FROM Student s");
        List<String> conditions = new ArrayList<>();

        // Add conditions only if parameters are provided
        if (grade != null) conditions.add("s.grade = :grade");
        if (state != null) conditions.add("LOWER(s.state) = LOWER(:state)");
        if (LGA != null) conditions.add("LOWER(s.LGA) = LOWER(:LGA)");
        if (sex != null) conditions.add("LOWER(s.sex) = LOWER(:sex)");
        if (religion != null) conditions.add("LOWER(s.religion) = LOWER(:religion)");
        if (nationality != null) conditions.add("LOWER(s.nationality) = LOWER(:nationality)");
        if (name != null) conditions.add("LOWER(s.name) LIKE LOWER(:name)");
        if (age != null) conditions.add("s.age = :age");

        // Append WHERE clause only if conditions exist
        if (!conditions.isEmpty()) {
            jpql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        // Create query
        TypedQuery<Student> query = em.createQuery(jpql.toString(), Student.class);

        // Set parameters
        if (grade != null) query.setParameter("grade", grade);
        if (state != null) query.setParameter("state", state);
        if (LGA != null) query.setParameter("LGA", LGA);
        if (sex != null) query.setParameter("sex", sex);
        if (religion != null) query.setParameter("religion", religion);
        if (nationality != null) query.setParameter("nationality", nationality);
        if (name != null) query.setParameter("name", "%" + name + "%");
        if (age != null) query.setParameter("age", age);

        // Apply pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Execute query
        List<Student> students = query.getResultList();

        // Build count query
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(s) FROM Student s");
        if (!conditions.isEmpty()) {
            countJpql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        TypedQuery<Long> countQuery = em.createQuery(countJpql.toString(), Long.class);

        // Set parameters for count query
        if (grade != null) countQuery.setParameter("grade", grade);
        if (state != null) countQuery.setParameter("state", state);
        if (LGA != null) countQuery.setParameter("LGA", LGA);
        if (sex != null) countQuery.setParameter("sex", sex);
        if (religion != null) countQuery.setParameter("religion", religion);
        if (nationality != null) countQuery.setParameter("nationality", nationality);
        if (name != null) countQuery.setParameter("name", "%" + name + "%");
        if (age != null) countQuery.setParameter("age", age);

        Long total = countQuery.getSingleResult();

        return new PageImpl<>(students, pageable, total);
    }
}