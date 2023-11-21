package com.facens.ac2.repository;

import com.facens.ac2.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IStudentRepository extends JpaRepository<Student, UUID> {
    public Optional<Student> findByEmail(String email);
}
