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
import static org.mockito.Mockito.*;

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
        verify(enrolledCourseRepository, times(1)).save(any(EnrolledCourse.class));
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
        verify(studentService, times(1)).getStudent(any(UUID.class));
        verify(courseService, times(1)).getCourse(any(UUID.class));
        verify(enrolledCourseRepository, times(1)).findOneByStudentAndCourse(any(Student.class), any(Course.class));
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

    @Test
    void changeCourseStatus_FinishCourse_Success() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.ACTIVE)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act
        EnrolledCourse enrolledCourse = enrolledCourseService.changeCourseStatus(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "finish",
                10.0
        );

        // Assert
        assertNotNull(enrolledCourse);
        assertEquals(EnrollmentStatus.COMPLETED, enrolledCourse.getStatus());
        assertEquals(10.0, enrolledCourse.getFinalGrade());
    }

    @Test
    void changeCourseStatus_FinishCourse_MissingGrade() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.ACTIVE)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act and Assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.changeCourseStatus(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    "finish",
                    null
            );
        });
    }

    @Test
    void changeCourseStatus_FinishCourse_InvalidGrade_High() throws StudentException, CourseException, EnrolledCourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.ACTIVE)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act and Assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.changeCourseStatus(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    "finish",
                    11.0
            );
        });
    }


    @Test
    void changeCourseStatus_FinishCourse_InvalidGrade_Low() throws StudentException, CourseException, EnrolledCourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.ACTIVE)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act and Assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.changeCourseStatus(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    "finish",
                    -10.0
            );
        });
    }

    @Test
    void changeCourseStatus_FinishCourse_InvalidStatus() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.DROPPED)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act and Assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.changeCourseStatus(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    "finish",
                    -10.0
            );
        });
    }

    @Test
    void changeCourseStatus_DropCourse_Success() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.ACTIVE)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act
        EnrolledCourse enrolledCourse = enrolledCourseService.changeCourseStatus(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "drop",
                null
        );

        // Assert
        assertNotNull(enrolledCourse);
        assertEquals(EnrollmentStatus.DROPPED, enrolledCourse.getStatus());
    }

    @Test
    void changeCourseStatus_DropCourse_InvalidStatus() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.DROPPED)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act and Assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.changeCourseStatus(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    "drop",
                    null
            );
        });
    }

    @Test
    void changeCourseStatus_ReopenCourse_Success() throws EnrolledCourseException, StudentException, CourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.DROPPED)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act
        EnrolledCourse enrolledCourse = enrolledCourseService.changeCourseStatus(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "reopen",
                null
        );

        // Assert
        assertNotNull(enrolledCourse);
        assertEquals(EnrollmentStatus.ACTIVE, enrolledCourse.getStatus());
    }

    @Test
    void changeCourseStatus_ReopenCourse_InvalidStatus() throws StudentException, CourseException, EnrolledCourseException {
        // Arrange
        Student student = new Student();
        Course course = new Course();

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        EnrolledCourse expectedEnrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .withStatus(EnrollmentStatus.ACTIVE)
                .build();

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseService.getCourse(any(UUID.class))).thenReturn(course);
        when(enrolledCourseRepository.findOneByStudentAndCourse(any(Student.class), any(Course.class))).thenReturn(Optional.ofNullable(expectedEnrolledCourse));
        when(enrolledCourseRepository.save(any(EnrolledCourse.class))).thenReturn(expectedEnrolledCourse);

        // Act and Assert
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.changeCourseStatus(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    "reopen",
                    null
            );
        });
    }

    @Test
    void changeCourseStatus_InvalidOperation() {
        assertThrows(EnrolledCourseException.class, () -> {
            enrolledCourseService.changeCourseStatus(UUID.randomUUID(), UUID.randomUUID(), "invalid", null);
        });
    }
}