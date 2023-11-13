package com.facens.ac2.services;

import com.facens.ac2.models.StudentModel;
import com.facens.ac2.repositories.StudentRepository;
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
