package com.facens.ac2.controllers;

import com.facens.ac2.dtos.CourseDTO;
import com.facens.ac2.dtos.CreateCourseDTO;
import com.facens.ac2.models.CourseModel;
import com.facens.ac2.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        List<CourseModel> allCourses = courseService.list();
        List<CourseDTO> allCoursesDTO = new ArrayList<>();

        for (CourseModel courseModel : allCourses) {
            var courseDTO = new CourseDTO(
                    courseModel.getId(),
                    courseModel.getName()
            );

            allCoursesDTO.add(courseDTO);
        }

        return allCoursesDTO;
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody @Valid CreateCourseDTO createCourseDTO) {
        var input = new CourseModel();
        BeanUtils.copyProperties(createCourseDTO, input);

        var courseModel = courseService.save(input);
        var courseDTO = new CourseDTO(
                courseModel.getId(),
                courseModel.getName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(courseDTO);
    }
}
