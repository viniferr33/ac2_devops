package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.exceptions.CourseException;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.repository.ICourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService {
    public final ICourseRepository courseRepository;
    public final StudentService studentService;

    public CourseService(ICourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    public Course createCourse(String name) {
        Course course = new Course(name);

        return courseRepository.save(course);
    }

    public Course getCourse(UUID id) throws CourseException {
        var course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new CourseException("Course does not exists!");
        }

        return course.get();
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    public List<Course> listAvailableCoursesForStudent(UUID id) throws StudentException {
        var student = studentService.getStudent(id);

        return listAvailableCoursesForStudent(student);
    }

    public List<Course> listAvailableCoursesForStudent(Student student) {
        var allCourses = listCourses();
        var studentCourses = student.getCourses().stream()
                .map(EnrolledCourse::getCourse)
                .toList();

        return allCourses.stream()
                .filter(course -> !studentCourses.contains(course))
                .collect(Collectors.toList());
    }
}
