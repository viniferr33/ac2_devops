package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.builders.EnrolledCourseBuilder;
import com.facens.ac2.domain.entities.exceptions.CourseException;
import com.facens.ac2.domain.entities.exceptions.EnrolledCourseException;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.repository.IEnrolledCourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnrolledCourseServiceTest {
    private IEnrolledCourseRepository enrolledCourseRepository;
    private StudentService studentService;
    private CourseService courseService;
    private EnrolledCourseService enrolledCourseService;

    @BeforeEach
    void setUp() {
        studentService = mock(StudentService.class);
        courseService = mock(CourseService.class);
        enrolledCourseRepository = mock(IEnrolledCourseRepository.class);

        enrolledCourseService = new EnrolledCourseService(
                enrolledCourseRepository,
                studentService,
                courseService
        );
    }

    @Test
    void enrolOnNewCourse_firstCourse() throws StudentException, CourseException, EnrolledCourseException {
        // Arrange
        Student student = new Student();
        student.setCourses(new ArrayList<>());

        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act
        EnrolledCourse enrolledCourse = enrolledCourseService.create(UUID.randomUUID(), UUID.randomUUID());

        // Assert
        assertNotNull(enrolledCourse);
        assertEquals(EnrollmentStatus.ACTIVE, enrolledCourse.getStatus());
        assertEquals(student, enrolledCourse.getStudent());
        assertEquals(course, enrolledCourse.getCourse());
    }

    @Test
    void enrolOnNewCourse_Success() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        List<EnrolledCourse> studentEnrolledCourses = new ArrayList<EnrolledCourse>();
        studentEnrolledCourses.add(new EnrolledCourse());

        Student student = new Student();
        student.setCourses(studentEnrolledCourses);

        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .build();

        List<Course> availableCourses = new ArrayList<Course>();
        availableCourses.add(course);

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(studentService.getAvailableCourseNum(any(Student.class))).thenReturn(3);
        when(courseService.listAvailableCoursesForStudent(any(Student.class))).thenReturn(availableCourses);
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act
        EnrolledCourse enrolledCourse = enrolledCourseService.create(UUID.randomUUID(), UUID.randomUUID());

        // Assert
        assertNotNull(enrolledCourse);
        assertEquals(EnrollmentStatus.ACTIVE, enrolledCourse.getStatus());
        assertEquals(student, enrolledCourse.getStudent());
        assertEquals(course, enrolledCourse.getCourse());
    }

    @Test
    void enrolOnNewCourse_NotEligible() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        List<EnrolledCourse> studentEnrolledCourses = new ArrayList<EnrolledCourse>();
        studentEnrolledCourses.add(new EnrolledCourse());

        Student student = new Student();
        student.setCourses(studentEnrolledCourses);

        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(studentService.getAvailableCourseNum(any(Student.class))).thenReturn(0);

        // Act and Assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.create(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void enrolOnNewCourse_NotAvailable() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        List<EnrolledCourse> studentEnrolledCourses = new ArrayList<EnrolledCourse>();
        studentEnrolledCourses.add(new EnrolledCourse());

        Student student = new Student();
        student.setCourses(studentEnrolledCourses);

        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .build();

        List<Course> availableCourses = new ArrayList<Course>();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(studentService.getAvailableCourseNum(any(Student.class))).thenReturn(3);
        when(courseService.listAvailableCoursesForStudent(any(Student.class))).thenReturn(availableCourses);
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act and assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.create(UUID.randomUUID(), UUID.randomUUID());
        });
    }

    @Test
    void getEnrolledCourse_Success() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));

        // Act
        EnrolledCourse enrolledCourse = enrolledCourseService.getEnrolledCourse(UUID.randomUUID(), UUID.randomUUID());

        // Assert
        assertNotNull(enrolledCourse);
        assertEquals(student, enrolledCourse.getStudent());
        assertEquals(course, enrolledCourse.getCourse());
    }

    @Test
    void getEnrolledCourse_NotEnrolled() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.getEnrolledCourse(UUID.randomUUID(), UUID.randomUUID());
        });
    }
}