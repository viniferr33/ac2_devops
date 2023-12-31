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

        var student = studentService.getStudent(studentId);
        var course = courseService.getCourse(courseId);

        if (!student.getCourses().isEmpty()) {
            Integer numCourses = studentService.getAvailableCourseNum(student);

            if (numCourses < 1) {
                throw new EnrolledCourseException("Student is not eligible to enroll on a new course!");
            }

            List<Course> studentCourses = courseService.listAvailableCoursesForStudent(student);

            if (!studentCourses.contains(course)) {
                throw new EnrolledCourseException("Course is not available for Student!");
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

        if (enrolledCourse.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new EnrolledCourseException("Cannot finish a course that is not Active!");
        }

        if (finalGrade > 10.0 || finalGrade < 0.0) {
            throw new EnrolledCourseException("Invalid Grade!");
        }

        enrolledCourse.setFinalGrade(finalGrade);
        enrolledCourse.setStatus(EnrollmentStatus.COMPLETED);

        return enrolledCourseRepository.save(enrolledCourse);
    }

    public EnrolledCourse dropCourse(UUID studentId, UUID courseId) throws EnrolledCourseException, StudentException, CourseException {
        var enrolledCourse = getEnrolledCourse(studentId, courseId);

        if (enrolledCourse.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new EnrolledCourseException("Cannot Drop course that is not Active!");
        }

        enrolledCourse.setStatus(EnrollmentStatus.DROPPED);

        return enrolledCourseRepository.save(enrolledCourse);
    }

    public EnrolledCourse reopenCourse(UUID studentId, UUID courseId) throws EnrolledCourseException, StudentException, CourseException {
        var enrolledCourse = getEnrolledCourse(studentId, courseId);

        if (enrolledCourse.getStatus() != EnrollmentStatus.DROPPED) {
            throw new EnrolledCourseException("Cannot Reopen a course that is not Dropped!");
        }

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
