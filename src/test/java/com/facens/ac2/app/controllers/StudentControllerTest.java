package com.facens.ac2.app.controllers;

import com.facens.ac2.app.requests.CreateStudentRequest;
import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.services.CourseService;
import com.facens.ac2.domain.services.EnrolledCourseService;
import com.facens.ac2.domain.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @MockBean
    private CourseService courseService;
    @MockBean
    private EnrolledCourseService enrolledCourseService;

    @Test
    void get_listStudents() throws Exception {
        // Arrange
        List<Student> mockStudents = Arrays.asList(
                new Student("Student1", "student1@test.com", new ArrayList<>(), new ArrayList<>()),
                new Student("Student2", "student2@test.com", new ArrayList<>(), new ArrayList<>())
        );
        when(studentService.listStudents()).thenReturn(mockStudents);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Student1"))
                .andExpect(jsonPath("$[1].name").value("Student2"));
    }

    @Test
    void get_getStudentById() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        Student mockStudent = new Student("Student1", "student1@test.com", new ArrayList<>(), new ArrayList<>());
        mockStudent.setId(id);
        when(studentService.getStudent(id)).thenReturn(mockStudent);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Student1"))
                .andExpect(jsonPath("$.email").value("student1@test.com"));
    }

    @Test
    void get_getStudentCourses() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        Student mockStudent = new Student("Student1", "student1@test.com", new ArrayList<>(), new ArrayList<>());

        List<EnrolledCourse> mockEnrolledCourses = Arrays.asList(
                new EnrolledCourse(mockStudent, new Course("Course1"), EnrollmentStatus.ACTIVE),
                new EnrolledCourse(mockStudent, new Course("Course2"), EnrollmentStatus.ACTIVE)
        );


        mockStudent.setId(id);
        mockStudent.setCourses(mockEnrolledCourses);

        when(studentService.getStudent(id)).thenReturn(mockStudent);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}/course", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id").value(mockStudent.getId().toString()))
                .andExpect(jsonPath("$.student.name").value(mockStudent.getName()))
                .andExpect(jsonPath("$.courses[0].course.name").value("Course1"))
                .andExpect(jsonPath("$.courses[1].course.name").value("Course2"));
    }

    @Test
    void get_getStudentAvailableCourses() throws Exception {
        // Arrange
        List<Course> mockCourses = Arrays.asList(
                new Course("Course1"),
                new Course("Course2")
        );

        when(courseService.listAvailableCoursesForStudent(any(UUID.class))).thenReturn(mockCourses);
        when(studentService.getAvailableCourseNum(any(UUID.class))).thenReturn(3);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}/course/available", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courses[0].name").value("Course1"))
                .andExpect(jsonPath("$.courses[1].name").value("Course2"))
                .andExpect(jsonPath("$.numAvaliableCourses").value(3));
    }

    @Test
    void post_createStudent() throws Exception {
        // Arrange
        CreateStudentRequest createStudentRequest = new CreateStudentRequest("Student1", "student1@email.com");
        Student mockStudent = new Student(createStudentRequest.name(), createStudentRequest.email(), new ArrayList<>(), new ArrayList<>());
        mockStudent.setId(UUID.randomUUID());

        when(studentService.createStudent(createStudentRequest.name(), createStudentRequest.email())).thenReturn(mockStudent);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Student1\", \"email\":\"student1@email.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Student1"))
                .andExpect(jsonPath("$.email").value("student1@email.com"));
    }

    @Test
    void post_enrollOnCourse() throws Exception {
        // Arrange
        EnrolledCourse mockEnrolledCourse = new EnrolledCourse(new Student(), new Course("Course1"), EnrollmentStatus.ACTIVE);

        when(enrolledCourseService.create(any(UUID.class), any(UUID.class))).thenReturn(mockEnrolledCourse);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/student/{id}/course", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"courseId\":\"" + UUID.randomUUID().toString() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.course.name").value("Course1"));
    }

    @Test
    void get_getEnrolledCourse() throws Exception {
        // Arrange
        EnrolledCourse mockEnrolledCourse = new EnrolledCourse(new Student(), new Course("Course1"), EnrollmentStatus.ACTIVE);

        when(enrolledCourseService.getEnrolledCourse(any(UUID.class), any(UUID.class))).thenReturn(mockEnrolledCourse);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/student/{idStudent}/course/{idCourse}", UUID.randomUUID().toString(), UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course.name").value("Course1"));
    }
}