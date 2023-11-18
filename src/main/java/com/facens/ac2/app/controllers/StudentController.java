package com.facens.ac2.app.controllers;

import com.facens.ac2.app.requests.CreateStudentRequest;
import com.facens.ac2.domain.entities.builders.exceptions.StudentException;
import com.facens.ac2.domain.services.StudentService;
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

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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
