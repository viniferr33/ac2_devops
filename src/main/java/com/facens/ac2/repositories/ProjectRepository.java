package com.facens.ac2.repositories;

import com.facens.ac2.models.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectModel, UUID> {
}
