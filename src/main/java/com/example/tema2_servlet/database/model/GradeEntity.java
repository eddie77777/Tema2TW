package com.example.tema2_servlet.database.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name="grade", schema = "public", catalog = "tw2")
public class GradeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column
    private int grade;

    @Basic
    @Column
    private Date dateTime;

    @ManyToOne
    private CourseInfo studentCourseInfo;
}
