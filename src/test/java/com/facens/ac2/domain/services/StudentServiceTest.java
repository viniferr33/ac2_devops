package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.builders.StudentBuilder;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
    void getStudent_byEmail() throws StudentException {
        // Arrange
        String name = "Johnny Silverhand";
        String email = "jhnny@samurai.net";

        StudentBuilder studentBuilder = new StudentBuilder();
        Student expectedStudent = studentBuilder
                .withName(name)
                .withEmail(email)
                .build();

        when(studentRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(expectedStudent));

        // Act
        Student getStudent = studentService.getStudent(email);

        // Assert
        assertNotNull(getStudent);
        assertEquals(name, getStudent.getName());
        assertEquals(email, getStudent.getEmail());
        verify(studentRepository, times(1)).findByEmail(any(String.class));
    }

    @Test
    void getStudent_byUUID_StudentDoesNotExists() {
        // Arrange
        when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentException.class, () -> {
            studentService.getStudent(UUID.randomUUID());
        });
    }

    @Test
    void getStudent_byEmail_StudentDoesNotExists() {
        // Arrange
        when(studentRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(StudentException.class, () -> {
            studentService.getStudent("email@email.com");
        });
    }

    @Test
    void getStudent_byUUID() throws StudentException {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Johnny Silverhand";
        String email = "jhnny@samurai.net";

        StudentBuilder studentBuilder = new StudentBuilder();
        Student expectedStudent = studentBuilder
                .withId(id)
                .withName(name)
                .withEmail(email)
                .build();

        when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(expectedStudent));

        // Act
        Student getStudent = studentService.getStudent(id);

        // Assert
        assertNotNull(getStudent);
        assertEquals(id, getStudent.getId());
        assertEquals(name, getStudent.getName());
        assertEquals(email, getStudent.getEmail());
        verify(studentRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void listStudents() {
        // Arrange
        List<Student> expectedStudentList = new ArrayList<Student>();
        expectedStudentList.add(new Student());
        expectedStudentList.add(new Student());

        when(studentRepository.findAll()).thenReturn(expectedStudentList);

        // Act
        List<Student> studentList = studentService.listStudents();

        // Assert
        assertNotNull(studentList);
        assertEquals(2, studentList.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getAvailableCoursesNum_withCompletedCourses() throws StudentException {
        // Arrange
        EnrolledCourse course1 = new EnrolledCourse();
        course1.setStatus(EnrollmentStatus.COMPLETED);
        course1.setFinalGrade(7.0);

        EnrolledCourse course2 = new EnrolledCourse();
        course2.setStatus(EnrollmentStatus.COMPLETED);
        course2.setFinalGrade(4.0);

        List<EnrolledCourse> courses = new ArrayList<EnrolledCourse>();
        courses.add(course1);
        courses.add(course2);

        UUID id = UUID.randomUUID();
        String name = "Johnny Silverhand";
        String email = "jhnny@samurai.net";

        StudentBuilder studentBuilder = new StudentBuilder();
        Student expectedStudent = studentBuilder
                .withId(id)
                .withName(name)
                .withEmail(email)
                .withCourses(courses)
                .build();

        when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(expectedStudent));

        // Act
        int numCourses = studentService.getAvailableCourseNum(id);

        // Assert
        assertEquals(3, numCourses);
    }

    @Test
    void getAvailableCoursesNum_withNoCompletedCourses() throws StudentException {
        // Arrange
        EnrolledCourse course1 = new EnrolledCourse();
        course1.setStatus(EnrollmentStatus.DROPPED);

        EnrolledCourse course2 = new EnrolledCourse();
        course2.setStatus(EnrollmentStatus.ACTIVE);

        List<EnrolledCourse> courses = new ArrayList<EnrolledCourse>();
        courses.add(course1);
        courses.add(course2);

        UUID id = UUID.randomUUID();
        String name = "Johnny Silverhand";
        String email = "jhnny@samurai.net";

        StudentBuilder studentBuilder = new StudentBuilder();
        Student expectedStudent = studentBuilder
                .withId(id)
                .withName(name)
                .withEmail(email)
                .withCourses(courses)
                .build();

        when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(expectedStudent));

        // Act
        int numCourses = studentService.getAvailableCourseNum(id);

        // Assert
        assertEquals(0, numCourses);
    }

    @Test
    void getAvailableCoursesNum_firstCourse() throws StudentException {
        // Arrange
        List<EnrolledCourse> courses = new ArrayList<EnrolledCourse>();

        UUID id = UUID.randomUUID();
        String name = "Johnny Silverhand";
        String email = "jhnny@samurai.net";

        StudentBuilder studentBuilder = new StudentBuilder();
        Student expectedStudent = studentBuilder
                .withId(id)
                .withName(name)
                .withEmail(email)
                .withCourses(courses)
                .build();

        when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(expectedStudent));

        // Act
        int numCourses = studentService.getAvailableCourseNum(id);

        // Assert
        assertEquals(1, numCourses);
    }
}