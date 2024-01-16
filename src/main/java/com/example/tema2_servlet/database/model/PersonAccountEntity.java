package com.example.tema2_servlet.database.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="person_account", schema = "public", catalog = "tw2")
public class PersonAccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private PersonEntity personInfo;

    @Enumerated(EnumType.ORDINAL)
    private Role personRole;
}


