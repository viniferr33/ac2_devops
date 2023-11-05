package com.facens.ac2.controllers;

import com.facens.ac2.dtos.CreateProjectDTO;
import com.facens.ac2.dtos.ProjectDTO;
import com.facens.ac2.models.ProjectModel;
import com.facens.ac2.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {

    final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectDTO> list() {
        List<ProjectModel> allProjects = projectService.list();
        List<ProjectDTO> allProjectsDTO = new ArrayList<>();

        for (ProjectModel allProject : allProjects) {
            var projectDTO = new ProjectDTO(
                    allProject.getId(),
                    allProject.getName(),
                    allProject.getCategory()
            );

            allProjectsDTO.add(projectDTO);
        }

        return allProjectsDTO;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@RequestBody @Valid CreateProjectDTO createProjectDTO) {
        var input = new ProjectModel();
        BeanUtils.copyProperties(createProjectDTO, input);

        var projectModel = projectService.save(input);
        var projectDTO = new ProjectDTO(
                projectModel.getId(),
                projectModel.getName(),
                projectModel.getCategory()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(projectDTO);
    }
}
