package com.facens.ac2.repository;

import com.facens.ac2.domain.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICourseRepository extends JpaRepository<Course, UUID> {
}
