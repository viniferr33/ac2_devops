package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.builders.StudentBuilder;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.repository.IStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Student getStudent(String email) throws StudentException {
        var student = studentRepository.findByEmail(email);

        if (student.isEmpty()) {
            throw new StudentException("Student does not exists!");
        }

        return student.get();
    }

    public Student getStudent(UUID id) throws StudentException {
        var student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Student does not exists!");
        }

        return student.get();
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public Integer getAvailableCourseNum(UUID id) throws StudentException {
        var student = getStudent(id);

        return getAvailableCourseNum(student);
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
