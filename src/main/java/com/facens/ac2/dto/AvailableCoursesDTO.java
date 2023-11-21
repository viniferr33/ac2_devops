package com.facens.ac2.dto;

import com.facens.ac2.domain.entities.Course;

import java.util.List;

public class AvailableCoursesDTO {
    private List<CourseDTO> courses;
    private Integer numAvaliableCourses;

    public AvailableCoursesDTO(List<CourseDTO> courses, Integer numAvaliableCourses) {
        this.courses = courses;
        this.numAvaliableCourses = numAvaliableCourses;
    }

    public AvailableCoursesDTO() {

    }

    public static AvailableCoursesDTO fromEntity(List<Course> courses, Integer numAvaliableCourses) {
        return new AvailableCoursesDTO(
                CourseDTO.fromEntity(courses),
                numAvaliableCourses
        );
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public Integer getNumAvaliableCourses() {
        return numAvaliableCourses;
    }

    public void setNumAvaliableCourses(Integer numAvaliableCourses) {
        this.numAvaliableCourses = numAvaliableCourses;
    }
}
