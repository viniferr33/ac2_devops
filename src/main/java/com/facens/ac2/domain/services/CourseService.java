package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.repository.ICourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    public final ICourseRepository courseRepository;

    public CourseService(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(String name) {
        Course course = new Course(name);

        return courseRepository.save(course);
    }

    public Optional<Course> getCourse(UUID id) {
        return courseRepository.findById(id);
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }
}
