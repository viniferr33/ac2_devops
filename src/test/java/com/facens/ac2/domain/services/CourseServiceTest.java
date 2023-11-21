package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.exceptions.CourseException;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.repository.ICourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    private ICourseRepository courseRepository;
    private CourseService courseService;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        courseRepository = mock(ICourseRepository.class);
        studentService = mock(StudentService.class);
        courseService = new CourseService(courseRepository, studentService);
    }

    @Test
    void createCourse_Success() {
        // Arrange
        String name = "Computer Science";
        Course expectedCourse = new Course();
        expectedCourse.setName(name);

        when(courseRepository.save(any(Course.class))).thenReturn(expectedCourse);

        // Act
        Course course = courseService.createCourse(name);

        // Assert
        assertNotNull(course);
        assertEquals(name, course.getName());
    }

    @Test
    void getCourse_Success() throws CourseException {
        // Arrange
        String name = "Computer Science";
        Course expectedCourse = new Course();
        expectedCourse.setName(name);

        when(courseRepository.findById(any(UUID.class))).thenReturn(Optional.of(expectedCourse));

        // Act
        Course course = courseService.getCourse(UUID.randomUUID());

        // Assert
        assertNotNull(course);
        assertEquals(name, course.getName());
    }

    @Test
    void getCourse_NotFound() {
        // Arrange
        when(courseRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(CourseException.class, () -> {
            courseService.getCourse(UUID.randomUUID());
        });
    }

    @Test
    void listCourse() {
        // Arrange
        List<Course> expectedCourseList = new ArrayList<Course>();
        expectedCourseList.add(new Course());
        expectedCourseList.add(new Course());

        when(courseRepository.findAll()).thenReturn(expectedCourseList);

        // Act
        List<Course> courseList = courseService.listCourses();

        // Assert
        assertNotNull(courseList);
        assertEquals(2, courseList.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void listAvailableCourses_ZeroCourses() throws StudentException {
        // Arrange
        List<Course> expectedCourseList = new ArrayList<Course>();
        expectedCourseList.add(new Course());
        expectedCourseList.add(new Course());

        Student student = new Student();
        student.setCourses(new ArrayList<EnrolledCourse>());

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseRepository.findAll()).thenReturn(expectedCourseList);

        // Act
        List<Course> availableCourses = courseService.listAvailableCoursesForStudent(UUID.randomUUID());

        // Assert
        assertNotNull(availableCourses);
        assertEquals(2, availableCourses.size());
    }

    @Test
    void listAvailableCourses_OneCourse() throws StudentException {
        // Arrange
        List<Course> expectedCourseList = new ArrayList<Course>();
        Course course1 = new Course();
        Course course2 = new Course();

        expectedCourseList.add(course1);
        expectedCourseList.add(course2);

        Student student = new Student();

        List<EnrolledCourse> enrolledCourseList = new ArrayList<EnrolledCourse>();
        EnrolledCourse enrolledCourse = new EnrolledCourse();
        enrolledCourse.setCourse(course1);
        enrolledCourseList.add(enrolledCourse);

        student.setCourses(enrolledCourseList);

        when(studentService.getStudent(any(UUID.class))).thenReturn(student);
        when(courseRepository.findAll()).thenReturn(expectedCourseList);

        // Act
        List<Course> availableCourses = courseService.listAvailableCoursesForStudent(UUID.randomUUID());

        // Assert
        assertNotNull(availableCourses);
        assertEquals(1, availableCourses.size());
        assertEquals(course2, availableCourses.get(0));
    }
}