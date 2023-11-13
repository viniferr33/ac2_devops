package com.facens.ac2.services;

import com.facens.ac2.models.CourseModel;
import com.facens.ac2.repositories.CourseRepository;
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
