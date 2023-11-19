package com.facens.ac2.app.controllers;

import com.facens.ac2.app.requests.CreateStudentRequest;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.domain.services.CourseService;
import com.facens.ac2.domain.services.StudentService;
import com.facens.ac2.dto.AvailableCoursesDTO;
import com.facens.ac2.dto.StudentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    final StudentService studentService;
    final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public List<StudentDTO> list() {
        return StudentDTO.fromEntity(
                studentService.listStudents()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable String id) {
        var student = studentService.getStudent(UUID.fromString(id));

        return student.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                StudentDTO.fromEntity(value)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new StudentDTO()
        ));
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<AvailableCoursesDTO> listAvaliableCourses(@PathVariable String id) {
        var student = studentService.getStudent(UUID.fromString(id));

        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new AvailableCoursesDTO()
            );
        }

        var availableCourses = courseService.listAvailableCoursesForStudent(student.get());
        var numAvaliableCourses = studentService.getAvailableCourseNum(student.get());

        return ResponseEntity.status(HttpStatus.OK).body(
                AvailableCoursesDTO.fromEntity(availableCourses, numAvaliableCourses)
        );
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody @Valid CreateStudentRequest request) throws StudentException {
        var student = studentService.createStudent(
                request.name(),
                request.email()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                StudentDTO.fromEntity(student)
        );
    }
}
