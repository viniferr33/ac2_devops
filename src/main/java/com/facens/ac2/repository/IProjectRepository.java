package com.facens.ac2.repository;

import com.facens.ac2.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProjectRepository extends JpaRepository<Project, UUID> {
}
