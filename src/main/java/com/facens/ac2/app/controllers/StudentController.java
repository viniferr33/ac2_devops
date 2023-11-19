package com.facens.ac2.app.controllers;

import com.facens.ac2.app.requests.CreateStudentRequest;
import com.facens.ac2.app.requests.EnrollOnCourseRequest;
import com.facens.ac2.app.requests.UpdateStatusRequest;
import com.facens.ac2.domain.entities.exceptions.CourseException;
import com.facens.ac2.domain.entities.exceptions.EnrolledCourseException;
import com.facens.ac2.domain.entities.exceptions.StudentException;
import com.facens.ac2.domain.services.CourseService;
import com.facens.ac2.domain.services.EnrolledCourseService;
import com.facens.ac2.domain.services.StudentService;
import com.facens.ac2.dto.AvailableCoursesDTO;
import com.facens.ac2.dto.EnrolledCourseDTO;
import com.facens.ac2.dto.StudentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    final StudentService studentService;
    final CourseService courseService;
    final EnrolledCourseService enrolledCourseService;

    public StudentController(StudentService studentService, CourseService courseService, EnrolledCourseService enrolledCourseService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrolledCourseService = enrolledCourseService;
    }

    @GetMapping
    public List<StudentDTO> list() {
        return StudentDTO.fromEntity(
                studentService.listStudents()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable String id) throws StudentException {
        var student = studentService.getStudent(UUID.fromString(id));

        return ResponseEntity.status(HttpStatus.OK).body(
                StudentDTO.fromEntity(student)
        );
    }

    @GetMapping("/{id}/course")
    public ResponseEntity<AvailableCoursesDTO> listAvaliableCourses(@PathVariable String id) throws StudentException {
        var availableCourses = courseService.listAvailableCoursesForStudent(UUID.fromString(id));
        var numAvailableCourses = studentService.getAvailableCourseNum(UUID.fromString(id));

        return ResponseEntity.status(HttpStatus.OK).body(
                AvailableCoursesDTO.fromEntity(availableCourses, numAvailableCourses)
        );
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody @Valid CreateStudentRequest request) throws StudentException {
        var student = studentService.createStudent(
                request.name(),
                request.email()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                StudentDTO.fromEntity(student)
        );
    }

    @PostMapping("/{studentId}/course")
    public ResponseEntity<EnrolledCourseDTO> enrollOnCourse(@RequestBody @Valid EnrollOnCourseRequest request, @PathVariable String studentId) throws StudentException, CourseException, EnrolledCourseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                EnrolledCourseDTO.fromEntity(
                        enrolledCourseService.create(
                                UUID.fromString(studentId),
                                UUID.fromString(request.courseId()
                                )
                        )
                )
        );
    }

    @GetMapping("/{studentId}/course/{courseId}")
    public ResponseEntity<EnrolledCourseDTO> getEnrolledCourse(@PathVariable String studentId, @PathVariable String courseId) throws StudentException, CourseException, EnrolledCourseException {
        return ResponseEntity.status(HttpStatus.OK).body(
                EnrolledCourseDTO.fromEntity(
                        enrolledCourseService.getEnrolledCourse(
                                UUID.fromString(studentId),
                                UUID.fromString(courseId)
                        )
                )
        );
    }

    @PutMapping("/{studentId}/course/{courseId}")
    public ResponseEntity<EnrolledCourseDTO> finishCourse(@PathVariable String studentId, @PathVariable String courseId, @RequestBody UpdateStatusRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                EnrolledCourseDTO.fromEntity(enrolledCourseService.changeCourseStatus(
                        UUID.fromString(studentId),
                        UUID.fromString(courseId),
                        request.operation(),
                        request.finalGrade()
                ))
        );
    }
}
