package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.builders.StudentBuilder;
import com.facens.ac2.domain.entities.builders.exceptions.StudentException;
import com.facens.ac2.repositories.IStudentRepository;

public class StudentService {
    public final IStudentRepository studentRepository;

    public StudentService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void createStudent(String name, String email) throws StudentException {
        StudentBuilder studentBuilder = new StudentBuilder();

        var student = studentBuilder
                .withName(name)
                .withEmail(email)
                .build();

        studentRepository.save(student);
    }


}
