package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.builders.StudentBuilder;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private IStudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(IStudentRepository.class);
        studentService = new StudentService(studentRepository);
    }

    @Test
    void createStudent_Success() throws StudentException {
        // Arrange
        String name = "Johnny Silverhand";
        String email = "jhnny@samurai.net";

        StudentBuilder studentBuilder = new StudentBuilder();
        Student expectedStudent = studentBuilder
                .withName(name)
                .withEmail(email)
                .build();

        when(studentRepository.save(any(Student.class))).thenReturn(expectedStudent);

        // Act
        Student createdStudent = studentService.createStudent(name, email);

        // Assert
        assertNotNull(createdStudent);
        assertEquals(name, createdStudent.getName());
        assertEquals(email, createdStudent.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void getAvailableCoursesNum_withCompletedCourses() throws StudentException {

    }
}