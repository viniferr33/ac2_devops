package com.facens.ac2.app.repositories;

import com.facens.ac2.app.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseModel, Integer> {
}
