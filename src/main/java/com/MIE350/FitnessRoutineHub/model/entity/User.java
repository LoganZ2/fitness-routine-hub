package com.MIE350.FitnessRoutineHub.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    private String description;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @NotNull
    @Column(nullable = false)
    private Instant createdAt;

    private Instant updateAt;

}
