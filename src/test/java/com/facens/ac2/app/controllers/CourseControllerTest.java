package com.facens.ac2.app.controllers;

import com.facens.ac2.app.requests.CreateCourseRequest;
import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.services.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void get_listCourses() throws Exception {
        // Arrange
        List<Course> mockCourses = Arrays.asList(
                new Course("Course1"),
                new Course("Course2")
        );
        when(courseService.listCourses()).thenReturn(mockCourses);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/course")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Course1"))
                .andExpect(jsonPath("$[1].name").value("Course2"));
    }

    @Test
    void get_getCourseById() throws Exception {
        // Arrange
        UUID courseId = UUID.randomUUID();
        Course mockCourse = new Course("Test Course");
        mockCourse.setId(courseId);

        when(courseService.getCourse(courseId)).thenReturn(mockCourse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/course/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Course"));
    }

    @Test
    void post_createCourse() throws Exception {
        // Arrange
        CreateCourseRequest createCourseRequest = new CreateCourseRequest("New Course");
        Course mockCourse = new Course(createCourseRequest.name());
        mockCourse.setId(UUID.randomUUID());

        when(courseService.createCourse(createCourseRequest.name())).thenReturn(mockCourse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Course\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Course"));
    }
}