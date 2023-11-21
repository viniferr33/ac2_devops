package com.facens.ac2.app.controllers;

import com.facens.ac2.app.requests.CreateProjectRequest;
import com.facens.ac2.domain.entities.Project;
import com.facens.ac2.domain.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    void get_listProjects() throws Exception {
        // Arrange
        List<Project> mockProjects = Arrays.asList(
                new Project("Project1", "Category1"),
                new Project("Project2", "Category2")
        );

        when(projectService.list()).thenReturn(mockProjects);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/project")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Project1"))
                .andExpect(jsonPath("$[1].name").value("Project2"));
    }

    @Test
    void get_getProjectById() throws Exception {
        // Arrange
        UUID projectId = UUID.randomUUID();
        Project mockProject = new Project("Test Project", "Test Category");
        mockProject.setId(projectId);

        when(projectService.getProject(projectId)).thenReturn(Optional.of(mockProject));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Project"));
    }

    @Test
    void post_createProject() throws Exception {
        // Arrange
        CreateProjectRequest createProjectRequest = new CreateProjectRequest("New Project", "New Category");
        Project mockProject = new Project(createProjectRequest.name(), createProjectRequest.category());
        mockProject.setId(UUID.randomUUID());

        when(projectService.create(createProjectRequest.name(), createProjectRequest.category())).thenReturn(mockProject);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Project\", \"category\":\"New Category\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Project"));
    }
}