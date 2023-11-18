package com.facens.ac2.app.controllers;

import com.facens.ac2.app.requests.CreateCourseRequest;
import com.facens.ac2.domain.services.CourseService;
import com.facens.ac2.dto.CourseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/course")
public class CourseController {

    final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> list() {
        return CourseDTO.fromEntity(
                courseService.listCourses()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable String id) {
        var course = courseService.getCourse(UUID.fromString(id));

        return course.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                CourseDTO.fromEntity(value)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new CourseDTO()
        ));
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody @Valid CreateCourseRequest request) {
        var course = courseService.createCourse(
                request.name()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                CourseDTO.fromEntity(course)
        );
    }
}
