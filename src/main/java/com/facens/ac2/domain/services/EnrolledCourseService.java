package com.facens.ac2.domain.services;

import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.builders.EnrolledCourseBuilder;
import com.facens.ac2.domain.entities.exceptions.CourseException;
import com.facens.ac2.domain.entities.exceptions.EnrolledCourseException;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.repository.IEnrolledCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public EnrolledCourse create(UUID studentId, UUID courseId) throws EnrolledCourseException, StudentException, CourseException {
        /**
         * 1 -> Checks if is the first course of the student
         * 2 -> Checks if the student is able to enroll on a new course (availableCourseList >= 1)
         * 3 -> Checks if the student is not already enrolled on course
         */

        var student = studentService.getStudent(studentId);
        var course = courseService.getCourse(courseId);

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

    public EnrolledCourse getEnrolledCourse(UUID studentId, UUID courseId) throws StudentException, CourseException, EnrolledCourseException {
        var student = studentService.getStudent(studentId);
        var course = courseService.getCourse(courseId);

        return getEnrolledCourse(student, course);
    }

    public EnrolledCourse getEnrolledCourse(Student student, Course course) throws EnrolledCourseException {
        var enrolledCourse = enrolledCourseRepository.findOneByStudentAndCourse(student, course);

        if (enrolledCourse.isEmpty()) {
            throw new EnrolledCourseException("Student is not enrolled on Course!");
        }

        return enrolledCourse.get();
    }

    public EnrolledCourse finishCourse(UUID studentId, UUID courseId, Double finalGrade) throws EnrolledCourseException, StudentException, CourseException {
        var enrolledCourse = getEnrolledCourse(studentId, courseId);

        enrolledCourse.setFinalGrade(finalGrade);
        enrolledCourse.setStatus(EnrollmentStatus.COMPLETED);

        return enrolledCourseRepository.save(enrolledCourse);
    }

    public EnrolledCourse dropCourse(UUID studentId, UUID courseId) throws EnrolledCourseException, StudentException, CourseException {
        var enrolledCourse = getEnrolledCourse(studentId, courseId);

        enrolledCourse.setStatus(EnrollmentStatus.DROPPED);

        return enrolledCourseRepository.save(enrolledCourse);
    }

    public EnrolledCourse reopenCourse(UUID studentId, UUID courseId) throws EnrolledCourseException, StudentException, CourseException {
        var enrolledCourse = getEnrolledCourse(studentId, courseId);

        enrolledCourse.setStatus(EnrollmentStatus.ACTIVE);

        return enrolledCourseRepository.save(enrolledCourse);
    }

    public EnrolledCourse changeCourseStatus(UUID studentId, UUID courseId, String operation, Double finalGrade) throws EnrolledCourseException, StudentException, CourseException {
        return switch (operation) {
            case "finish" -> {
                if (finalGrade == null) {
                    throw new EnrolledCourseException("Cannot finish a course without final grade!");
                }
                yield finishCourse(studentId, courseId, finalGrade);
            }
            case "drop" -> dropCourse(studentId, courseId);
            case "reopen" -> reopenCourse(studentId, courseId);
            default -> throw new EnrolledCourseException("Invalid Course Operation!");
        };
    }
}
