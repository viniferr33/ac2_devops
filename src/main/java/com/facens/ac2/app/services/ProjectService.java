package com.facens.ac2.app.services;

import com.facens.ac2.app.models.ProjectModel;
import com.facens.ac2.app.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectModel save(ProjectModel projectModel) {
        return projectRepository.save(projectModel);
    }

    public List<ProjectModel> list() {
        return projectRepository.findAll();
    }
}
