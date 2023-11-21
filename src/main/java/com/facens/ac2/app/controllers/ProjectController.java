package com.facens.ac2.app.controllers;

import com.facens.ac2.app.requests.CreateProjectRequest;
import com.facens.ac2.domain.services.ProjectService;
import com.facens.ac2.dto.ProjectDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {

    public final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectDTO> list() {
        return ProjectDTO.fromEntity(
                projectService.list()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable @Valid String id) {
        var project = projectService.getProject(UUID.fromString(id));

        return project.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                ProjectDTO.fromEntity(value)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ProjectDTO()
        ));
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@RequestBody @Valid CreateProjectRequest request) {
        var project = projectService.create(
                request.name(),
                request.category()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ProjectDTO.fromEntity(project)
        );
    }
}
