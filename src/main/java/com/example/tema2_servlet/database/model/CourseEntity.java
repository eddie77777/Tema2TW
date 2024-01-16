package com.example.tema2_servlet.database.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="course", schema = "public", catalog = "tw2")
public class CourseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name="course_name")
    private String courseName;

    @Basic
    @Column(name = "course_description")
    private String courseDescription;

    @ManyToOne
    @JoinColumn(name="teacher_id", referencedColumnName = "id")
    private PersonEntity teacher;

    @OneToMany(mappedBy = "course")
    private List<CourseInfo> students;
}
