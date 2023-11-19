package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.builders.EnrolledCourseBuilder;
import com.facens.ac2.domain.entities.exceptions.EnrolledCourseException;
import com.facens.ac2.repository.IEnrolledCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrolledCourseService {

    public final IEnrolledCourseRepository enrolledCourseRepository;
    public final StudentService studentService;
    public final CourseService courseService;

    public EnrolledCourseService(IEnrolledCourseRepository enrolledCourseRepository, StudentService studentService, CourseService courseService) {
        this.enrolledCourseRepository = enrolledCourseRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public EnrolledCourse create(Student student, Course course) throws EnrolledCourseException {
        /**
         * 1 -> Checks if is the first course of the student
         * 2 -> Checks if the student is able to enroll on a new course (availableCourseList >= 1)
         * 3 -> Checks if the student is not already enrolled on course
         */

        if (!student.getCourses().isEmpty()) {
            Integer numCourses = studentService.getAvailableCourseNum(student);

            if (numCourses < 1) {
                throw new EnrolledCourseException("Student is not eligible to enroll on a new course!");
            }

            List<Course> studentCourses = courseService.listAvailableCoursesForStudent(student);

            if (studentCourses.contains(course)) {
                throw new EnrolledCourseException("Student is already enrolled on course!");
            }
        }

        EnrolledCourseBuilder enrolledCourseBuilder = new EnrolledCourseBuilder();
        var enrolledCourse = enrolledCourseBuilder
                .withCourse(course)
                .withStudent(student)
                .build();

        return enrolledCourseRepository.save(enrolledCourse);
    }

    public EnrolledCourse getEnrolledCourse(Student student, Course course) throws EnrolledCourseException {
        var enrolledCourse = enrolledCourseRepository.findOneByStudentAndCourse(student, course);

        if (enrolledCourse == null) {
            throw new EnrolledCourseException("Student is not enrolled on Course!");
        }

        return enrolledCourse;
    }

    public EnrolledCourse finishCourse(Student student, Course course, Double finalGrade) throws EnrolledCourseException {
        var enrolledCourse = getEnrolledCourse(student, course);

        enrolledCourse.setFinalGrade(finalGrade);
        enrolledCourse.setStatus(EnrollmentStatus.COMPLETED);

        return enrolledCourseRepository.save(enrolledCourse);
    }

    public EnrolledCourse dropCourse(Student student, Course course) throws EnrolledCourseException {
        var enrolledCourse = getEnrolledCourse(student, course);

        enrolledCourse.setStatus(EnrollmentStatus.DROPPED);

        return enrolledCourseRepository.save(enrolledCourse);
    }

    public EnrolledCourse reopenCourse(Student student, Course course) throws EnrolledCourseException {
        var enrolledCourse = getEnrolledCourse(student, course);

        enrolledCourse.setStatus(EnrollmentStatus.ACTIVE);

        return enrolledCourseRepository.save(enrolledCourse);
    }
}
