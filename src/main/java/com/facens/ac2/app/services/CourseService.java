package com.facens.ac2.app.services;

import com.facens.ac2.app.models.CourseModel;
import com.facens.ac2.app.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseModel save(CourseModel courseModel) {
        return courseRepository.save(courseModel);
    }

    public List<CourseModel> list() {
        return courseRepository.findAll();
    }
}
