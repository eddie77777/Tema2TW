package com.example.tema2_servlet.database.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="person", schema = "public", catalog = "tw2")
public class PersonEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "student")
    private List<CourseInfo> courses;
}
