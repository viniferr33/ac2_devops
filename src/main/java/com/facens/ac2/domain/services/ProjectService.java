package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.Project;
import com.facens.ac2.repository.IProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    public final IProjectRepository projectRepository;

    public ProjectService(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(String name, String category) {
        Project project = new Project(
                name,
                category
        );

        return projectRepository.save(project);
    }

    public Optional<Project> getProject(UUID id) {
        return projectRepository.findById(id);
    }

    public List<Project> list() {
        return projectRepository.findAll();
    }
}
