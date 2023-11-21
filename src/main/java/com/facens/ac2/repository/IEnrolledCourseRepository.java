package com.facens.ac2.repository;

import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {
    public Optional<EnrolledCourse> findOneByStudentAndCourse(Student student, Course course);
}
