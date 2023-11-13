package com.facens.ac2.repositories;

import com.facens.ac2.models.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentModel, UUID> {
}
