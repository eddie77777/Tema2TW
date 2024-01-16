package com.example.tema2_servlet.database.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="person_course", schema = "public", catalog = "tw2")
public class CourseInfo {
    @EmbeddedId
    CourseKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "person_id")
    PersonEntity student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    CourseEntity course;

    @Column(name="final_grade")
    Float finalGrade;

    @OneToMany(mappedBy = "studentCourseInfo")
    private List<GradeEntity> grades;
}
