package com.facens.ac2.repositories;

import com.facens.ac2.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseModel, Integer> {
}
