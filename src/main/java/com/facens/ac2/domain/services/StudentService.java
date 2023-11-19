package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.builders.StudentBuilder;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.repository.IStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    public final IStudentRepository studentRepository;

    public StudentService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(String name, String email) throws StudentException {
        StudentBuilder studentBuilder = new StudentBuilder();

        var student = studentBuilder
                .withName(name)
                .withEmail(email)
                .build();

        return studentRepository.save(student);
    }

    public Optional<Student> getStudent(String email) {
        return studentRepository.findByEmail(email);
    }

    public Optional<Student> getStudent(UUID id) {
        return studentRepository.findById(id);
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public Integer getAvailableCourseNum(Student student) {
        if (student.getCourses().isEmpty()) {
            return 1;
        }

        int numCourse = 0;

        for (EnrolledCourse enrolledCourse : student.getCourses()) {
            if (enrolledCourse.status == EnrollmentStatus.COMPLETED && enrolledCourse.finalGrade >= 7.0) {
                numCourse += 3;
            }
        }

        return numCourse;
    }
}
