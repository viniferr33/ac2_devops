package com.facens.ac2.app.services;

import com.facens.ac2.app.models.StudentModel;
import com.facens.ac2.app.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentModel save(StudentModel studentModel) {
        return studentRepository.save(studentModel);
    }

    public List<StudentModel> list() {
        return studentRepository.findAll();
    }
}
