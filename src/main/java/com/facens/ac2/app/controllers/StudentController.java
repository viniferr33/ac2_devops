package com.facens.ac2.app.controllers;

import com.facens.ac2.domain.services.StudentService;
import com.facens.ac2.dto.StudentDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
