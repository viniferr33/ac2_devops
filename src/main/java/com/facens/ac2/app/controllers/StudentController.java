package com.facens.ac2.app.controllers;

import com.facens.ac2.app.dtos.CreateStudentDTO;
import com.facens.ac2.app.dtos.StudentDTO;
import com.facens.ac2.app.models.StudentModel;
import com.facens.ac2.app.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<StudentModel> allStudents = studentService.list();
        List<StudentDTO> allStudentDTO = new ArrayList<>();

        for (StudentModel studentModel : allStudents) {
            var studentDTO = new StudentDTO(
                    studentModel.getId(),
                    studentModel.getName(),
                    studentModel.getEmail()
            );

            allStudentDTO.add(studentDTO);
        }

        return allStudentDTO;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody @Valid CreateStudentDTO createStudentDTO) {
        var input = new StudentModel();
        BeanUtils.copyProperties(createStudentDTO, input);

        var studentModel = studentService.save(input);
        var studentDTO = new StudentDTO(
                studentModel.getId(),
                studentModel.getName(),
                studentModel.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(studentDTO);
    }
}
