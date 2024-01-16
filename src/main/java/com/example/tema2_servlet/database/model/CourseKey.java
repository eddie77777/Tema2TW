package com.example.tema2_servlet.database.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CourseKey implements Serializable {
    @Column(name="person_id")
    private long studentId;

    @Column(name="course_id")
    private long courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseKey that = (CourseKey) o;

        if (studentId != that.studentId) return false;
        return courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        int result = (int) (studentId ^ (studentId >>> 32));
        result = 31 * result + (int) (courseId ^ (courseId >>> 32));
        return result;
    }
}
